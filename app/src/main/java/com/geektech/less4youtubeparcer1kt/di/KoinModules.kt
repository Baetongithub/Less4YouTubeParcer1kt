package com.geektech.less4youtubeparcer1kt.di

import com.geektech.less4youtubeparcer1kt.network.networkModule

val koinModules = listOf(
    networkModule,
    viewModules,
    repoModules
)