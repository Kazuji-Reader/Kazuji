#requires -Version 5.1
[CmdletBinding()]
param(
	[ValidatePattern('^[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+$')]
	[string]$Repository = 'joaovpimenta/Kazuji',
	[switch]$ReplaceExistingSecrets,
	[switch]$Resume
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

if ([Environment]::OSVersion.Platform -ne [PlatformID]::Win32NT) {
	throw 'Este script usa DPAPI para proteger o backup local e precisa ser executado no Windows.'
}
if ([string]::IsNullOrWhiteSpace($env:LOCALAPPDATA)) {
	throw 'A variável LOCALAPPDATA não está definida.'
}
if ($Resume -and $ReplaceExistingSecrets) {
	throw 'Use -Resume ou -ReplaceExistingSecrets, não ambos.'
}

$ghCommand = Get-Command gh -CommandType Application -ErrorAction Stop | Select-Object -First 1
$keytoolCommand = Get-Command keytool -CommandType Application -ErrorAction SilentlyContinue | Select-Object -First 1
if ($null -eq $keytoolCommand -and -not [string]::IsNullOrWhiteSpace($env:JAVA_HOME)) {
	$keytoolCandidate = Join-Path $env:JAVA_HOME 'bin\keytool.exe'
	if (Test-Path -LiteralPath $keytoolCandidate -PathType Leaf) {
		$keytoolCommand = [pscustomobject]@{ Source = $keytoolCandidate }
	}
}
if ($null -eq $keytoolCommand) {
	throw 'keytool não foi encontrado. Instale um JDK 17+ e adicione JAVA_HOME\bin ao PATH.'
}

$secretNames = @('KEYSTORE_FILE', 'KEYSTORE_PASSWORD', 'KEY_ALIAS', 'KEY_PASSWORD')
$signingDirectory = Join-Path $env:LOCALAPPDATA 'Kazuji\signing'
$keystorePath = Join-Path $signingDirectory 'kazuji-release.jks'
$credentialBackupPath = Join-Path $signingDirectory 'kazuji-release.credentials.json'
$keyAlias = 'kazuji-release'

& $ghCommand.Source auth status --hostname github.com
if ($LASTEXITCODE -ne 0) {
	throw 'Faça login no GitHub CLI primeiro: gh auth login'
}

$resolvedRepository = & $ghCommand.Source repo view $Repository --json nameWithOwner --jq '.nameWithOwner'
if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace(($resolvedRepository | Out-String).Trim())) {
	throw "Não foi possível acessar o repositório $Repository com a conta autenticada no gh."
}
$Repository = ($resolvedRepository | Out-String).Trim()

$existingSecretNames = @(& $ghCommand.Source secret list --repo $Repository --json name --jq '.[].name')
if ($LASTEXITCODE -ne 0) {
	throw "Não foi possível listar os secrets de Actions em $Repository."
}
$existingSigningSecrets = @($existingSecretNames | Where-Object { $secretNames -contains $_ })
$hasKeystore = Test-Path -LiteralPath $keystorePath -PathType Leaf
$hasCredentialBackup = Test-Path -LiteralPath $credentialBackupPath -PathType Leaf

if ($Resume) {
	if (-not ($hasKeystore -and $hasCredentialBackup)) {
		throw "Para retomar, os dois arquivos locais precisam existir em $signingDirectory."
	}
	$storedCredentials = Get-Content -LiteralPath $credentialBackupPath -Raw | ConvertFrom-Json
	if ($storedCredentials.repository -ne $Repository) {
		throw "O backup local pertence a $($storedCredentials.repository), não a $Repository."
	}
	if ([string]::IsNullOrWhiteSpace([string]$storedCredentials.keyAlias) -or
		[string]::IsNullOrWhiteSpace([string]$storedCredentials.storePasswordDpapi) -or
		[string]::IsNullOrWhiteSpace([string]$storedCredentials.keyPasswordDpapi)) {
		throw 'O backup local está incompleto; nenhum secret foi enviado.'
	}
}
elseif ($hasKeystore -or $hasCredentialBackup) {
	throw "Já existem arquivos de assinatura em $signingDirectory. Para reenviar os secrets da mesma chave, use -Resume."
}

Write-Host "Repositório GitHub: $Repository"
Write-Host "Keystore local: $keystorePath"
Write-Host "Backup local protegido por DPAPI: $credentialBackupPath"
Write-Host 'Serão enviados quatro secrets: KEYSTORE_FILE, KEYSTORE_PASSWORD, KEY_ALIAS e KEY_PASSWORD.'

