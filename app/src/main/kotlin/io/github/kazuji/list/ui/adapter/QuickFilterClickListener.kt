package io.github.kazuji.list.ui.adapter

import io.github.kazuji.list.domain.ListFilterOption

interface QuickFilterClickListener {

	fun onFilterOptionClick(option: ListFilterOption)
}
