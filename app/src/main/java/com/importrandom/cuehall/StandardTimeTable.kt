package com.importrandom.cuehall

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StandardTimeTable : AppCompatActivity() {

    private var isAmSelected = true
    private var tableNumber: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standard_time_page)

        tableNumber = intent.getIntExtra("TABLE_NUMBER", -1)

        // EDGE TO EDGE DISPLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
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

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.standard_time_page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // BUTTONS
        val amButton = findViewById<Button>(R.id.am_switch)
        val pmButton = findViewById<Button>(R.id.pm_switch)

        // Set initial states
        updateAmPmButtons()

        // AM BUTTON
        amButton.setOnClickListener {
            isAmSelected = true
            updateAmPmButtons()
        }

        // PM BUTTON
        pmButton.setOnClickListener {
            isAmSelected = false
            updateAmPmButtons()
        }

        // BACK BUTTON
        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("TABLE_NUMBER", tableNumber)
            setResult(RESULT_OK, intent)
            finish()
        }

        // PROCEED BUTTON
        val proceedButton = findViewById<Button>(R.id.proceed_button)
        proceedButton.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", tableNumber)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    // AM AND PM BUTTON ATTRIBUTES
    private fun updateAmPmButtons() {
        val amButton = findViewById<Button>(R.id.am_switch)
        val pmButton = findViewById<Button>(R.id.pm_switch)

        val fadeDuration = 100L

        if (isAmSelected) {
            pmButton.animate().alpha(0f).setDuration(fadeDuration).withEndAction {
                pmButton.background = ContextCompat.getDrawable(this, R.drawable.button_background)

                pmButton.animate().alpha(1f).setDuration(fadeDuration).start()
            }.start()

            amButton.animate().alpha(0f).setDuration(fadeDuration).withEndAction {
                amButton.background = ContextCompat.getDrawable(this, R.drawable.bg_black_sun)

                amButton.animate().alpha(1f).setDuration(fadeDuration).start()
            }.start()
        } else {
            amButton.animate().alpha(0f).setDuration(fadeDuration).withEndAction {
                amButton.background = ContextCompat.getDrawable(this, R.drawable.button_background)

                amButton.animate().alpha(1f).setDuration(fadeDuration).start()
            }.start()

            pmButton.animate().alpha(0f).setDuration(fadeDuration).withEndAction {
                pmButton.background = ContextCompat.getDrawable(this, R.drawable.bg_black_sun)

                pmButton.animate().alpha(1f).setDuration(fadeDuration).start()
            }.start()
        }
    }

}
