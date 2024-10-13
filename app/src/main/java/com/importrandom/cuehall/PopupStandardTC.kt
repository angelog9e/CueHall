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

class Popup_tc_standard : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.popup_tc_standard, container, false)

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

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
            val tableNumber = arguments?.getInt("table_number") ?: 0
            val popup = Popup_standard.newInstance(tableNumber)
            popup.show(parentFragmentManager, "popup_tag")
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(400.dpToPx5(requireContext()), 800.dpToPx5(requireContext()))
    }

    companion object {
        fun newInstance(tableNumber: Int): Popup_tc_standard {
            val args = Bundle()
            args.putInt("table_number", tableNumber)
            val fragment = Popup_tc_standard()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx5(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
