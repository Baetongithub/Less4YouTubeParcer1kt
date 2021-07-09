package com.geektech.less4youtubeparcer1kt.ui.video_player

import android.annotation.SuppressLint
import android.util.SparseArray
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.geektech.less4youtubeparcer1kt.R
import com.geektech.less4youtubeparcer1kt.`object`.Constants
import com.geektech.less4youtubeparcer1kt.base.BaseActivity
import com.geektech.less4youtubeparcer1kt.extensions.toast
import com.geektech.less4youtubeparcer1kt.model.playlistItems.Items
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
        val items = intent.getSerializableExtra(Constants.ITEMS_DETAILED) as Items


        tv_player_title.text = items.snippet?.title
        tv_player_description.text = items.snippet?.description

        tv_player_back.setOnClickListener { finish() }

        tv_player_download.setOnClickListener { toast(tv_player_download.text.toString()) }

        initPlayer()
    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        video_view_player.player = player

        val items = intent.getSerializableExtra(Constants.ITEMS_DETAILED) as Items

        val videoID = items.contentDetails?.videoId
        val url = "https://www.youtube.com/watch?v=${videoID}"

        playVideo(url)
    }

    @SuppressLint("StaticFieldLeak")
    private fun playVideo(url: String) = object : YouTubeExtractor(this) {
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        playerView?.let {
            WindowInsetsControllerCompat(window, it).let { controller ->
                controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
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