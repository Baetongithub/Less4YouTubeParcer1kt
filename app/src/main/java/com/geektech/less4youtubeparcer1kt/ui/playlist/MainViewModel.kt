package com.geektech.less4youtubeparcer1kt.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geektech.less4youtubeparcer1kt.base.BaseViewModel
import com.geektech.less4youtubeparcer1kt.model.playlist.PlayList
import com.geektech.less4youtubeparcer1kt.network.result.Resource
import com.geektech.less4youtubeparcer1kt.ui.Repository

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun loadAllPlaylist(): LiveData<Resource<PlayList?>> {
        return repository.fetchAllPlayList()
    }
}