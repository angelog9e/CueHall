package com.importrandom.cuehall

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
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
import android.graphics.drawable.TransitionDrawable


class StandardTablesForm : AppCompatActivity() {

    private lateinit var timeButton: Button
    private lateinit var dateButton: Button
    private lateinit var proceedButton: Button
    private var selectedDate = ""
    private var selectedTime = ""

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
        timeButton = findViewById(R.id.time_btn)
        dateButton = findViewById(R.id.date_button)
        proceedButton = findViewById(R.id.proceed_button)

        proceedButton.background = ContextCompat.getDrawable(this, R.drawable.button_background_transition2)
        proceedButton.isEnabled = false
        (proceedButton.background as TransitionDrawable).reverseTransition(0) // Start in disabled state

        findViewById<Button>(R.id.bckButton).setOnClickListener {
            startActivity(Intent(this, MainInterface::class.java))
        }

        timeButton.setOnClickListener { openTimeRangePicker() }
        dateButton.setOnClickListener { openDatePicker() }
        proceedButton.setOnClickListener {
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                PopupStandardTC.newInstance(getTableNumber(), selectedDate, selectedTime)
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

    private fun openTimeRangePicker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(this, R.style.DialogTheme, { _, startHour, startMinute ->
            val startFormatted = formatTime(startHour, startMinute)
            TimePickerDialog(this, R.style.DialogTheme, { _, endHour, endMinute ->
                val endFormatted = formatTime(endHour, endMinute)
                selectedTime = "$startFormatted - $endFormatted"
                timeButton.text = selectedTime
                updateProceedButtonState()
            }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false).show()
        }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false).show()
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(hour: Int, minute: Int): String {
        val period = if (hour >= 12) "PM" else "AM"
        val hourIn12 = if (hour == 0 || hour == 12) 12 else hour % 12
        return String.format("%d:%02d %s", hourIn12, minute, period)
    }

    private fun showSelectionPrompt() {
        AlertDialog.Builder(this)
            .setTitle("Selection Required")
            .setMessage("Please select a time and date.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun updateProceedButtonState() {
        val isEnabled = selectedDate.isNotEmpty() && selectedTime.isNotEmpty()

        val transitionDrawable = proceedButton.background as TransitionDrawable
        if (isEnabled) {
            proceedButton.isEnabled = true
            transitionDrawable.startTransition(200) // Transition only when both date and time are selected
        } else {
            proceedButton.isEnabled = false
            transitionDrawable.resetTransition() // Ensure it stays in the disabled state
        }
    }

    private fun displayTableNumber(tableNumber: Int) {
        findViewById<TextView>(R.id.table_no)?.text = if (tableNumber != -1) "TABLE# $tableNumber" else ""
    }

    private fun getTableNumber(): Int = intent.getIntExtra("TABLE_NUMBER", -1)
}
