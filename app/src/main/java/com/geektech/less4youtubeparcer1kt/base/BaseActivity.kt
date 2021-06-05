package com.geektech.less4youtubeparcer1kt.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private var layout: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

        showConnectionState()
        setUpUI()
        setLiveData()
    }

    abstract fun setUpUI() // инициализация все view и работа с ними

    abstract fun setLiveData() // инициализация все LiveData

    abstract fun showConnectionState()
}