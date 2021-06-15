package com.geektech.less4youtubeparcer1kt.ui.detailed_playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geektech.less4youtubeparcer1kt.base.BaseViewModel
import com.geektech.less4youtubeparcer1kt.model.playlistItems.PlaylistItems
import com.geektech.less4youtubeparcer1kt.network.result.Resource
import com.geektech.less4youtubeparcer1kt.ui.Repository

class DetailedViewModel(private val repository: Repository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun loadPlaylistItems(id: String): LiveData<Resource<PlaylistItems?>> {
        return repository.loadPlaylistItems(id)
    }
}