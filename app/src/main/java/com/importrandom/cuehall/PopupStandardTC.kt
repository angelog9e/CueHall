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
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class PopupStandardTC: DialogFragment() {

    private var tableNumber: Int = 0
    private var inputName: String = ""
    private var selectedDate: String = ""
    private var selectedTimeIn: String = ""
    private var selectedTimeOut: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.popup_tc_standard, container, false)

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        // Retrieve the passed table number, date, and time from the arguments
        tableNumber = arguments?.getInt("table_number") ?: 0
        inputName = arguments?.getString("input_name") ?: ""
        selectedDate = arguments?.getString("selected_date") ?: ""
        selectedTimeIn = arguments?.getString("selected_time_in") ?: ""
        selectedTimeOut = arguments?.getString("selected_time_out") ?: ""

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
            // Pass the table number, date, and time to the next popup (Popup_standard)
            val popup = PopupStandard.newInstance(tableNumber, inputName, selectedDate, selectedTimeIn, selectedTimeOut)
            popup.show(parentFragmentManager, "popup_standard")
            dismiss()  // Close this dialog after showing the next popup
        }

        val webView: WebView = view.findViewById(R.id.justified_text)
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.webViewClient = WebViewClient()

        // HTML CONTENT
        val text = """
    <html>
    <head>
        <style>
            body {
                text-align: justify;
                color: #fff4f0;
                font-family: 'quicksand_medium';
                font-size: 16;
                background-color: transparent;
                margin: 10;
                padding: 18;
                line-height: 1.5;
            }
        </style>
    </head>
    <body>
        <p>General: To reserve the Standard Room, customers must be at least 21 years old and confirm their acceptance of the terms upon reservation. A valid ID may be required for verification.</p>
        <p>Reservations: Reservations for the Standard Table can be made online, by phone, or in person with a downpayment. They are subject to availability, may be modified or canceled by management, and advance booking is recommended, especially during peak hours. A confirmation will be provided upon reservation.</p>
        <p>Billiard Tables: Standard billiard tables are available on a first-come, first-served basis. Customers are liable for any damage to tables, chairs, or equipment during their reservation period. A maximum of 6 players is allowed per table to ensure a comfortable experience.</p>
        <p>Conduct: Respectful behavior is required at all times. Profanity, loud noise, and disruptive actions are prohibited. Management may refuse service and remove violators without a refund. Harassment or inappropriate behavior will not be tolerated.</p>
        <p>Liability: Customers are liable for any damage to the Standard Billiard Table and equipment during their reservation. A refundable security deposit may be required at booking, provided no damage occurs.</p>
        <p>Payment/s: Payment is due at the time of reservation or upon bill presentation. Accepted payment methods include GCash or cash. All payments are non-refundable.</p>
        <p>Food and Beverage Policy: No outside food or drinks are allowed on the premises. Customers are encouraged to inquire about menu items and exclusive offerings.</p>
        <p>Changes to Terms and Conditions: Terms and conditions may change without notice. Customers should review them regularly, as management reserves the right to update them for improved safety and experience.</p>
        
    </body>
    </html>
""".trimIndent()
        webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null)
        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(300.dpToPx5(requireContext()), 600.dpToPx5(requireContext()))
    }

    companion object {
        fun newInstance(
            tableNumber: Int,
            inputName: String,
            selectedDate: String,
            selectedTimeIn: String,
            selectedTimeOut: String,
        ): PopupStandardTC {
            val args = Bundle()
            args.putInt("table_number", tableNumber)
            args.putString("input_name", inputName)
            args.putString("selected_date", selectedDate)
            args.putString("selected_time_in", selectedTimeIn)
            args.putString("selected_time_out", selectedTimeOut)
            val fragment = PopupStandardTC()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx5(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
