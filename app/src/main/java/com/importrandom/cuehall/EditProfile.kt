package com.importrandom.cuehall

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

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
        val backButton = findViewById<Button>(R.id.back_button)
        val saveButton = findViewById<Button>(R.id.save_btn)

        backButton.setOnClickListener {
            finish()
        }

        // SAVE BUTTON
        saveButton.setOnClickListener {
            val intent = Intent(this, AccountSettings::class.java)
            startActivity(intent)
        }

        val showPasswordButton1 = findViewById<ImageButton>(R.id.show_password_button1)
        val showPasswordButton2 = findViewById<ImageButton>(R.id.show_password_button2)
        val passwordInput1 = findViewById<EditText>(R.id.current_password_input)
        val passwordInput2 = findViewById<EditText>(R.id.new_password_input)

        fun togglePasswordVisibility(passwordInput: EditText, showPasswordButton: ImageButton) {
            var isPasswordVisible = false

            showPasswordButton.setOnClickListener {
                if (isPasswordVisible) {
                    // Hide password
                    passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    showPasswordButton.setImageResource(R.drawable.ic_show_password) // Show icon
                } else {
                    // Show password
                    passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    showPasswordButton.setImageResource(R.drawable.ic_hide_password) // Hide icon
                }
                // Move cursor to the end of the text
                passwordInput.setSelection(passwordInput.text.length)
                isPasswordVisible = !isPasswordVisible
            }
        }

        togglePasswordVisibility(passwordInput1, showPasswordButton1)
        togglePasswordVisibility(passwordInput2, showPasswordButton2)



        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile_page)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
