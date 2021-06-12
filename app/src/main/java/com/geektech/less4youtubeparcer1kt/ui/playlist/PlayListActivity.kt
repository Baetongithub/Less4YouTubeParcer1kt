package com.geektech.less4youtubeparcer1kt.ui.playlist

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.geektech.less4youtubeparcer1kt.extensions.visible
import com.geektech.less4youtubeparcer1kt.model.playlist.Items
import com.geektech.less4youtubeparcer1kt.ui.deatiled_playlist.DetailedActivity
import com.geektech.less4youtubeparcer1kt.utils.CheckConnectionState
import com.geektech.less4youtubeparcer1kt.utils.Status
import kotlinx.android.synthetic.main.activity_play_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListActivity : BaseActivity(R.layout.activity_play_list) {

    private val list = mutableListOf<Items>()
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
        ccs.observe(this, { isConnected ->
            if (isConnected) {
                swipe_refresh_layout.isRefreshing = true
                tv_no_internet.visibility = GONE
                image_ccs.visibility = GONE
                button.visibility = GONE
                recycler_view.visibility = VISIBLE
                if (recycler_view.visibility == VISIBLE)
                    swipe_refresh_layout.isRefreshing = false

            } else {
                swipe_refresh_layout.isRefreshing = false
                tv_no_internet.visibility = VISIBLE
                image_ccs.visibility = VISIBLE
                button.visibility = VISIBLE
                recycler_view.visibility = GONE
            }
        })
    }

    private fun onHolderClick(items: Items) {
        val intent = Intent(this, DetailedActivity::class.java)
            .putExtra(Constants.KEY_TITLE, items.snippet.title)
            .putExtra(Constants.KEY_ITEM_COUNT, items.contentDetails.itemCount)
            .putExtra(Constants.ID, items.id)
        startActivity(intent)
    }
}