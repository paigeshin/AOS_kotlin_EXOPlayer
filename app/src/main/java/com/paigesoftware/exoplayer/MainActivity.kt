package com.paigesoftware.exoplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.URI

class MainActivity : AppCompatActivity() {

    val videoURL = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exoplayer: SimpleExoPlayer

        try {
            val bandWidthMeter = DefaultBandwidthMeter()
            val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWidthMeter))
            exoplayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
            val videoURI = Uri.parse(videoURL)
            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
            val extractorsFactory = DefaultExtractorsFactory()
            val mediaSource = ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)
            exoplayerView.player = exoplayer
            exoplayer.prepare(mediaSource)
            exoplayer.playWhenReady = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}