if ($Resume) {
	if ($existingSigningSecrets.Count -gt 0) {
		Write-Host "Secrets que serão definidos novamente com a mesma chave local: $($existingSigningSecrets -join ', ')"
	}
	$confirmation = Read-Host 'O script reenviará os quatro secrets usando a keystore local existente. Digite UPLOAD para continuar'
	if ($confirmation -cne 'UPLOAD') {
		throw 'Reenvio cancelado.'
	}
}
else {
	if ($existingSigningSecrets.Count -gt 0) {
		Write-Host "Secrets já existentes: $($existingSigningSecrets -join ', ')"
		if (-not $ReplaceExistingSecrets) {
			throw 'Secrets existentes não serão substituídos. Use -ReplaceExistingSecrets somente se quiser trocar a identidade de assinatura.'
		}
		Write-Warning 'Substituir esses secrets por uma nova keystore pode impedir atualizações sobre instalações assinadas pela chave anterior.'
		$replaceConfirmation = Read-Host 'Para confirmar a substituição dos secrets existentes, digite REPLACE'
		if ($replaceConfirmation -cne 'REPLACE') {
			throw 'Substituição cancelada.'
		}
	}
	$confirmation = Read-Host 'O script criará uma nova identidade de assinatura e enviará os secrets ao GitHub. Digite CREATE para continuar'
	if ($confirmation -cne 'CREATE') {
		throw 'Operação cancelada.'
	}
}

function New-RandomPassword {
	$bytes = New-Object byte[] 32
	$random = [Security.Cryptography.RandomNumberGenerator]::Create()
	try {
		$random.GetBytes($bytes)
		return [Convert]::ToBase64String($bytes).TrimEnd('=').Replace('+', '-').Replace('/', '_')
	}
	finally {
		[Array]::Clear($bytes, 0, $bytes.Length)
		$random.Dispose()
	}
}

function ConvertFrom-DpapiText {
	param([Parameter(Mandatory = $true)][string]$ProtectedText)

	$secureText = ConvertTo-SecureString $ProtectedText
	$pointer = [Runtime.InteropServices.Marshal]::SecureStringToBSTR($secureText)
	try {
		return [Runtime.InteropServices.Marshal]::PtrToStringBSTR($pointer)
	}
	finally {
		[Runtime.InteropServices.Marshal]::ZeroFreeBSTR($pointer)
		$secureText.Dispose()
	}
}

function Set-GitHubSecretFromStdin {
	param(
		[Parameter(Mandatory = $true)][string]$Name,
		[Parameter(Mandatory = $true)][string]$Value
	)

	$startInfo = New-Object System.Diagnostics.ProcessStartInfo
	$startInfo.FileName = $ghCommand.Source
	$startInfo.Arguments = "secret set $Name --repo $Repository"
	$startInfo.UseShellExecute = $false
	$startInfo.CreateNoWindow = $true
	$startInfo.RedirectStandardInput = $true
	$startInfo.RedirectStandardOutput = $true
	$startInfo.RedirectStandardError = $true

	$process = New-Object System.Diagnostics.Process
	$process.StartInfo = $startInfo
	try {
		if (-not $process.Start()) {
			throw "Não foi possível iniciar gh para definir o secret $Name."
		}
		$stdoutTask = $process.StandardOutput.ReadToEndAsync()
		$stderrTask = $process.StandardError.ReadToEndAsync()
		$process.StandardInput.Write($Value)
		$process.StandardInput.Close()
		$process.WaitForExit()
		$null = $stdoutTask.GetAwaiter().GetResult()
		$stderr = $stderrTask.GetAwaiter().GetResult()
		if ($process.ExitCode -ne 0) {
			$errorDetail = ($stderr | Out-String).Trim()
			throw "gh não conseguiu definir o secret $Name (código $($process.ExitCode)). $errorDetail"
		}
	}
	finally {
		$process.Dispose()
	}
}

