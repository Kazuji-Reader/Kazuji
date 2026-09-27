package io.github.kazuji.mihon.parsers.core

import io.github.kazuji.mihon.parsers.ContentLoaderContext
import io.github.kazuji.mihon.parsers.InternalParsersApi
import io.github.kazuji.mihon.parsers.model.Content
import io.github.kazuji.mihon.parsers.model.ContentListFilter
import io.github.kazuji.mihon.parsers.model.ContentSource
import io.github.kazuji.mihon.parsers.model.SortOrder

@InternalParsersApi
public abstract class SinglePageContentParser(
	context: ContentLoaderContext,
	source: ContentSource,
) : AbstractContentParser(context, source) {

	final override suspend fun getList(offset: Int, order: SortOrder, filter: ContentListFilter): List<Content> {
		if (offset > 0) {
			return emptyList()
		}
		return getList(order, filter)
	}

	public abstract suspend fun getList(order: SortOrder, filter: ContentListFilter): List<Content>
}

