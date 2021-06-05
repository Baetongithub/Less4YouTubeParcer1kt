package com.geektech.less4youtubeparcer1kt.ui

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.model.Items
import com.geektech.less4youtubeparcer1kt.utils.CheckConnectionState
import com.geektech.less4youtubeparcer1kt.utils.OnItemClickListener
import kotlinx.android.synthetic.main.activity_play_list.*

class PlayListActivity : BaseActivity(R.layout.activity_play_list), OnItemClickListener {

    private val list = mutableListOf<Items>()
    private var playListAdapter: PlayListAdapter? = null
    private var viewModel: MainViewModel? = null

    override fun setUpUI() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        swipe_refresh_layout.setOnRefreshListener { setupRecyclerView() }
    }

    private fun setupRecyclerView() {
        swipe_refresh_layout.isRefreshing = true
        playListAdapter = PlayListAdapter(this, list, this)
        recycler_view.apply {
            swipe_refresh_layout.isRefreshing = false
            layoutManager = LinearLayoutManager(this@PlayListActivity)
            adapter = playListAdapter
        }
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun setLiveData() {
        swipe_refresh_layout.isRefreshing = true
        viewModel?.loadAllPlaylist()?.observe(this, { response ->
            response?.items?.let {
                swipe_refresh_layout.isRefreshing = false
                list.addAll(it)
            }
            setupRecyclerView()
            playListAdapter?.notifyDataSetChanged()
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

    override fun onClick(items: Items) {
        val intent = Intent(this, MainActivity::class.java)
            .putExtra(Constants.ID, items.id)
        startActivity(intent)
    }
}