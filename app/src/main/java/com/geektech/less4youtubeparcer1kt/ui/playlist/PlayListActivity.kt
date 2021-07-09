package com.geektech.less4youtubeparcer1kt.ui.playlist

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.gone
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.geektech.less4youtubeparcer1kt.extensions.visible
import com.geektech.less4youtubeparcer1kt.model.playlist.ItemsPlaylist
import com.geektech.less4youtubeparcer1kt.ui.detailed_playlist.DetailedActivity
import com.geektech.less4youtubeparcer1kt.utils.CheckConnectionState
import com.geektech.less4youtubeparcer1kt.utils.Status
import kotlinx.android.synthetic.main.activity_play_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListActivity : BaseActivity(R.layout.activity_play_list) {

    private val list = mutableListOf<ItemsPlaylist>()
    private val playListAdapter: PlayListAdapter by lazy {
        PlayListAdapter(
            this,
            list,
            this::onHolderClick
        )
    }
    private val viewModel: MainViewModel by viewModel()

    override fun setUpUI() {

        swipe_refresh_layout.setOnRefreshListener { setupRecyclerView() }
    }

    private fun setupRecyclerView() {
        swipe_refresh_layout.isRefreshing = true
        recycler_view.apply {
            swipe_refresh_layout.isRefreshing = false
            layoutManager = LinearLayoutManager(this@PlayListActivity)
            adapter = playListAdapter
        }
    }

    override fun setLiveData() {

        viewModel.loading.observe(this, {
            progress_bar.visible = it
        })

        swipe_refresh_layout.isRefreshing = true
        viewModel.loadAllPlaylist().observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    response?.data?.items.let {
                        it?.let { it1 -> list.addAll(it1) }
                        swipe_refresh_layout.isRefreshing = false
                        viewModel.loading.postValue(false)
                    }
                    setupRecyclerView()
                    playListAdapter.notifyDataSetChanged()

                }
                Status.ERROR -> response.message?.let { toast(it) }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        })
    }

    override fun showConnectionState() {

        val ccs = CheckConnectionState(application)
        ccs.observe(this, {
            swipe_refresh_layout.isRefreshing = it
            tv_no_internet.gone = it
            image_ccs.gone = it
            button.gone = it
            recycler_view.visible = it
            if (recycler_view.visible)
                swipe_refresh_layout.isRefreshing = false
        })
    }

    private fun onHolderClick(itemsPlaylist: ItemsPlaylist) {
        val intent = Intent(this, DetailedActivity::class.java)
            .putExtra(Constants.ITEMS_PLAYLIST, itemsPlaylist)
        startActivity(intent)
    }
}