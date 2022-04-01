package com.youtube.youtubeparcer.ui.detailed_playlist

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youtube.youtubeparcer.databinding.ItemPlaylistBinding
import com.youtube.youtubeparcer.data.model.playlistItems.Items
import com.youtube.youtubeparcer.extensions.glide
import com.youtube.youtubeparcer.utils.GetItemDesc

class PlaylistItemsAdapter(
    private val getItemDesc: GetItemDesc,
    private val list: MutableList<Items> = mutableListOf(),
    private val onItemClick: (items: Items) -> Unit
) : RecyclerView.Adapter<PlaylistItemsAdapter.TheViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), getItemDesc
        )
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.onBind(list[position])

        holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TheViewHolder(
        private val itemPlaylistBinding: ItemPlaylistBinding,
        private var getItemDesc: GetItemDesc
    ) :
        RecyclerView.ViewHolder(itemPlaylistBinding.root) {
        fun onBind(items: Items) {
            itemPlaylistBinding.view1.visibility = GONE
            itemPlaylistBinding.tvTitle.text = items.snippet?.title
            val date: String? = items.snippet?.publishedAt?.substring(0, 10)
            itemPlaylistBinding.tvVideoSeries.text = date
            itemPlaylistBinding.itImageView.glide(items.snippet?.thumbnails?.maxres?.url)

            getItemDesc.getDesc(items)
        }
    }
}