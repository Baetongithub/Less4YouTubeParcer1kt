package com.geektech.less4youtubeparcer1kt.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geektech.less4youtubeparcer1kt.BuildConfig.API_KEY
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseViewModel
import com.geektech.less4youtubeparcer1kt.model.PlayList
import com.geektech.less4youtubeparcer1kt.network.RetrofitClient
import com.geektech.less4youtubeparcer1kt.network.YouTubeAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : BaseViewModel() {

    private var youTubeAPI: YouTubeAPI = RetrofitClient.create()

    fun loadAllPlaylist(): LiveData<PlayList?> {
        return fetchAllPlayList()
    }

    private fun fetchAllPlayList(): LiveData<PlayList?> {
        val data = MutableLiveData<PlayList?>()

        youTubeAPI.fetchAllPlayLists(API_KEY, Constants.PART, Constants.CHANNEL_ID)
            .enqueue(object : Callback<PlayList?> {
                override fun onResponse(call: Call<PlayList?>, response: Response<PlayList?>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PlayList?>, t: Throwable) {
                    // 404 - не найдено, 403 - токен истек, 401 - нет доступа
                }
            })
        return data
    }
}