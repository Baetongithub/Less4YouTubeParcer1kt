package com.youtube.youtubeparcer.data.model.playlistItems

import java.io.Serializable


data class PlaylistItems(
    var items: List<Items>? = null,
    var id: String? = null
)

data class Items(
    var id: String? = null,
    var playlistId: String? = null,
    var snippet: Snippet? = null,
    var contentDetails: ContentDetails? = null
) : Serializable

data class Snippet(
    var publishedAt: String? = null,
    var channelId: String? = null,
    var title: String? = null,
    var description: String? = null,
    var thumbnails: Thumbnails? = null,
    var channelTitle: String? = null,
    var playlistId: String? = null,
    var position: Int? = null
) : Serializable

data class ContentDetails(
    var startAt: String? = null,
    var endAt: String? = null,
    var videoId: String? = null
) : Serializable

data class Thumbnails(
    var default: Default? = null,
    var high: High? = null,
    var medium: Medium? = null,
    var maxres: Maxres? = null
) : Serializable

data class Default(
    var url: String? = null,
    var width: Int? = null,
    var height: Int? = null
) : Serializable

data class High(
    var url: String? = null,
    var width: Int? = null,
    var height: Int? = null
) : Serializable

data class Medium(
    var url: String? = null,
    var width: Int? = null,
    var height: Int? = null
) : Serializable

data class Maxres(
    var url: String? = null,
    var width: Int? = null,
    var height: Int? = null
) : Serializable