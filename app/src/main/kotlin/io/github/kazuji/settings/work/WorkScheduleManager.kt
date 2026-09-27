package io.github.kazuji.settings.work

import android.content.SharedPreferences
import io.github.kazuji.core.prefs.AppSettings
import io.github.kazuji.core.util.ext.processLifecycleScope
import io.github.kazuji.download.ui.worker.DownloadSchedulerWorker
import io.github.kazuji.download.ui.worker.DownloadWorker
import io.github.kazuji.suggestions.ui.SuggestionsWorker
import io.github.kazuji.tracker.work.TrackWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkScheduleManager @Inject constructor(
	private val settings: AppSettings,
	private val suggestionScheduler: SuggestionsWorker.Scheduler,
	private val trackerScheduler: TrackWorker.Scheduler,
	private val downloadScheduler: DownloadWorker.Scheduler,
) : SharedPreferences.OnSharedPreferenceChangeListener {

	override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
		when (key) {
			AppSettings.KEY_TRACKER_ENABLED,
			AppSettings.KEY_TRACKER_FREQUENCY,
			AppSettings.KEY_TRACKER_WIFI_ONLY -> updateWorker(
				scheduler = trackerScheduler,
				isEnabled = settings.isTrackerEnabled,
				force = key != AppSettings.KEY_TRACKER_ENABLED,
			)

			AppSettings.KEY_SUGGESTIONS,
			AppSettings.KEY_SUGGESTIONS_WIFI_ONLY -> updateWorker(
				scheduler = suggestionScheduler,
				isEnabled = settings.isSuggestionsEnabled,
				force = key != AppSettings.KEY_SUGGESTIONS,
			)

			AppSettings.KEY_DOWNLOAD_OFF_PEAK_ENABLED,
			AppSettings.KEY_DOWNLOAD_OFF_PEAK_START,
			AppSettings.KEY_DOWNLOAD_OFF_PEAK_END -> {
				DownloadSchedulerWorker.scheduleAlarm(downloadScheduler.workManager, settings)
			}
		}
	}

	fun init() {
		settings.subscribe(this)
		processLifecycleScope.launch(Dispatchers.IO) {
			updateWorkerImpl(trackerScheduler, settings.isTrackerEnabled, true) // always force due to adaptive interval
			updateWorkerImpl(suggestionScheduler, settings.isSuggestionsEnabled, false)
			DownloadSchedulerWorker.scheduleAlarm(downloadScheduler.workManager, settings)
		}
	}

	private fun updateWorker(scheduler: PeriodicWorkScheduler, isEnabled: Boolean, force: Boolean) {
		processLifecycleScope.launch(Dispatchers.IO) {
			updateWorkerImpl(scheduler, isEnabled, force)
		}
	}

	private suspend fun updateWorkerImpl(scheduler: PeriodicWorkScheduler, isEnabled: Boolean, force: Boolean) {
		if (force || scheduler.isScheduled() != isEnabled) {
			if (isEnabled) {
				scheduler.schedule()
			} else {
				scheduler.unschedule()
			}
		}
	}
}
