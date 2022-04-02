package com.youtube.youtubeparcer.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youtube.youtubeparcer.databinding.ItemPlaylistBinding
import com.youtube.youtubeparcer.R
import com.youtube.youtubeparcer.data.model.playlist.ItemsPlaylist
import com.youtube.youtubeparcer.extensions.glide
import kotlin.reflect.KFunction1

class PlayListAdapter(
    private val list: MutableList<ItemsPlaylist>,
    private val onHolderClick: KFunction1<ItemsPlaylist, Unit>
) : RecyclerView.Adapter<PlayListAdapter.TheViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
        return TheViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
        holder.onBind(list[position])

        holder.itemView.setOnClickListener { onHolderClick(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TheViewHolder(
        private val playListBinding: ItemPlaylistBinding,
        private var context: Context
    ) : RecyclerView.ViewHolder(playListBinding.root) {

        fun onBind(itemsPlaylist: ItemsPlaylist) {
            playListBinding.tvTitle.text = itemsPlaylist.snippet?.title
            playListBinding.tvVideoSeries.text =
                (itemsPlaylist.contentDetails?.itemCount + " " + context.getString(R.string.video_series))

            playListBinding.itImageView.glide(itemsPlaylist.snippet?.thumbnails?.maxres?.url)
            playListBinding.itTvPlaylist.text = context.getString(R.string.playlist)
        }
    }
}