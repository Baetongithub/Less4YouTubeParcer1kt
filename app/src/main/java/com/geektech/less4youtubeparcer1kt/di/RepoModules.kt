package com.geektech.less4youtubeparcer1kt.di

import com.geektech.less4youtubeparcer1kt.ui.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModules: Module = module {
    single { Repository(get()) }
}