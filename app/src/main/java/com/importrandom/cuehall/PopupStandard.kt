package com.importrandom.cuehall

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class PopupStandard : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val view = inflater.inflate(R.layout.popup_standard, container, false)

        val tableNumber = arguments?.getInt("table_number") ?: 0
        val selectedDate = arguments?.getString("selected_date") ?: ""
        val selectedTime = arguments?.getString("selected_time") ?: ""

        val tableNoTextView: TextView = view.findViewById(R.id.table_no)
        val dateTextView: TextView = view.findViewById(R.id.date_view) // Assuming you have a TextView for date
        val timeTextView: TextView = view.findViewById(R.id.time_view) // Assuming you have a TextView for time

        tableNoTextView.text = "TABLE # $tableNumber"
        dateTextView.text = "$selectedDate"
        timeTextView.text = "$selectedTime"

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(400.dpToPx1(requireContext()), 550.dpToPx1(requireContext()))
    }

    companion object {
        fun newInstance(tableNumber: Int, selectedDate: String, selectedTime: String): PopupStandard {
            val args = Bundle()
            args.putInt("table_number", tableNumber)
            args.putString("selected_date", selectedDate)
            args.putString("selected_time", selectedTime)
            val fragment = PopupStandard()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx1(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
