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
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class VIPRooms : AppCompatActivity() {

    private lateinit var footer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vip_rooms)

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

        // VIP ROOMS BUTTONS
        val room1Button = findViewById<Button>(R.id.room1_btn)
        room1Button.setOnClickListener {
            val intent = Intent(this, VIPRoomsForm::class.java)
            intent.putExtra("ROOM_NUMBER", 1)
            startActivity(intent)
        }

        val room2Button = findViewById<Button>(R.id.room2_btn)
        room2Button.setOnClickListener {
            val intent = Intent(this, VIPRoomsForm::class.java)
            intent.putExtra("ROOM_NUMBER", 2)
            startActivity(intent)
        }

        val room3Button = findViewById<Button>(R.id.room3_btn)
        room3Button.setOnClickListener {
            val intent = Intent(this, VIPRoomsForm::class.java)
            intent.putExtra("ROOM_NUMBER", 3)
            startActivity(intent)
        }

        val room4Button = findViewById<Button>(R.id.room4_btn)
        room4Button.setOnClickListener {
            val intent = Intent(this, VIPRoomsForm::class.java)
            intent.putExtra("ROOM_NUMBER", 4)
            startActivity(intent)
        }

        val room5Button = findViewById<Button>(R.id.room5_btn)
        room5Button.setOnClickListener {
            val intent = Intent(this, VIPRoomsForm::class.java)
            intent.putExtra("ROOM_NUMBER", 5)
            startActivity(intent)
        }

        val room6Button = findViewById<Button>(R.id.room6_btn)
        room6Button.setOnClickListener {
            val intent = Intent(this, VIPRoomsForm::class.java)
            intent.putExtra("ROOM_NUMBER", 6)
            startActivity(intent)
        }

        // ACCOUNT SETTINGS Button
        val accButton = findViewById<Button>(R.id.account_button)
        accButton.setOnClickListener {
            val intent = Intent(this, AccountSettings::class.java)
            startActivity(intent)
        }

        // VIP ROOM BUTTON
        val vipRoomsButton = findViewById<Button>(R.id.vip_btn)
        vipRoomsButton.setOnClickListener{
            val intent = Intent(this, VIPRooms::class.java)
            startActivity(intent)

        }
        // Vip Room Attributes
        val drawable2 = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 54f // Adjust the radius here
            setColor(Color.parseColor("#8024242C"))
        }
        vipRoomsButton.background = drawable2
        vipRoomsButton.setOnClickListener {
            it.animate().alpha(0.7f).setDuration(300).withEndAction {
                it.alpha = 1f
            }.start()
        }

        // STANDARD TABLES BUTTON
        val standardTablesButton = findViewById<Button>(R.id.standard_table_btn)
        standardTablesButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // FOOTER BOOKINGS BUTTON
        val footerBookingsButton = findViewById<Button>(R.id.footer_bookings_button)
        footerBookingsButton.setOnClickListener {
            val intent = Intent(this, Bookings::class.java)
            startActivity(intent)
        }

        // FOOTER TABLES BUTTON
        val footerTablesButton = findViewById<Button>(R.id.footer_tables_button)

        footerTablesButton.text = "ROOMS"

        val roomsIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_rooms, null)
        footerTablesButton.setCompoundDrawablesWithIntrinsicBounds(null, roomsIcon, null, null)

        footerTablesButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)

        }
        // Tables button attributes
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 54f // Adjust the radius here
            setColor(Color.parseColor("#8024242C"))
        }
        footerTablesButton.background = drawable
        footerTablesButton.setOnClickListener {
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

    }
}
