package com.geektech.less4youtubeparcer1kt.model.playlist

data class PlayList(
    var kind: String,
    var items: List<Items>,
)

data class ContentDetails(var itemCount: String)

data class Items(
    var snippet: Snippets,
    var id: String,
    var contentDetails: ContentDetails
)

data class Snippets(
    var title: String,
    var description: String,
    var thumbnails: Thumbnails,
    var channelId: String,
    var localized: Localized
)

data class Localized(
    var title: String,
    var description: String
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

