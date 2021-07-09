package com.geektech.less4youtubeparcer1kt.ui.playlist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.extensions.glide
import com.geektech.less4youtubeparcer1kt.extensions.inflate
import com.geektech.less4youtubeparcer1kt.model.playlist.ItemsPlaylist
import kotlinx.android.synthetic.main.item_playlist.view.*
import kotlin.reflect.KFunction1

class PlayListAdapter(
    private var context: Context,
    private val list: MutableList<ItemsPlaylist> = mutableListOf(),
    private val onHolderClick: KFunction1<ItemsPlaylist, Unit>
) : RecyclerView.Adapter<PlayListAdapter.TheViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(parent.inflate(R.layout.item_playlist), context)
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.onBind(list[position])

        holder.itemView.setOnClickListener { onHolderClick(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TheViewHolder(
        itemView: View,
        private var context: Context
    ) : RecyclerView.ViewHolder(itemView) {

         fun onBind(itemsPlaylist: ItemsPlaylist) {
            itemView.it_tv_title.text = itemsPlaylist.snippet?.title
            itemView.it_tv_video_series.text =
                (itemsPlaylist.contentDetails?.itemCount + " " + context.getString(R.string.video_series)).format()
             itemsPlaylist.snippet?.thumbnails?.maxres?.url?.let { itemView.it_image_view.glide(it) }
            itemView.it_tv_playlist.text = context.getString(R.string.playlist)
        }
    }
}