package com.importrandom.cuehall

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Bookings : AppCompatActivity() {

    private lateinit var footer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookings_page)

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

        // BUTTONS
        val accButton = findViewById<Button>(R.id.account_button)
        accButton.setOnClickListener {
            val intent = Intent(this, AccountSettings::class.java)
            startActivity(intent)
        }

        // TABLES BUTTON
        val tablesButton = findViewById<Button>(R.id.tables_button)
        tablesButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)

        }

        // BOOKINGS BUTTON
        val bookingsButton = findViewById<Button>(R.id.bookings_button)
        bookingsButton.setOnClickListener {
            val intent = Intent(this, Bookings::class.java)
            startActivity(intent)
        }

        // Bookings button attributes
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 54f // Adjust the radius here
            setColor(Color.parseColor("#8024242C")) // Semi-transparent color
        }
        bookingsButton.background = drawable
        bookingsButton.setOnClickListener {
            it.animate().alpha(0.7f).setDuration(300).withEndAction {
                it.alpha = 1f
            }.start()
        }

        // FOOTER ANIMATION
        footer = findViewById(R.id.footer)
        footer.post {
            val animator = ObjectAnimator.ofFloat(footer, "translationY", footer.height.toFloat(), 0f)
            animator.duration = 500
            animator.start()
        }

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bookings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}
