package com.importrandom.cuehall

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Calendar
import java.util.Locale


@Suppress("DEPRECATION")
class StandardTablesForm : AppCompatActivity() {

    private lateinit var timeInButton: Button
    private lateinit var timeOutButton: Button
    private lateinit var dateButton: Button
    private lateinit var proceedButton: Button
    private var selectedDate = ""
    private var selectedTimeIn = ""
    private var selectedTimeOut = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standard_tables_page)

        setupEdgeToEdgeDisplay()
        initializeButtons()
        displayTableNumber(intent.getIntExtra("TABLE_NUMBER", -1))
        updateProceedButtonState()
    }

    private fun setupEdgeToEdgeDisplay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    private fun initializeButtons() {
        timeInButton = findViewById(R.id.timeIn_btn)
        timeInButton.setOnClickListener {
            showTimePicker(timeInButton)
        }
        timeOutButton = findViewById(R.id.timeOut_btn)
        timeOutButton.setOnClickListener {
            showTimePicker(timeOutButton)
        }
        dateButton = findViewById(R.id.date_button)
        proceedButton = findViewById(R.id.proceed_button)

        proceedButton.background = ContextCompat.getDrawable(this, R.drawable.button_background_transition2)
        proceedButton.isEnabled = false
        (proceedButton.background as TransitionDrawable).reverseTransition(0) // Start in disabled state

        findViewById<Button>(R.id.bckButton).setOnClickListener {
            startActivity(Intent(this, MainInterface::class.java))
        }

        dateButton.setOnClickListener { openDatePicker() }
        proceedButton.setOnClickListener {
            if (selectedDate.isNotEmpty() && selectedTimeIn.isNotEmpty() && selectedTimeOut.isNotEmpty()) {
                PopupStandardTC.newInstance(
                    getTableNumber(), selectedDate, selectedTimeIn, selectedTimeOut
                )
                    .show(supportFragmentManager, "popup_tc_standard")
            } else {
                showSelectionPrompt()
            }
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, R.style.DialogTheme, { _, year, month, day ->
            selectedDate = "${month + 1}/$day/$year"
            dateButton.text = selectedDate
            updateProceedButtonState()
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).apply {
            datePicker.minDate = calendar.timeInMillis
        }.show()
    }

    private fun showTimePicker(button: Button) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this, R.style.DialogTheme,
            { _, hourOfDay, minute ->
                val formattedTime = formatTime(hourOfDay, minute)
                button.text = formattedTime

                // Update selected time based on button (assuming different buttons for in/out)
                if (button.id == R.id.timeIn_btn) {  // Assuming timeIn_btn is the button for time in
                    selectedTimeIn = formattedTime
                } else if (button.id == R.id.timeOut_btn) {  // Assuming timeOut_btn is the button for time out
                    selectedTimeOut = formattedTime
                }

                // Call updateProceedButtonState after any selection update
                updateProceedButtonState()
            },
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE],
            false
        )
        timePickerDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTime(hour: Int, minute: Int): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault()) // Use "h" for 12-hour format
        return sdf.format(java.util.Date(0, 0, 0, hour, minute, 0))
    }

    private fun updateProceedButtonState() {
        val isEnabled = selectedDate.isNotEmpty() && selectedTimeIn.isNotEmpty() && selectedTimeOut.isNotEmpty()
        val transitionDrawable = proceedButton.background as TransitionDrawable
        if (isEnabled) {
            proceedButton.isEnabled = true
            transitionDrawable.startTransition(200) // Transition only when both date and time are selected
        } else {
            proceedButton.isEnabled = false
            transitionDrawable.resetTransition() // Ensure it stays in the disabled state
        }
    }

    private fun showSelectionPrompt() {
        AlertDialog.Builder(this)
            .setTitle("Selection Required")
            .setMessage("Please select a time and date.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun displayTableNumber(tableNumber: Int) {
        findViewById<TextView>(R.id.table_no)?.text = if (tableNumber != -1) "TABLE# $tableNumber" else ""
    }

    private fun getTableNumber(): Int = intent.getIntExtra("TABLE_NUMBER", -1)
}
