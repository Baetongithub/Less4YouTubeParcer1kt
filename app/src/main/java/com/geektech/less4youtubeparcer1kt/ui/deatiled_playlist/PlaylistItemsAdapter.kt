package com.geektech.less4youtubeparcer1kt.ui.deatiled_playlist

import android.annotation.SuppressLint
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.extensions.glide
import com.geektech.less4youtubeparcer1kt.extensions.inflate
import com.geektech.less4youtubeparcer1kt.model.playlistItems.Items
import com.geektech.less4youtubeparcer1kt.utils.GetItemDesc
import kotlinx.android.synthetic.main.item_playlist.view.*

class PlaylistItemsAdapter(
    private val getItemDesc: GetItemDesc,
    private val list: MutableList<Items> = mutableListOf(),
    private val onItemClick: (items: Items) -> Unit
) : RecyclerView.Adapter<PlaylistItemsAdapter.TheViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(parent.inflate(R.layout.item_playlist), getItemDesc)
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.onBind(list[position])

        holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TheViewHolder(itemView: View, var getItemDesc: GetItemDesc) :
        RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun onBind(items: Items) {
            itemView.view1.visibility = GONE
            itemView.it_tv_title.text = items.snippet.title
            val date: String = items.snippet.publishedAt.substring(0, 10)
            itemView.it_tv_video_series.text = date
            itemView.it_image_view.glide(items.snippet.thumbnails.maxres.url)

            getItemDesc.getDesc(items)
        }
    }
}