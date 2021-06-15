package com.geektech.less4youtubeparcer1kt.ui.video_player

import android.annotation.SuppressLint
import android.util.SparseArray
import android.view.View
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity(R.layout.activity_player) {

    private var playerView: PlayerView? = null
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun setUpUI() {
        tv_player_title.text = intent.getStringExtra(Constants.KEY_TITLE_PLAYER)
        tv_player_description.text = intent.getStringExtra(Constants.KEY_DESC_PLAYER)

        tv_player_back.setOnClickListener { finish() }

        tv_player_download.setOnClickListener { toast(tv_player_download.text.toString()) }

        initPlayer()
    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        video_view_player.player = player

        val videoID = intent.getStringExtra(Constants.VIDEO_ID)
        val url = "https://www.youtube.com/watch?v=${videoID}"

        playVideo(url)
    }

    @SuppressLint("StaticFieldLeak")
    private fun playVideo(url: String) {
        object : YouTubeExtractor(this) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>, videoMeta: VideoMeta) {
                val videoTag = 137 // video tag for 1080p MP4
                val audioTag = 140 // Audio tag for m4a
                val audioSource: MediaSource =
                    ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(ytFiles[audioTag].url))
                val videoSource: MediaSource =
                    ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(ytFiles[videoTag].url))
                player!!.setMediaSource(
                    MergingMediaSource(
                        true,
                        videoSource,
                        audioSource
                    ),
                    true
                )
                player!!.prepare()
                player!!.playWhenReady = playWhenReady
                player!!.seekTo(currentWindow, playbackPosition)
            }
        }.extract(url, false, true)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24)
            initPlayer()
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24)
            releasePlayer()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initPlayer()
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        playerView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24)
            releasePlayer()
    }

    override fun setLiveData() {

    }

    override fun showConnectionState() {

    }
}