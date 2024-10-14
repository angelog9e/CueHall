package com.importrandom.cuehall

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class PopupVIPTC : DialogFragment() {

    private var roomNumber: Int = 0
    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.popup_tc_vip, container, false)

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // Retrieve the passed room number, date, and time from the arguments
        roomNumber = arguments?.getInt("room_number") ?: 0
        selectedDate = arguments?.getString("selected_date") ?: ""
        selectedTime = arguments?.getString("selected_time") ?: ""

        val proceedButton = view.findViewById<Button>(R.id.proceed_button)
        proceedButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_background_transition)
        val transitionDrawable = proceedButton.background as TransitionDrawable

        proceedButton.isEnabled = false

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            proceedButton.isEnabled = isChecked
            if (isChecked) {
                transitionDrawable.startTransition(300)
            } else {
                transitionDrawable.reverseTransition(300)
            }
        }

        proceedButton.setOnClickListener {
            // Pass the room number, date, and time to the next popup (Popup_vip)
            val popup = PopupVIP.newInstance(roomNumber, selectedDate, selectedTime)
            popup.show(parentFragmentManager, "popup_vip")
            dismiss()  // Close this dialog after showing the next popup
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(400.dpToPx6(requireContext()), 800.dpToPx6(requireContext()))
    }

    companion object {
        fun newInstance(roomNumber: Int, selectedDate: String, selectedTime: String): PopupVIPTC {
            val args = Bundle()
            args.putInt("room_number", roomNumber)
            args.putString("selected_date", selectedDate)
            args.putString("selected_time", selectedTime)
            val fragment = PopupVIPTC()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx6(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}

