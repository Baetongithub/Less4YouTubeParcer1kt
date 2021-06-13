package com.geektech.less4youtubeparcer1kt.ui.deatiled_playlist

import android.annotation.SuppressLint
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.geektech.less4youtubeparcer1kt.extensions.visible
import com.geektech.less4youtubeparcer1kt.model.playlistItems.Items
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

    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun setUpUI() {
        toolbar_layout.title = intent.getStringExtra(Constants.KEY_TITLE)
        tv_detailed_item_count.text =
            intent.getStringExtra(Constants.KEY_ITEM_COUNT) + " " + getString(R.string.video_series)

        toolbar_layout.setCollapsedTitleTextColor(resources.getColor(R.color.red_dark))
        toolbar_layout.setExpandedTitleColor(resources.getColor(R.color.black))
        toolbar_layout.setExpandedTitleMargin(40, 0, 0, 310)

        fab.setColorFilter(resources.getColor(R.color.white))

        tv_detailed_back.setOnClickListener { finish() }
    }

    override fun setLiveData() {
        viewModel.loading.observe(this, {
            progress_bar.visible = it
        })
        val id = intent.getStringExtra(Constants.ID)
        id?.let { it ->
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

    private fun setupRecyclerView() {
        recycler_view_detailed.apply {
            layoutManager = LinearLayoutManager(this@DetailedActivity)
            adapter = playlistItemsAdapter
        }
    }

    override fun showConnectionState() {

        val ccs = CheckConnectionState(application)
        ccs.observe(this, { isConnected ->
            if (isConnected) {
                rl_connection.visibility = GONE
                app_bar.visibility = VISIBLE
                recycler_view_detailed.visibility = VISIBLE
                tv_detailed_item_count.visibility = VISIBLE
                fab.visibility = VISIBLE
            } else {
                rl_connection.visibility = VISIBLE
                app_bar.visibility = GONE
                recycler_view_detailed.visibility = GONE
                tv_detailed_item_count.visibility = GONE
                fab.visibility = GONE

            }
        })
    }

    private fun onItemClick(items: Items) {
        toast(items.contentDetails.videoId)
    }

    override fun getDesc(items: Items) {
        tv_toolbar_description.text = items.snippet.description
    }
}