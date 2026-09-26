package io.github.joaovpimenta.kazuji.explore.data

import androidx.annotation.StringRes
import io.github.joaovpimenta.kazuji.R

enum class SourcesSortOrder(
	@StringRes val titleResId: Int,
) {
	ALPHABETIC(R.string.by_name),
	POPULARITY(R.string.popular),
	MANUAL(R.string.manual),
	LAST_USED(R.string.last_used),
}
