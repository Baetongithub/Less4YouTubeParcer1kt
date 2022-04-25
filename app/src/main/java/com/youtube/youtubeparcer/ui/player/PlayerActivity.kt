package com.youtube.youtubeparcer.ui.player

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.SparseArray
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.youtube.youtubeparcer.R
import com.youtube.youtubeparcer.base.BaseActivity
import com.youtube.youtubeparcer.data.model.playlistItems.Items
import com.youtube.youtubeparcer.databinding.ActivityPlayerBinding
import com.youtube.youtubeparcer.extensions.toast
import com.youtube.youtubeparcer.utils.Constants

class PlayerActivity : BaseActivity<ActivityPlayerBinding>(ActivityPlayerBinding::inflate) {

    private var playerView: StyledPlayerView? = null
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun setUpUI() {
        val items = intent.getSerializableExtra(Constants.ITEMS_DETAILED) as Items

        val videoID = items.contentDetails?.videoId
        val url = Constants.YT_WATCH_LINK + videoID

        vb.tvPlayerTitle.text = items.snippet?.title
        vb.tvPlayerDescription.text = items.snippet?.description

        vb.tvPlayerBack.setOnClickListener { finish() }

        vb.tvBtnDownload.setOnClickListener {

            object : YouTubeExtractor(this) {
                @SuppressLint("ServiceCast")
                override fun onExtractionComplete(
                    ytFiles: SparseArray<YtFile>?,
                    videoMeta: VideoMeta?
                ) {
                    if (ytFiles != null) {
                        val tag = 22
                        val downloadUrl = ytFiles[tag].url
                        val downloadManager: DownloadManager =
                            getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        val request: DownloadManager.Request =
                            DownloadManager.Request(
                                Uri.parse(downloadUrl)
                            )
                        request.apply {
                            setTitle(videoMeta?.title)
                            setDescription(videoMeta?.shortDescription)
                            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            setDestinationInExternalPublicDir(
                                Environment.DIRECTORY_DOWNLOADS,
                                "Downloads"
                            )
                        }
                        downloadManager.enqueue(request)
                    }
                }

            }.extract(url)

            toast(getString(R.string.downloading))
        }

        initPlayer()
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(this).build()
        vb.videoViewPlayer.player = player

        val items = intent.getSerializableExtra(Constants.ITEMS_DETAILED) as Items

        val videoID = items.contentDetails?.videoId
        val url = Constants.YT_WATCH_LINK + videoID

        playVideo(url)
    }

    @SuppressLint("StaticFieldLeak")
    private fun playVideo(url: String) = object : YouTubeExtractor(this) {
        override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, videoMeta: VideoMeta?) {
            val videoTag = 18 //Medium Quality - 480x360 MP4 (будет возможность выбор тег для скач)
            val audioTag = 140 // Audio tag for m4a

            val audioSource: MediaSource =
                ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                    .createMediaSource(MediaItem.fromUri(ytFiles!![audioTag].url))
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

    }.extract(url)

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
            currentWindow = player!!.currentMediaItemIndex
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

    override fun checkConnectionState() {

    }
}