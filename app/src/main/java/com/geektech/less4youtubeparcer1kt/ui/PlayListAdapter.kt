package com.geektech.less4youtubeparcer1kt.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.extensions.glide
import com.geektech.less4youtubeparcer1kt.model.Items
import com.geektech.less4youtubeparcer1kt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlayListAdapter(
    private var context: Context,
    private val list: MutableList<Items> = mutableListOf(),
    private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PlayListAdapter.TheViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return TheViewHolder(view, onItemClickListener, context)
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TheViewHolder(
        itemView: View,
        private var onItemClickListener: OnItemClickListener,
        private var context: Context
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun onBind(items: Items) {
            itemView.it_tv_title.text = items.snippet.title
            itemView.it_tv_video_series.text =
                items.contentDetails.itemCount + " " + context.getString(R.string.video_series)
            // пока что-то не могу получить картинки
            //itemView.it_image_view.glide(items.thumbnails.url)
            itemView.it_tv_playlist.text = context.getString(R.string.playlist)

            itemView.setOnClickListener { onItemClickListener.onClick(items) }
        }
    }
}