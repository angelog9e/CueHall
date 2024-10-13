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

class VIPRoomsForm : AppCompatActivity() {

    private val REQUEST_CODE_TIME_PAGE = 1 // Request code for VIPTimeTable

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vip_rooms_page)

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
            val intent = Intent(this, VIPRooms::class.java)
            startActivity(intent)
        }

        // TIME BUTTON
        val timeButton = findViewById<Button>(R.id.time_btn)
        timeButton.setOnClickListener {
            val intent = Intent(this, VIPTimeTable::class.java)
            intent.putExtra("ROOM_NUMBER", getRoomNumber())
            startActivityForResult(intent, REQUEST_CODE_TIME_PAGE)
        }

        // PROCEED BUTTON
        val proceedButton: Button = findViewById(R.id.proceed_button)
        proceedButton.setOnClickListener {
            val roomNumber = getRoomNumber()
            val popupTcVip = Popup_tc_vip.newInstance(roomNumber)
            popupTcVip.show(supportFragmentManager, "popup_tc_vip")
        }

        val roomNumber = intent.getIntExtra("ROOM_NUMBER", -1)
        displayRoomNumber(roomNumber)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TIME_PAGE && resultCode == RESULT_OK) {
            val roomNumber = data?.getIntExtra("ROOM_NUMBER", -1) ?: -1
            displayRoomNumber(roomNumber)
        }
    }

    private fun displayRoomNumber(roomNumber: Int) {
        val tableTextView = findViewById<TextView>(R.id.room_no)
        if (roomNumber != -1) {
            tableTextView.text = "VIP ROOM# $roomNumber"
        }
    }

    private fun getRoomNumber(): Int {
        return intent.getIntExtra("ROOM_NUMBER", -1)
    }
}
