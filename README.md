# Dependency

```xml
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-android-extensions'
}

android {
    compileSdkVersion 30
    buildToolsVersion "30.0.2"

    defaultConfig {
        applicationId "com.paigesoftware.exoplayer"
        minSdkVersion 26
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {

    def exoplayer_version = "r2.4.0"

    implementation "com.google.android.exoplayer:exoplayer:$exoplayer_version"
    implementation "com.google.android.exoplayer:exoplayer-core:$exoplayer_version"
    implementation "com.google.android.exoplayer:exoplayer-dash:$exoplayer_version"
    implementation "com.google.android.exoplayer:exoplayer-ui:$exoplayer_version"
    implementation "com.google.android.exoplayer:exoplayer-hls:$exoplayer_version"
    implementation "com.google.android.exoplayer:exoplayer-smoothstreaming:$exoplayer_version"

    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.3.2'
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.2.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

}
```

# Manifest - Configuration Change Handling

```xml
<activity android:name=".MainActivity"
    android:configChanges="orientation|screenSize">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

# XML

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.google.android.exoplayer2.ui.SimpleExoPlayerView
        android:id="@+id/exoplayerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintL
```

# Activity

```kotlin
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
```