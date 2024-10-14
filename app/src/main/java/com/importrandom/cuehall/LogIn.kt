package com.importrandom.cuehall

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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

class LogIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

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
        val loginButton = findViewById<Button>(R.id.login_button)
        val signUpButton = findViewById<Button>(R.id.sign_up)
        val forgotPasswordButton = findViewById<Button>(R.id.forgot_pass)
        val backButton = findViewById<Button>(R.id.back_button)

        // LOGIN BUTTON
        loginButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)
        }

        // SIGNUP BUTTON
        signUpButton.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        // FORGOT PASS BUTTON
        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, RecoverPassword::class.java)
            startActivity(intent)
        }

        // BACK BUTTON
        backButton.setOnClickListener {
            val intent = Intent(this, StartScreen::class.java)
            startActivity(intent)
        }

        // FIND VIEWS
        val passwordInput = findViewById<EditText>(R.id.login_password_input)
        val showPasswordButton = findViewById<ImageButton>(R.id.show_password_button)

// Initially hide the showPasswordButton
        showPasswordButton.visibility = View.GONE

// TOGGLE PASSWORD VISIBILITY
        var isPasswordVisible = false

        showPasswordButton.setOnClickListener {
            if (isPasswordVisible) {
                // Hide password
                passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordButton.setImageResource(R.drawable.ic_show_password)
            } else {
                // Show password
                passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordButton.setImageResource(R.drawable.ic_hide_password)
            }
            // Move cursor to the end of the text
            passwordInput.setSelection(passwordInput.text.length)

            isPasswordVisible = !isPasswordVisible
        }

            // TEXT WATCHER TO SHOW BUTTON WHEN PASSWORD IS TYPED
        passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    showPasswordButton.visibility = View.GONE
                } else {
                    showPasswordButton.visibility = View.VISIBLE
                }
            }
        })

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.log_in)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
