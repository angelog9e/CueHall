package com.importrandom.cuehall

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AccountSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_settings)

        // EDGE TO EDGE DISPLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val insetsController = window.insetsController
            insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

       // BUTTONS
        val logOutButton = findViewById<Button>(R.id.logout_button)
        val backButton = findViewById<Button>(R.id.bckButton)
        val accountButton = findViewById<Button>(R.id.account_button)

        // EDIT PROFILE BUTTON
        accountButton.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        // ABOUT US BUTTON
        val aboutUsButton: Button = findViewById(R.id.about_us_button)
        aboutUsButton.setOnClickListener {
            val popup =
                AboutUs() // Assuming AboutUs is a Fragment or a DialogFragment
            popup.show(supportFragmentManager, "popup_tag")
        }

        // CONTACT US BUTTON
        val helpButton: Button = findViewById(R.id.help_button)
        helpButton.setOnClickListener {
            val popup = ContactUs()
            popup.show(supportFragmentManager, "popup_tag")
        }

        // LOGOUT BUTTON
        logOutButton.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        // BACK BUTTON
        backButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)
        }

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account_settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
