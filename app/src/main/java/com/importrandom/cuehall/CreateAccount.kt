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
import android.util.Patterns

class CreateAccount : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextEmail: EditText // Add email field
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button // Change button name

    private lateinit var showPasswordButton: ImageButton
    private var isPasswordVisible = false

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

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

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_account)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // BUTTONS
        val backButton = findViewById<Button>(R.id.bckButton)
        val signInButton = findViewById<Button>(R.id.sign_in_btn)

        backButton.setOnClickListener {
            finish()
        }

        // SIGN IN BUTTON
        signInButton.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        // Initialize views
        editTextUsername = findViewById(R.id.createUsername) // Update IDs if needed
        editTextEmail = findViewById(R.id.createEmail) // Add email field
        editTextPassword = findViewById(R.id.createPassword) // Update IDs if needed
        buttonRegister = findViewById(R.id.btnRegister) // Update button ID

        editTextPassword = findViewById(R.id.createPassword)
        showPasswordButton = findViewById(R.id.btnShowPassword)

        showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

        // Set click listener for register button
        buttonRegister.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val email = editTextEmail.text.toString().trim() // Get email
            val password = editTextPassword.text.toString().trim()

        //Validate Email
            if (isValidEmail(email)) {
                // Email is valid, proceed with registration or login
            } else {
                // Display an error message to the user, e.g.,
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            }

            // Check if username, email, and password are empty
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (email.isEmpty()) {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                registerUser(username, email, password)
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

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun registerUser(username: String, email: String, password: String) {
        val url = "http://192.168.1.193/cuehall/register.php" // Update URL to your register script

        val formBody: RequestBody = FormBody.Builder()
            .add("username", username)
            .add("email", email) // Add email data
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CreateAccount, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@CreateAccount, "Registration failed: Unexpected response", Toast.LENGTH_SHORT).show()
                    }
                    return
                }

                val responseData = response.body()?.string()
                if (responseData == null) {
                    runOnUiThread {
                        Toast.makeText(this@CreateAccount, "Registration failed: Empty response", Toast.LENGTH_SHORT).show()
                    }
                    return
                }

                try {
                    val jsonObject = JSONObject(responseData)
                    val success = jsonObject.getBoolean("success")

                    runOnUiThread {
                        if (success) { Toast.makeText(this@CreateAccount, "Registration successful!", Toast.LENGTH_SHORT).show()
                            // Handle successful registration (e.g., navigate to login)
                        } else {
                            val message =
                                jsonObject.getString("message") // Handle specific error messages
                            Toast.makeText(this@CreateAccount, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@CreateAccount, "Registration failed: Invalid response format", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}



