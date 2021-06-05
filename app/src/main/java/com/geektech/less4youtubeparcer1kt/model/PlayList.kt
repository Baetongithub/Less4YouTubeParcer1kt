package com.geektech.less4youtubeparcer1kt.model

data class PlayList(
    var kind: String,
    var items: List<Items>,
)

data class ContentDetails(var itemCount: String)

data class Items(
    var snippet: Snippets,
    var id: String,
    var contentDetails: ContentDetails,
    var thumbnails: Thumbnails
)

data class Snippets(
    var title: String,
    var description: String
)

data class Thumbnails(var url: String)
