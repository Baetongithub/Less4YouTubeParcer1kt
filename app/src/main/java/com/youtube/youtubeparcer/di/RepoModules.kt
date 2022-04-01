package com.youtube.youtubeparcer.di

import com.youtube.youtubeparcer.ui.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModules: Module = module {
    single { Repository(get()) }
}