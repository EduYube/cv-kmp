package com.eyubero.kmp.di

import com.eyubero.kmp.presentation.core.SmokeMessageProvider
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.context.startKoin
import org.koin.dsl.module

private var koinApp: KoinApplication? = null

fun initKoin() {
    koinApp = startKoin {
        modules(commonModule, platformModule())
    }
}

internal fun getKoin() =
    requireNotNull(koinApp) { "Koin has not been initialized. Call initKoin() first." }.koin

private val commonModule = module {
    // smoke test dep (de momento)
    single { SmokeMessageProvider() }
}

expect fun platformModule(): Module