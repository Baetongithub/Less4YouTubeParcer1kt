package com.youtube.youtubeparcer.ui.main

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtube.youtubeparcer.utils.Constants
import com.youtube.youtubeparcer.base.BaseActivity
import com.youtube.youtubeparcer.databinding.ActivityPlayListBinding
import com.youtube.youtubeparcer.extensions.toast
import com.youtube.youtubeparcer.data.model.playlist.ItemsPlaylist
import com.youtube.youtubeparcer.ui.detailed_playlist.DetailedActivity
import com.youtube.youtubeparcer.utils.CheckConnectionState
import com.youtube.youtubeparcer.data.network.result.Status
import com.youtube.youtubeparcer.extensions.gone
import com.youtube.youtubeparcer.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListActivity : BaseActivity<ActivityPlayListBinding>(ActivityPlayListBinding::inflate) {

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

        //vb.swipeRefreshLayout.setOnRefreshListener { setupRecyclerView() }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        //   vb.swipeRefreshLayout.isRefreshing = true
        vb.recyclerView.apply {
            // visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context)
            adapter = playListAdapter

        }
    }

    override fun setLiveData() {

        viewModel.loading.observe(this, {
            vb.progressBar.visible = it
        })

        //  vb.swipeRefreshLayout.isRefreshing = true
        viewModel.loadAllPlaylist().observe(this, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    if (response.data?.items != null)
                        list.addAll(response.data.items)
                    viewModel.loading.postValue(false)
//                    list.add(ItemsPlaylist(id = "Some String значение"))
//                    list.add(ItemsPlaylist(id = "Some String значение"))
//                    list.add(ItemsPlaylist(id = "Some String значение"))

                    setupRecyclerView()
                }
                Status.ERROR -> response.message?.let { toast(it) }

                Status.LOADING -> {

                    viewModel.loading.postValue(true)
                }
            }
        })
    }

    override fun showConnectionState() {

        val ccs = CheckConnectionState(application)
        ccs.observe(this, {
            // vb.swipeRefreshLayout.isRefreshing = it
            vb.tvNoInternet.gone = it
            vb.imageCcs.gone = it
            vb.button.gone = it
            vb.recyclerView.visible = it
//            if (vb.recyclerView.visible)
////                vb.swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun onHolderClick(itemsPlaylist: ItemsPlaylist) {
        val intent = Intent(this, DetailedActivity::class.java)
            .putExtra(Constants.ITEMS_PLAYLIST, itemsPlaylist)
        startActivity(intent)
    }
}