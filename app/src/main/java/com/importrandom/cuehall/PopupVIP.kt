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

class PopupVIP : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val view = inflater.inflate(R.layout.popup_vip, container, false)

        val roomNumber = arguments?.getInt("room_number") ?: 0
        val selectedDate = arguments?.getString("selected_date") ?: ""
        val selectedTimeIn = arguments?.getString("selected_time_in_vip") ?: ""
        val selectedTimeOut = arguments?.getString("selected_time_out_vip") ?: "" // Example time out

        val roomNoTextView: TextView = view.findViewById(R.id.room_no)
        val dateTextView: TextView = view.findViewById(R.id.date_view_vip) // Assuming you have a TextView for date
        val timeInTextView: TextView = view.findViewById(R.id.timeIn_view_vip) // Assuming you have a TextView for time
        val timeOutTextView: TextView = view.findViewById(R.id.timeOut_view_vip) // Assuming you have a TextView for time out

        roomNoTextView.text = "VIP ROOM # $roomNumber"
        dateTextView.text = "$selectedDate"
        timeInTextView.text = "$selectedTimeIn"
        timeOutTextView.text = "$selectedTimeOut" // Example time out

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(300.dpToPx2(requireContext()), 550.dpToPx2(requireContext()))
    }

    companion object {
        fun newInstance(roomNumber: Int, selectedDate: String, selectedTimeIn: String, selectedTimeOut: String): PopupVIP {
            val args = Bundle()
            args.putInt("room_number", roomNumber)
            args.putString("selected_date", selectedDate) // Add the selectedDate argument
            args.putString("selected_time_in_vip", selectedTimeIn) // Add the selectedTime argument
            args.putString("selected_time_out_vip", selectedTimeOut) // Add the selectedTime argument
            val fragment = PopupVIP()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx2(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
