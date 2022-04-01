package com.youtube.youtubeparcer.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val viewBinding: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    private var binding: VB? = null
    val vb get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBinding.invoke(layoutInflater)
        setContentView(vb.root)
        showConnectionState()
        setUpUI()
        setLiveData()
    }

    abstract fun setUpUI()

    abstract fun setLiveData()

    abstract fun showConnectionState()
}