package com.youtube.youtubeparcer.ui.detailed_playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.youtube.youtubeparcer.base.BaseViewModel
import com.youtube.youtubeparcer.data.model.playlistItems.PlaylistItems
import com.youtube.youtubeparcer.data.network.result.Resource
import com.youtube.youtubeparcer.ui.Repository

class DetailedViewModel(private val repository: Repository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun loadPlaylistItems(id: String): LiveData<Resource<PlaylistItems?>> {
        return repository.loadPlaylistItems(id)
    }
}