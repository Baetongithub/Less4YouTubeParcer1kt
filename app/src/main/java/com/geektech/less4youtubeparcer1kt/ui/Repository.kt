package com.geektech.less4youtubeparcer1kt.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.geektech.less4youtubeparcer1kt.BuildConfig.API_KEY
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.model.playlist.PlayList
import com.geektech.less4youtubeparcer1kt.model.playlistItems.PlaylistItems
import com.geektech.less4youtubeparcer1kt.network.YouTubeAPI
import com.geektech.less4youtubeparcer1kt.network.result.Resource
import kotlinx.coroutines.Dispatchers

class Repository(private val youTubeAPI: YouTubeAPI) {

    fun fetchAllPlayList(): LiveData<Resource<PlayList?>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        val response = youTubeAPI.fetchAllPlayLists(API_KEY, Constants.PART, Constants.CHANNEL_ID)
        emit(
            if (response.isSuccessful) Resource.success(response.body())
            else Resource.error(response.message(), response.body(), response.code())
        )
    }

    fun loadPlaylistItems(id: String): LiveData<Resource<PlaylistItems?>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val response = youTubeAPI.loadPlaylistItems(API_KEY, Constants.PART, id)
            emit(
                if (response.isSuccessful) Resource.success(response.body())
                else Resource.error(response.message(), response.body(), response.code())
            )
        }
}