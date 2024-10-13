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

class Popup_standard : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val view = inflater.inflate(R.layout.popup_standard, container, false)

        val tableNumber = arguments?.getInt("table_number") ?: 0

        val tableNoTextView: TextView = view.findViewById(R.id.table_no)
        tableNoTextView.text = "TABLE # $tableNumber"

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(400.dpToPx1(requireContext()), 550.dpToPx1(requireContext()))
    }

    companion object {
        fun newInstance(tableNumber: Int): Popup_standard {
            val args = Bundle()
            args.putInt("table_number", tableNumber)
            val fragment = Popup_standard()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx1(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
