package com.youtube.youtubeparcer.utils

import com.youtube.youtubeparcer.data.model.playlistItems.Items

interface GetItemDesc {
    fun getDesc(items: Items)
}