package io.github.kazuji.download.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.kazuji.core.parser.MangaDataRepository
import io.github.kazuji.core.prefs.AppSettings
import io.github.kazuji.core.prefs.TriStateOption
import io.github.kazuji.core.ui.BaseViewModel
import io.github.kazuji.download.data.entity.DownloadQueueEntity
import io.github.kazuji.download.data.repository.DownloadQueueRepository
import io.github.kazuji.download.domain.usecase.QueueAllUnreadFromFavoritesUseCase
import io.github.kazuji.download.ui.worker.DownloadSchedulerWorker
import io.github.kazuji.local.domain.EnforceStorageQuotaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class DownloadQueueViewModel @Inject constructor(
    private val downloadQueueRepository: DownloadQueueRepository,
    private val mangaDataRepository: MangaDataRepository,
    private val queueAllUnreadFromFavoritesUseCase: QueueAllUnreadFromFavoritesUseCase,
    private val enforceStorageQuotaUseCase: EnforceStorageQuotaUseCase,
    private val settings: AppSettings,
    private val workManager: androidx.work.WorkManager,
) : BaseViewModel() {

    val storageUsage = MutableStateFlow<EnforceStorageQuotaUseCase.StorageUsage?>(null)

    val queue: StateFlow<List<DownloadQueueItem>> = downloadQueueRepository.observeQueue()
        .map { entities ->
            entities.map { entity ->
                val manga = mangaDataRepository.findMangaById(entity.mangaId, withChapters = false)
                DownloadQueueItem(entity, manga)
            }
        }
        .stateIn(viewModelScope + Dispatchers.IO, SharingStarted.Lazily, emptyList())

    init {
        refreshStorageUsage()
    }

    fun refreshStorageUsage() {
        launchJob(Dispatchers.IO) {
            storageUsage.value = enforceStorageQuotaUseCase.getUsage()
        }
    }

    fun queueAllUnreadFromFavorites() {
        launchJob(Dispatchers.IO) {
            val wifiOnly = settings.allowDownloadOnMeteredNetwork == TriStateOption.DISABLED
            queueAllUnreadFromFavoritesUseCase(
                wifiOnly = wifiOnly,
                chargingOnly = settings.isDownloadOnlyOnChargingEnabled,
                offPeakOnly = settings.isDownloadOffPeakEnabled
            )
        }
    }

    fun removeFromQueue(id: Long) {
        launchJob(Dispatchers.IO) {
            downloadQueueRepository.removeFromQueue(id)
        }
    }

    fun updatePaused(id: Long, isPaused: Boolean) {
        launchJob(Dispatchers.IO) {
            downloadQueueRepository.updatePaused(id, isPaused)
        }
    }

    fun reorderQueue(ids: List<Long>) {
        launchJob(Dispatchers.IO) {
            downloadQueueRepository.updateQueueOrder(ids)
        }
    }

    fun clearQueue() {
        launchJob(Dispatchers.IO) {
            downloadQueueRepository.clearQueue()
        }
    }

    fun triggerScheduler() {
        DownloadSchedulerWorker.enqueue(workManager, force = true)
    }
}

data class DownloadQueueItem(
    val entity: DownloadQueueEntity,
    val manga: org.koitharu.kotatsu.parsers.model.Manga?,
)
