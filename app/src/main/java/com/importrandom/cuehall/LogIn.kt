package com.importrandom.cuehall

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class LogIn : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var showPasswordButton: ImageButton
    private var isPasswordVisible = false

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        // Initialize views
        editTextUsername = findViewById(R.id.usernameLogin)
        editTextPassword = findViewById(R.id.passwordLogin)
        buttonLogin = findViewById(R.id.btnLogin)

        editTextPassword = findViewById(R.id.passwordLogin)
        showPasswordButton = findViewById(R.id.btnShowPasswordLogin)

        showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

        // Set click listener for login button
        buttonLogin.setOnClickListener {
            val username =
                editTextUsername.text.toString().trim() // Trim leading/trailing whitespace
            val password = editTextPassword.text.toString().trim()

            // Check if username and password are empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                loginUser(username, password)
            }
        }
    }


    private fun togglePasswordVisibility() {
        val passwordTransformationMethod = when (isPasswordVisible) {
            true -> HideReturnsTransformationMethod.getInstance()
            false -> PasswordTransformationMethod.getInstance()
        }

        editTextPassword.transformationMethod = passwordTransformationMethod

        showPasswordButton.setImageResource(
            when (isPasswordVisible) {
                true -> R.drawable.ic_hide_password
                false -> R.drawable.ic_show_password
    })
}

        private fun loginUser(username: String, password: String) {
            val url = "http://192.168.1.193/cuehall/login.php"

            val formBody: RequestBody = FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(this@LogIn, "Login failed: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(
                                this@LogIn,
                                "Login failed: Unexpected response",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return
                    }

                    val responseData = response.body()?.string()
                    if (responseData == null) {
                        runOnUiThread {
                            Toast.makeText(
                                this@LogIn,
                                "Login failed: Empty response",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return
                    }

                    try {
                        val jsonObject = JSONObject(responseData)
                        val success = jsonObject.getBoolean("success")

                        runOnUiThread {
                            if (success) {
                                val intent = Intent(this@LogIn, MainInterface::class.java)
                                startActivity(intent)
                                finish() // Optional: Close login activity
                            } else {
                                Toast.makeText(
                                    this@LogIn,
                                    "Login failed: Invalid credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(
                                this@LogIn,
                                "Login failed: Invalid response format",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })

            // EDGE TO EDGE DISPLAY
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                val insetsController = window.insetsController
                insetsController?.apply {
                    hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }

            // BUTTONS
            val signUpButton = findViewById<Button>(R.id.btnSignup)
            val forgotPasswordButton = findViewById<Button>(R.id.btnForgotPassword)
            val backButton = findViewById<Button>(R.id.bckButton)


            // SIGNUP BUTTON
            signUpButton.setOnClickListener {
                val intent = Intent(this@LogIn, CreateAccount::class.java)
                startActivity(intent)
            }

            // FORGOT PASS BUTTON
            forgotPasswordButton.setOnClickListener {
                val intent = Intent(this@LogIn, RecoverPassword::class.java)
                startActivity(intent)
            }

            // BACK BUTTON
            backButton.setOnClickListener {
                val intent = Intent(this@LogIn, StartScreen::class.java)
                startActivity(intent)
            }

            // EDGE TO EDGE DISPLAY PADDING
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.log_in)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
                )
                insets
            }
        }
    }





