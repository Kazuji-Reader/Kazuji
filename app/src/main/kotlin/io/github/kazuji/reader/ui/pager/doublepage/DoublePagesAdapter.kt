package io.github.kazuji.reader.ui.pager.doublepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import io.github.kazuji.core.exceptions.resolve.ExceptionResolver
import io.github.kazuji.core.os.NetworkState
import io.github.kazuji.databinding.ItemPageBinding
import io.github.kazuji.reader.domain.PageLoader
import io.github.kazuji.reader.ui.config.ReaderSettings
import io.github.kazuji.reader.ui.pager.BaseReaderAdapter

class DoublePagesAdapter(
	private val lifecycleOwner: LifecycleOwner,
	loader: PageLoader,
	readerSettingsProducer: ReaderSettings.Producer,
	networkState: NetworkState,
	exceptionResolver: ExceptionResolver,
) : BaseReaderAdapter<DoublePageHolder>(loader, readerSettingsProducer, networkState, exceptionResolver) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		loader: PageLoader,
		readerSettingsProducer: ReaderSettings.Producer,
		networkState: NetworkState,
		exceptionResolver: ExceptionResolver,
	) = DoublePageHolder(
		owner = lifecycleOwner,
		binding = ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
		loader = loader,
		readerSettingsProducer = readerSettingsProducer,
		networkState = networkState,
		exceptionResolver = exceptionResolver,
	)
}
