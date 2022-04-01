package com.youtube.youtubeparcer.data.network

import com.youtube.youtubeparcer.data.model.playlist.PlayList
import com.youtube.youtubeparcer.data.model.playlistItems.PlaylistItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeAPI {

    @GET("playlists")
    suspend fun fetchAllPlayLists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlayList>

    @GET("playlistItems")
    suspend fun loadPlaylistItems(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlaylistItems>
}
