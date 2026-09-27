package io.github.kazuji.sync.ui

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.kazuji.sync.domain.SyncHelper

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SyncAdapterEntryPoint {
	val syncHelperFactory: SyncHelper.Factory
}
