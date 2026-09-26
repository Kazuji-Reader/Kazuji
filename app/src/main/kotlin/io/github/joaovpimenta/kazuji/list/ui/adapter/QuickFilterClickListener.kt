package io.github.joaovpimenta.kazuji.list.ui.adapter

import io.github.joaovpimenta.kazuji.list.domain.ListFilterOption

interface QuickFilterClickListener {

	fun onFilterOptionClick(option: ListFilterOption)
}
