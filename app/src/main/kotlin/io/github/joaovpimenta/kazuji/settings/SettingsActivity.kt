package io.github.joaovpimenta.kazuji.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import io.github.joaovpimenta.kazuji.R
import io.github.joaovpimenta.kazuji.backups.ui.periodical.PeriodicalBackupSettingsFragment
import io.github.joaovpimenta.kazuji.core.model.MangaSource
import io.github.joaovpimenta.kazuji.core.nav.AppRouter
import io.github.joaovpimenta.kazuji.core.ui.BaseActivity
import io.github.joaovpimenta.kazuji.core.util.ext.buildBundle
import io.github.joaovpimenta.kazuji.core.util.ext.end
import io.github.joaovpimenta.kazuji.core.util.ext.observe
import io.github.joaovpimenta.kazuji.core.util.ext.observeEvent
import io.github.joaovpimenta.kazuji.core.util.ext.start
import io.github.joaovpimenta.kazuji.core.util.ext.textAndVisible
import io.github.joaovpimenta.kazuji.databinding.ActivitySettingsBinding
import io.github.joaovpimenta.kazuji.main.ui.owners.AppBarOwner
import io.github.joaovpimenta.kazuji.settings.about.AboutSettingsFragment
import io.github.joaovpimenta.kazuji.settings.discord.DiscordSettingsFragment
import io.github.joaovpimenta.kazuji.settings.search.SettingsItem
import io.github.joaovpimenta.kazuji.settings.search.SettingsSearchFragment
import io.github.joaovpimenta.kazuji.settings.search.SettingsSearchViewModel
import io.github.joaovpimenta.kazuji.settings.sources.SourceSettingsFragment
import io.github.joaovpimenta.kazuji.settings.sources.SourcesSettingsFragment
import io.github.joaovpimenta.kazuji.settings.sources.manage.SourcesManageFragment
import io.github.joaovpimenta.kazuji.settings.tracker.TrackerSettingsFragment
import io.github.joaovpimenta.kazuji.settings.userdata.BackupsSettingsFragment

@AndroidEntryPoint
class SettingsActivity :
	BaseActivity<ActivitySettingsBinding>(),
	PreferenceFragmentCompat.OnPreferenceStartFragmentCallback,
	AppBarOwner {

	override val appBar: AppBarLayout
		get() = viewBinding.appbar

	private val isMasterDetails
		get() = viewBinding.containerMaster != null

	private val viewModel: SettingsSearchViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(ActivitySettingsBinding.inflate(layoutInflater))
		setDisplayHomeAsUp(isEnabled = true, showUpAsClose = false)
		val fm = supportFragmentManager
		val currentFragment = fm.findFragmentById(R.id.container)
		if (currentFragment == null || (isMasterDetails && currentFragment is RootSettingsFragment)) {
			openDefaultFragment()
		}
		if (isMasterDetails && fm.findFragmentById(R.id.container_master) == null) {
			supportFragmentManager.commit {
				setReorderingAllowed(true)
				replace(R.id.container_master, RootSettingsFragment())
			}
		}
		viewModel.isSearchActive.observe(this, ::toggleSearchMode)
		viewModel.onNavigateToPreference.observeEvent(this, ::navigateToPreference)
	}

	override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
		val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
		val isTablet = viewBinding.containerMaster != null
		viewBinding.appbar.updatePaddingRelative(
			start = bars.start(v),
			top = bars.top,
			end = if (isTablet) 0 else bars.end(v),
		)
		viewBinding.textViewHeader?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
			marginEnd = bars.end(v)
			topMargin = bars.top
		}
		return insets
	}

	override fun onPreferenceStartFragment(
		caller: PreferenceFragmentCompat,
		pref: Preference,
	): Boolean {
		val fragmentName = pref.fragment ?: return false
		openFragment(
			fragmentClass = FragmentFactory.loadFragmentClass(classLoader, fragmentName),
			args = pref.peekExtras(),
			isFromRoot = caller is RootSettingsFragment,
		)
		return true
	}

	fun setSectionTitle(title: CharSequence?) {
		viewBinding.textViewHeader?.apply {
			textAndVisible = title
		} ?: setTitle(title ?: getString(R.string.settings))
	}

	fun openFragment(fragmentClass: Class<out Fragment>, args: Bundle?, isFromRoot: Boolean) {
		viewModel.discardSearch()
		val hasFragment = supportFragmentManager.findFragmentById(R.id.container) != null
		supportFragmentManager.commit {
			setReorderingAllowed(true)
			replace(R.id.container, fragmentClass, args)
			setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
			if (!isMasterDetails || (hasFragment && !isFromRoot)) {
				addToBackStack(null)
			}
		}
	}

	private fun toggleSearchMode(isEnabled: Boolean) {
		viewBinding.containerSearch.isVisible = isEnabled
		val searchFragment = supportFragmentManager.findFragmentById(R.id.container_search)
		if (searchFragment != null) {
			if (!isEnabled) {
				invalidateOptionsMenu()
				supportFragmentManager.commit {
					setReorderingAllowed(true)
					remove(searchFragment)
					setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
				}
			}
		} else if (isEnabled) {
			supportFragmentManager.commit {
				setReorderingAllowed(true)
				add(R.id.container_search, SettingsSearchFragment::class.java, null)
				setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
			}
		}
	}

	private fun openDefaultFragment() {
		val fragment = when (intent?.action) {
			AppRouter.ACTION_READER -> ReaderSettingsFragment()
			AppRouter.ACTION_SUGGESTIONS -> SuggestionsSettingsFragment()
			AppRouter.ACTION_HISTORY -> BackupsSettingsFragment()
			AppRouter.ACTION_TRACKER -> TrackerSettingsFragment()
			AppRouter.ACTION_PERIODIC_BACKUP -> PeriodicalBackupSettingsFragment()
			AppRouter.ACTION_SOURCES -> SourcesSettingsFragment()
			AppRouter.ACTION_MANAGE_DISCORD -> DiscordSettingsFragment()
			AppRouter.ACTION_PROXY -> ProxySettingsFragment()
			AppRouter.ACTION_MANAGE_DOWNLOADS -> DownloadsSettingsFragment()
			AppRouter.ACTION_SOURCE -> SourceSettingsFragment.newInstance(
				MangaSource(intent.getStringExtra(AppRouter.KEY_SOURCE)),
			)

			AppRouter.ACTION_MANAGE_SOURCES -> SourcesManageFragment()
			Intent.ACTION_VIEW -> {
				when (intent.data?.host) {
					HOST_ABOUT -> AboutSettingsFragment()
					HOST_SYNC_SETTINGS -> SyncSettingsFragment()
					else -> null
				}
			}

			else -> null
		} ?: if (isMasterDetails) AppearanceSettingsFragment() else RootSettingsFragment()
		supportFragmentManager.commit {
			setReorderingAllowed(true)
			replace(R.id.container, fragment)
		}
	}

	private fun navigateToPreference(item: SettingsItem) {
		val args = buildBundle(1) {
			putString(ARG_PREF_KEY, item.key)
		}
		openFragment(item.fragmentClass, args, true)
	}

	companion object {

		private const val HOST_ABOUT = "about"
		private const val HOST_SYNC_SETTINGS = "sync-settings"
		const val ARG_PREF_KEY = "pref_key"
	}
}
