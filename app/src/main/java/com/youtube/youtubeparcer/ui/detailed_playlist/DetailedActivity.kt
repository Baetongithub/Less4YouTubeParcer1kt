package com.youtube.youtubeparcer.ui.detailed_playlist

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtube.youtubeparcer.databinding.ActivityDetailedBinding
import com.youtube.youtubeparcer.R
import com.youtube.youtubeparcer.utils.Constants
import com.youtube.youtubeparcer.base.BaseActivity
import com.youtube.youtubeparcer.extensions.toast
import com.youtube.youtubeparcer.data.model.playlist.ItemsPlaylist
import com.youtube.youtubeparcer.data.model.playlistItems.Items
import com.youtube.youtubeparcer.ui.player.PlayerActivity
import com.youtube.youtubeparcer.utils.CheckConnectionState
import com.youtube.youtubeparcer.utils.GetItemDesc
import com.youtube.youtubeparcer.data.network.result.Status
import com.youtube.youtubeparcer.extensions.gone
import com.youtube.youtubeparcer.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedActivity : BaseActivity<ActivityDetailedBinding>(ActivityDetailedBinding::inflate), GetItemDesc {

    private val list = mutableListOf<Items>()
    private val playlistItemsAdapter: PlaylistItemsAdapter by lazy {
        PlaylistItemsAdapter(
            this,
            list,
            this::onItemClick
        )
    }

    private val viewModel: DetailedViewModel by viewModel()

    override fun setUpUI() {
        val itemsPlaylist = intent.getSerializableExtra(Constants.ITEMS_PLAYLIST) as ItemsPlaylist

        vb.toolbarLayout.title = itemsPlaylist.snippet?.title
        vb.tvDetailedItemCount.text =
            (itemsPlaylist.contentDetails?.itemCount + " " + getString(R.string.video_series)).format()

        vb.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.red_dark))
        vb.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.black))
        vb.toolbarLayout.setExpandedTitleMargin(40, 0, 0, 310)

        vb.fab.setColorFilter(ContextCompat.getColor(this, R.color.white))
        vb.fab.setOnClickListener { toast(getString(R.string.play_all)) }

        vb.tvDetailedBack.setOnClickListener { finish() }
    }

    override fun setLiveData() {
        viewModel.loading.observe(this, { vb.progressBar.visible = it })

        val itemsPlaylist = intent.getSerializableExtra(Constants.ITEMS_PLAYLIST) as ItemsPlaylist
        val id = itemsPlaylist.id
        id.let { it ->
            if (it != null) {
                viewModel.loadPlaylistItems(it).observe(this, { response ->
                    when (response.status) {
                        Status.SUCCESS -> {
                            response?.data?.items.let {
                                it?.let { it1 -> list.addAll(it1) }
                                viewModel.loading.postValue(false)
                            }
                            setupRecyclerView()

                        }
                        Status.ERROR -> response.message?.let { toast(it) }

                        Status.LOADING -> viewModel.loading.postValue(true)
                    }
                })
            }
        }
    }

    private fun setupRecyclerView() {
        vb.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DetailedActivity)
            adapter = playlistItemsAdapter
        }
    }

    override fun checkConnectionState() {

        val ccs = CheckConnectionState(application)
        ccs.observe(this, {
            vb.rlConnection.gone = it
            vb.appBar.visible = it
            vb.recyclerView.visible = it
            vb.tvDetailedItemCount.visible = it
            vb.fab.visible = it

        })
    }

    override fun getDesc(items: Items) {
        vb.tvToolbarDescription.text = items.snippet?.description
    }

    private fun onItemClick(items: Items) {
        val intent = Intent(this, PlayerActivity::class.java)
            .putExtra(Constants.ITEMS_DETAILED, items)
        startActivity(intent)
    }
}