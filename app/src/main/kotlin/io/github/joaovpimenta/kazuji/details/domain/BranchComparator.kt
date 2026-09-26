package io.github.joaovpimenta.kazuji.details.domain

import io.github.joaovpimenta.kazuji.core.util.LocaleStringComparator
import io.github.joaovpimenta.kazuji.details.ui.model.MangaBranch

class BranchComparator : Comparator<MangaBranch> {

	private val delegate = LocaleStringComparator()

	override fun compare(o1: MangaBranch, o2: MangaBranch): Int = delegate.compare(o1.name, o2.name)
}
