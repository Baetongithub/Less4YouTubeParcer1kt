package com.youtube.youtubeparcer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.youtube.youtubeparcer.BuildConfig.API_KEY
import com.youtube.youtubeparcer.utils.Constants
import com.youtube.youtubeparcer.data.model.playlist.PlayList
import com.youtube.youtubeparcer.data.model.playlistItems.PlaylistItems
import com.youtube.youtubeparcer.data.network.YouTubeAPI
import com.youtube.youtubeparcer.data.network.result.Resource
import kotlinx.coroutines.Dispatchers

class Repository(private val youTubeAPI: YouTubeAPI) {

    fun fetchAllPlayList(): LiveData<Resource<PlayList?>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val response =
            youTubeAPI.fetchAllPlayLists(API_KEY, Constants.PART, Constants.CHANNEL_ID, 50)
        emit(
            if (response.isSuccessful) Resource.success(response.body())
            else Resource.error(response.message(), response.body(), response.code())
        )
    }

    fun loadPlaylistItems(id: String): LiveData<Resource<PlaylistItems?>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val response = youTubeAPI.loadPlaylistItems(API_KEY, Constants.PART, id, 50)
            emit(
                if (response.isSuccessful) Resource.success(response.body())
                else Resource.error(response.message(), response.body(), response.code())
            )
        }
}