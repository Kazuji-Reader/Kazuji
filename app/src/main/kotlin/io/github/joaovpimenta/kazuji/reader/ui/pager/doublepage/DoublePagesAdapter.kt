package io.github.joaovpimenta.kazuji.reader.ui.pager.doublepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import io.github.joaovpimenta.kazuji.core.exceptions.resolve.ExceptionResolver
import io.github.joaovpimenta.kazuji.core.os.NetworkState
import io.github.joaovpimenta.kazuji.databinding.ItemPageBinding
import io.github.joaovpimenta.kazuji.reader.domain.PageLoader
import io.github.joaovpimenta.kazuji.reader.ui.config.ReaderSettings
import io.github.joaovpimenta.kazuji.reader.ui.pager.BaseReaderAdapter

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
