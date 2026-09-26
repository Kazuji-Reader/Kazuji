package io.github.joaovpimenta.kazuji.settings.utils

import android.widget.EditText
import androidx.preference.EditTextPreference
import io.github.joaovpimenta.kazuji.core.util.EditTextValidator

class EditTextBindListener(
	private val inputType: Int,
	private val hint: String?,
	private val validator: EditTextValidator?,
) : EditTextPreference.OnBindEditTextListener {

	override fun onBindEditText(editText: EditText) {
		editText.inputType = inputType
		editText.hint = hint
		validator?.attachToEditText(editText)
	}
}
