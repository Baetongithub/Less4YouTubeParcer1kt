package com.youtube.youtubeparcer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youtube.youtubeparcer.base.BaseViewModel
import com.youtube.youtubeparcer.data.model.playlist.PlayList
import com.youtube.youtubeparcer.data.network.result.Resource
import com.youtube.youtubeparcer.ui.Repository

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun loadAllPlaylist(): LiveData<Resource<PlayList?>> {
        return repository.fetchAllPlayList()
    }
}