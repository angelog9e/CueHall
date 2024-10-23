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

@Suppress("DEPRECATION")
class MainInterface : AppCompatActivity() {

    private lateinit var footer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_interface)

        // EDGE TO EDGE DISPLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val insetsController = window.insetsController
            insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        // STANDARD TABLE BUTTONS

        val table1Button = findViewById<Button>(R.id.table1_btn)
        table1Button.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", 1)
            startActivity(intent)
        }

        val table2Button = findViewById<Button>(R.id.table2_btn)
        table2Button.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", 2)
            startActivity(intent)
        }

        val table3Button = findViewById<Button>(R.id.table3_btn)
        table3Button.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", 3)
            startActivity(intent)
        }

        val table4Button = findViewById<Button>(R.id.table4_btn)
        table4Button.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", 4)
            startActivity(intent)
        }

        val table5Button = findViewById<Button>(R.id.table5_btn)
        table5Button.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", 5)
            startActivity(intent)
        }

        val table6Button = findViewById<Button>(R.id.table6_btn)
        table6Button.setOnClickListener {
            val intent = Intent(this, StandardTablesForm::class.java)
            intent.putExtra("TABLE_NUMBER", 6)
            startActivity(intent)
        }

        // ACCOUNT SETTINGS BUTTON
        val accButton = findViewById<Button>(R.id.account_button)
        accButton.setOnClickListener {
            val intent = Intent(this, AccountSettings::class.java)
            startActivity(intent)
        }

        // VIP ROOM BUTTON
        val vipRoomsButton = findViewById<Button>(R.id.vip_btn)
        vipRoomsButton.setOnClickListener {
            val intent = Intent(this, VIPRooms::class.java)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
        // STANDARD TABLES BUTTON
        val standardTablesButton = findViewById<Button>(R.id.standard_table_btn)
        standardTablesButton.setOnClickListener{

        }
        // Standard Table Attributes
        val drawable1 = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 54f // Adjust the radius here
            setColor(Color.parseColor("#8024242C"))
        }
        standardTablesButton.background = drawable1
        standardTablesButton.setOnClickListener {
            it.animate().alpha(0.7f).setDuration(300).withEndAction {
                it.alpha = 1f
            }.start()
        }

        // FOOTER BOOKINGS BUTTON
        val footerBookingsButton = findViewById<Button>(R.id.footer_bookings_button)
        footerBookingsButton.setOnClickListener {
            val intent = Intent(this, Bookings::class.java)
            startActivity(intent)

        }

        // FOOTER TABLES BUTTON
        val footerTablesButton = findViewById<Button>(R.id.footer_tables_button)
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
