package com.importrandom.cuehall

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class StandardTablesForm : AppCompatActivity() {

    private val REQUEST_CODE_TIME_PAGE = 1 // Request code for VIPTimeTable

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standard_tables_page)

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

        // BACK BUTTON
        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)
        }

        // TIME BUTTON
        val timeButton = findViewById<Button>(R.id.time_btn)
        timeButton.setOnClickListener {
            val intent = Intent(this, StandardTimeTable::class.java)
            intent.putExtra("TABLE_NUMBER", getTableNumber())
            startActivityForResult(intent, REQUEST_CODE_TIME_PAGE)
        }

        // PROCEED BUTTON
        val proceedButton: Button = findViewById(R.id.proceed_button)
        proceedButton.setOnClickListener {
            val roomNumber = getTableNumber()
            val popupTcVip = Popup_tc_standard.newInstance(roomNumber)
            popupTcVip.show(supportFragmentManager, "popup_tc_standard")
        }

        // Retrieve and display table number
        val tableNumber = intent.getIntExtra("TABLE_NUMBER", -1)
        displayTableNumber(tableNumber)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TIME_PAGE && resultCode == RESULT_OK) {
            val tableNumber = data?.getIntExtra("TABLE_NUMBER", -1) ?: -1
            displayTableNumber(tableNumber)
        }
    }

    private fun displayTableNumber(tableNumber: Int) {
        val tableTextView = findViewById<TextView>(R.id.table_no)
        if (tableNumber != -1) {
            tableTextView.text = "TABLE# $tableNumber"
        }
    }

    private fun getTableNumber(): Int {
        return intent.getIntExtra("TABLE_NUMBER", -1)
    }
}
