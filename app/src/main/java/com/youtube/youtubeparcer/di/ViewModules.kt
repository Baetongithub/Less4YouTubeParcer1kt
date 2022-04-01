package com.youtube.youtubeparcer.di

import com.youtube.youtubeparcer.ui.main.MainViewModel
import com.youtube.youtubeparcer.ui.detailed_playlist.DetailedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailedViewModel(get()) }
}