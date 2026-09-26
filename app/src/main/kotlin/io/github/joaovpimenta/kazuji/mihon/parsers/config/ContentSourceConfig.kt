package io.github.joaovpimenta.kazuji.mihon.parsers.config

interface ContentSourceConfig {
	operator fun <T> get(key: ConfigKey<T>): T
}
