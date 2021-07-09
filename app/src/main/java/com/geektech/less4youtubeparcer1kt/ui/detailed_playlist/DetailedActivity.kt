package com.geektech.less4youtubeparcer1kt.ui.detailed_playlist

import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.gone
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.geektech.less4youtubeparcer1kt.extensions.visible
import com.geektech.less4youtubeparcer1kt.model.playlist.ItemsPlaylist
import com.geektech.less4youtubeparcer1kt.model.playlistItems.Items
import com.geektech.less4youtubeparcer1kt.ui.video_player.PlayerActivity
import com.geektech.less4youtubeparcer1kt.utils.CheckConnectionState
import com.geektech.less4youtubeparcer1kt.utils.GetItemDesc
import com.geektech.less4youtubeparcer1kt.utils.Status
import kotlinx.android.synthetic.main.activity_detailed.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedActivity : BaseActivity(R.layout.activity_detailed), GetItemDesc {

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

        toolbar_layout.title = itemsPlaylist.snippet?.title
        tv_detailed_item_count.text =
            (itemsPlaylist.contentDetails?.itemCount + " " + getString(R.string.video_series)).format()

        toolbar_layout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.red_dark))
        toolbar_layout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.black))
        toolbar_layout.setExpandedTitleMargin(40, 0, 0, 310)

        fab.setColorFilter(ContextCompat.getColor(this, R.color.white))
        fab.setOnClickListener { toast(getString(R.string.play_all)) }

        tv_detailed_back.setOnClickListener { finish() }
    }

    override fun setLiveData() {
        viewModel.loading.observe(this, { progress_bar.visible = it })

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
                            playlistItemsAdapter.notifyDataSetChanged()

                        }
                        Status.ERROR -> response.message?.let { toast(it) }

                        Status.LOADING -> viewModel.loading.postValue(true)
                    }
                })
            }
        }
    }

    private fun setupRecyclerView() {
        recycler_view_detailed.apply {
            layoutManager = LinearLayoutManager(this@DetailedActivity)
            adapter = playlistItemsAdapter
        }
    }

    override fun showConnectionState() {

        val ccs = CheckConnectionState(application)
        ccs.observe(this, {
            rl_connection.gone = it
            app_bar.visible = it
            recycler_view_detailed.visible = it
            tv_detailed_item_count.visible = it
            fab.visible = it

        })
    }

    override fun getDesc(items: Items) {
        tv_toolbar_description.text = items.snippet?.description
    }

    private fun onItemClick(items: Items) {
        val intent = Intent(this, PlayerActivity::class.java)
            .putExtra(Constants.ITEMS_DETAILED, items)
        startActivity(intent)
    }
}