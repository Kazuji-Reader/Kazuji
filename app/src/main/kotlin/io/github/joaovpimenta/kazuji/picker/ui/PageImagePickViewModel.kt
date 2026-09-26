package io.github.joaovpimenta.kazuji.picker.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import io.github.joaovpimenta.kazuji.core.ui.BaseViewModel
import io.github.joaovpimenta.kazuji.core.util.ext.MutableEventFlow
import io.github.joaovpimenta.kazuji.core.util.ext.call
import io.github.joaovpimenta.kazuji.reader.ui.PageSaveHelper
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PageImagePickViewModel @Inject constructor() : BaseViewModel() {

	val onFileReady = MutableEventFlow<File>()

	fun savePageToTempFile(pageSaveHelper: PageSaveHelper, task: PageSaveHelper.Task) {
		launchLoadingJob(Dispatchers.IO) {
			val file = pageSaveHelper.saveToTempFile(task)
			onFileReady.call(file)
		}
	}
}
