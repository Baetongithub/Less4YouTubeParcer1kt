package com.youtube.youtubeparcer.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.glide(url: String?) {
    if (url != null) Glide.with(this).load(url).into(this)
}

fun ViewGroup.inflate(res: Int): View {
    return LayoutInflater.from(this.context).inflate(res, this, false)
}

var View.invisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

var View.gone: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }