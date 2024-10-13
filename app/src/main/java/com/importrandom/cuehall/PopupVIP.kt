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

class Popup_vip : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val view = inflater.inflate(R.layout.popup_vip, container, false)

        val roomNumber = arguments?.getInt("room_number") ?: 0

        val roomNoTextView: TextView = view.findViewById(R.id.room_no)
        roomNoTextView.text = "VIP ROOM # $roomNumber"

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(400.dpToPx2(requireContext()), 550.dpToPx2(requireContext()))
    }

    companion object {
        fun newInstance(roomNumber: Int): Popup_vip {
            val args = Bundle()
            args.putInt("room_number", roomNumber)
            val fragment = Popup_vip()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx2(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
