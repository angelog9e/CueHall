package com.importrandom.cuehall

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

class Contact_us : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contact_us, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(300.dpToPx4(requireContext()), 460.dpToPx4(requireContext()))
    }
}

fun Int.dpToPx4(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
