package com.importrandom.cuehall

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartScreen : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        // EDGE TO EDGE DISPLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val insetsController = window.insetsController
            insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }

        // RESERVE NOW BUTTON
        val reserveNow = findViewById<Button>(R.id.reserve_now)
        reserveNow.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        // VIDEO BG
        videoView = findViewById(R.id.videobackground)
        val uri = Uri.parse("android.resource://${packageName}/${R.raw.startscreenbg}")
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        videoView.start()

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.start_screen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.start()
    }
}
