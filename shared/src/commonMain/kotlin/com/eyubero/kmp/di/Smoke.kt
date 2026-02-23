package com.eyubero.kmp.di

import com.eyubero.kmp.presentation.core.SmokeMessageProvider

fun smokeMessage(): String {
    return getKoin().get<SmokeMessageProvider>().message()
}