if ($Resume) {
	$storePassword = ConvertFrom-DpapiText $storedCredentials.storePasswordDpapi
	$keyPassword = ConvertFrom-DpapiText $storedCredentials.keyPasswordDpapi
	$keyAlias = [string]$storedCredentials.keyAlias
	$fingerprint = [string]$storedCredentials.certificateSha256
}
else {
	New-Item -ItemType Directory -Path $signingDirectory -Force | Out-Null
	$storePassword = New-RandomPassword
	$keyPassword = New-RandomPassword
	$credentialsSaved = $false
	try {
		$env:KAZUJI_STORE_PASSWORD = $storePassword
		$env:KAZUJI_KEY_PASSWORD = $keyPassword
		$keytoolArguments = @(
			'-genkeypair',
			'-keystore', $keystorePath,
			'-storetype', 'JKS',
			'-alias', $keyAlias,
			'-keyalg', 'RSA',
			'-keysize', '4096',
			'-validity', '10000',
			'-dname', 'CN=Kazuji Release, O=Kazuji, C=BR',
			'-storepass:env', 'KAZUJI_STORE_PASSWORD',
			'-keypass:env', 'KAZUJI_KEY_PASSWORD',
			'-noprompt'
		)
		$previousErrorActionPreference = $ErrorActionPreference
		try {
			# Windows PowerShell 5.1 can turn native stderr warnings into terminating errors.
			$ErrorActionPreference = 'Continue'
			$generationOutput = & $keytoolCommand.Source @keytoolArguments 2>&1
			$generationExitCode = $LASTEXITCODE
		}
		finally {
			$ErrorActionPreference = $previousErrorActionPreference
		}
		if ($generationExitCode -ne 0) {
			$generationOutput | ForEach-Object { Write-Host $_ }
			throw "keytool falhou ao criar a keystore (código $generationExitCode)."
		}

		$previousErrorActionPreference = $ErrorActionPreference
		try {
			$ErrorActionPreference = 'Continue'
			$certificateOutput = & $keytoolCommand.Source -list -v -keystore $keystorePath -storepass:env KAZUJI_STORE_PASSWORD -alias $keyAlias 2>&1
			$listExitCode = $LASTEXITCODE
		}
		finally {
			$ErrorActionPreference = $previousErrorActionPreference
		}
		if ($listExitCode -ne 0) {
			$certificateOutput | ForEach-Object { Write-Host $_ }
			throw "keytool falhou ao verificar a keystore (código $listExitCode)."
		}
		$fingerprint = $null
		foreach ($line in $certificateOutput) {
			if ([string]$line -match '^\s*SHA-?256:\s*(?<value>(?:[0-9A-Fa-f]{2}:?)+)\s*$') {
				$fingerprint = $Matches['value']
				break
			}
		}

		$credentialBackup = [ordered]@{
			formatVersion = 1
			repository = $Repository
			keyAlias = $keyAlias
			keystorePath = $keystorePath
			certificateSha256 = $fingerprint
			storePasswordDpapi = ConvertFrom-SecureString (ConvertTo-SecureString $storePassword -AsPlainText -Force)
			keyPasswordDpapi = ConvertFrom-SecureString (ConvertTo-SecureString $keyPassword -AsPlainText -Force)
		}
		$backupJson = $credentialBackup | ConvertTo-Json -Depth 4
		[IO.File]::WriteAllText($credentialBackupPath, $backupJson, [System.Text.UTF8Encoding]::new($false))
		$credentialsSaved = $true
	}
	catch {
		if (-not $credentialsSaved -and (Test-Path -LiteralPath $keystorePath)) {
			Remove-Item -LiteralPath $keystorePath -Force
		}
		throw
	}
	finally {
		Remove-Item Env:\KAZUJI_STORE_PASSWORD -ErrorAction SilentlyContinue
		Remove-Item Env:\KAZUJI_KEY_PASSWORD -ErrorAction SilentlyContinue
	}
}

try {
	$keystoreBase64 = [Convert]::ToBase64String([IO.File]::ReadAllBytes($keystorePath))
	# Send the keystore last so the workflow receives it after the other values.
	Set-GitHubSecretFromStdin -Name 'KEYSTORE_PASSWORD' -Value $storePassword
	Set-GitHubSecretFromStdin -Name 'KEY_ALIAS' -Value $keyAlias
	Set-GitHubSecretFromStdin -Name 'KEY_PASSWORD' -Value $keyPassword
	Set-GitHubSecretFromStdin -Name 'KEYSTORE_FILE' -Value $keystoreBase64

	Write-Host "Release signing secrets uploaded to $Repository. Secret values were not printed."
	Write-Host "Keep the keystore file and protected credential backup safe: $signingDirectory"
	if ($fingerprint) {
		Write-Host "New Kazuji signing certificate SHA-256: $fingerprint"
	}
	else {
		Write-Warning 'A impressão digital SHA-256 não foi extraída automaticamente; obtenha-a com keytool -list -v.'
	}
}
catch {
	Write-Host "Os arquivos locais foram preservados em $signingDirectory. Para reenviar os quatro secrets com a mesma chave, execute novamente com -Resume."
	throw
}
finally {
	Remove-Item Env:\KAZUJI_STORE_PASSWORD -ErrorAction SilentlyContinue
	Remove-Item Env:\KAZUJI_KEY_PASSWORD -ErrorAction SilentlyContinue
	$storePassword = $null
	$keyPassword = $null
	$keystoreBase64 = $null
}
