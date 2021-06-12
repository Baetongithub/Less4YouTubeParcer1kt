package com.geektech.less4youtubeparcer1kt.network

import com.geektech.less4youtubeparcer1kt.model.playlist.PlayList
import com.geektech.less4youtubeparcer1kt.model.playlistItems.PlaylistItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeAPI {

    @GET("playlists")
    suspend fun fetchAllPlayLists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String
    ): Response<PlayList>

    @GET("playlistItems")
    suspend fun loadPlaylistItems(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String
    ): Response<PlaylistItems>
}
