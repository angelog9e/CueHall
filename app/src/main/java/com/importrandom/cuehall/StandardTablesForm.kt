package com.importrandom.cuehall

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.util.*

class StandardTablesForm : AppCompatActivity() {

    private lateinit var timeButton: Button
    private lateinit var dateButton: Button
    private var selectedDate: String = ""
    private var selectedTime: String = ""
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

        // Initialize buttons
        timeButton = findViewById(R.id.time_btn) // Button to pick time
        dateButton = findViewById(R.id.date_button) // Button to pick date

        // BACK BUTTON
        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)
        }

        // TIME BUTTON
        timeButton.setOnClickListener {
            openTimeRangePicker()
        }

        // DATE BUTTON
        dateButton.setOnClickListener {
            openDatePicker()
        }

        // PROCEED BUTTON
        val proceedButton: Button = findViewById(R.id.proceed_button)
        proceedButton.setOnClickListener {
            val tableNumber = getTableNumber()
            val popupStandardTC = PopupStandardTC.newInstance(tableNumber, selectedDate, selectedTime)  // Pass the selected date and time to Popup_tc_standard
            popupStandardTC.show(supportFragmentManager, "popup_tc_standard")
        }

        // Retrieve and display table number
        val tableNumber = intent.getIntExtra("TABLE_NUMBER", -1)
        displayTableNumber(tableNumber)
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Use correct order for year, month, day in the DatePickerDialog
        DatePickerDialog(this, R.style.DialogTheme, { _, selectedYear, selectedMonth, selectedDay ->
            // Update the date button with the selected date
            selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"  // Store selected date
            dateButton.text = selectedDate
        }, year, month, day).show()
    }

    private fun openTimeRangePicker() {
        // Get the current time
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Show the first TimePickerDialog for the "start time"
        TimePickerDialog(this, R.style.DialogTheme, { _, startHour, startMinute ->
            // Convert start time to 12-hour format
            val startAmPm = if (startHour >= 12) "PM" else "AM"
            val startHourIn12Format = when {
                startHour == 0 -> 12 // Midnight case
                startHour == 12 -> 12 // Noon case
                startHour > 12 -> startHour - 12
                else -> startHour
            }
            val formattedStartMinute = String.format("%02d", startMinute) // Ensures 2 digits for minute

            // Now, show the second TimePickerDialog for the "end time"
            TimePickerDialog(this, R.style.DialogTheme, { _, endHour, endMinute ->
                // Convert end time to 12-hour format
                val endAmPm = if (endHour >= 12) "PM" else "AM"
                val endHourIn12Format = when {
                    endHour == 0 -> 12 // Midnight case
                    endHour == 12 -> 12 // Noon case
                    endHour > 12 -> endHour - 12
                    else -> endHour
                }
                val formattedEndMinute = String.format("%02d", endMinute) // Ensures 2 digits for minute

                selectedTime = "$startHourIn12Format:$formattedStartMinute $startAmPm - $endHourIn12Format:$formattedEndMinute $endAmPm"  // Store selected time
                timeButton.text = selectedTime

            }, currentHour, currentMinute, false).show()  // End TimePickerDialog (false for 12-hour format)

        }, currentHour, currentMinute, false).show()  // Start TimePickerDialog (false for 12-hour format)
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
