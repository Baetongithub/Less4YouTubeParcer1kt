package com.geektech.less4youtubeparcer1kt.network

import com.geektech.less4youtubeparcer1kt.model.PlayList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeAPI {

    @GET("playlists")
    fun fetchAllPlayLists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String
    ): Call<PlayList>

}
