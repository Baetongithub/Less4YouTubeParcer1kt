package com.geektech.less4youtubeparcer1kt.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.geektech.less4youtubeparcer1kt.utils.CheckConnectionState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {

    private var viewModel: MainViewModel? = null

    override fun setUpUI() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val id = intent.getStringExtra(Constants.ID)
        toast(id.toString())
    }

    override fun setLiveData() {

    }

    override fun showConnectionState() {

        val ccs = CheckConnectionState(application)
        ccs.observe(this, { isConnected ->
            if (isConnected) {
                tv_no_internet.visibility = GONE
                image_ccs.visibility = GONE
                button.visibility = GONE
            } else {
                tv_no_internet.visibility = VISIBLE
                image_ccs.visibility = VISIBLE
                button.visibility = VISIBLE
            }
        })
    }
}