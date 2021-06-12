package com.geektech.less4youtubeparcer1kt.model.playlistItems


data class PlaylistItems(
    var items: List<Items>,
    var id: String
)

data class Items(
    var id: String,
    var playlistId: String,
    var snippet: Snippet,
    var contentDetails: ContentDetails
)

data class Snippet(
    var publishedAt: String,
    var channelId: String,
    var title: String,
    var description: String,
    var thumbnails: Thumbnails,
    var channelTitle: String,
    var playlistId: String,
    var position: Int
)

data class ContentDetails(
    var startAt: String,
    var endAt: String,
    var videoId: String
)

data class Thumbnails(var default: Default, var high: High, var medium: Medium, var maxres: Maxres)

data class Default(
    var url: String,
    var width: Int,
    var height: Int
)

data class High(
    var url: String,
    var width: Int,
    var height: Int
)

data class Medium(
    var url: String,
    var width: Int,
    var height: Int
)

data class Maxres(
    var url: String,
    var width: Int,
    var height: Int
)
