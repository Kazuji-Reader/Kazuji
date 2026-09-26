package io.github.joaovpimenta.kazuji.download.ui.list

import io.github.joaovpimenta.kazuji.core.ui.list.OnListItemClickListener
import io.github.joaovpimenta.kazuji.download.ui.list.chapters.DownloadChapter

interface DownloadItemListener : OnListItemClickListener<DownloadItemModel> {

	fun onCancelClick(item: DownloadItemModel)

	fun onPauseClick(item: DownloadItemModel)

	fun onResumeClick(item: DownloadItemModel)

	fun onSkipClick(item: DownloadItemModel)

	fun onSkipAllClick(item: DownloadItemModel)

	fun onExpandClick(item: DownloadItemModel)

	fun onDeleteChapterClick(item: DownloadItemModel, chapter: DownloadChapter)
}
