package com.geektech.less4youtubeparcer1kt.di

import com.geektech.less4youtubeparcer1kt.ui.playlist.MainViewModel
import com.geektech.less4youtubeparcer1kt.ui.deatiled_playlist.DetailedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailedViewModel(get()) }
}