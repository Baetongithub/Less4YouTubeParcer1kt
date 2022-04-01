package com.youtube.youtubeparcer.data.model.playlist

import java.io.Serializable

data class PlayList(
    var kind: String,
    var items: List<ItemsPlaylist>,
)

data class ContentDetails(var itemCount: String) : Serializable

data class ItemsPlaylist(
    var snippet: Snippets? = null,
    var id: String? = null,
    var contentDetails: ContentDetails? = null
) : Serializable

data class Snippets(
    var title: String? = null,
    var description: String? = null,
    var thumbnails: Thumbnails? = null,
    var channelId: String? = null,
    var localized: Localized? = null
) : Serializable

data class Localized(
    var title: String? = null,
    var description: String? = null
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