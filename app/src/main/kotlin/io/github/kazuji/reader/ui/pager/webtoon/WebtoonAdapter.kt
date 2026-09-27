package io.github.kazuji.reader.ui.pager.webtoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import io.github.kazuji.core.exceptions.resolve.ExceptionResolver
import io.github.kazuji.core.os.NetworkState
import io.github.kazuji.databinding.ItemPageWebtoonBinding
import io.github.kazuji.reader.domain.PageLoader
import io.github.kazuji.reader.ui.config.ReaderSettings
import io.github.kazuji.reader.ui.pager.BaseReaderAdapter

class WebtoonAdapter(
	private val lifecycleOwner: LifecycleOwner,
	loader: PageLoader,
	readerSettingsProducer: ReaderSettings.Producer,
	networkState: NetworkState,
	exceptionResolver: ExceptionResolver,
) : BaseReaderAdapter<WebtoonHolder>(loader, readerSettingsProducer, networkState, exceptionResolver) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		loader: PageLoader,
		readerSettingsProducer: ReaderSettings.Producer,
		networkState: NetworkState,
		exceptionResolver: ExceptionResolver,
	) = WebtoonHolder(
		owner = lifecycleOwner,
		binding = ItemPageWebtoonBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false,
		),
		loader = loader,
		readerSettingsProducer = readerSettingsProducer,
		networkState = networkState,
		exceptionResolver = exceptionResolver,
	)
}
