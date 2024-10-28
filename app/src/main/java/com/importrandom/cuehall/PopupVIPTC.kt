package com.importrandom.cuehall

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
        <p>General: To reserve the VIP Room, customers must be at least 21 years old and confirm their acceptance of the terms upon reservation. A valid ID may be required for verification.</p>
        <p>Reservations: Reservations for the VIP Room can be made online, by phone, or in person with a downpayment. They are subject to availability, may be modified or canceled by management, and advance booking is recommended, especially during peak hours. A confirmation will be provided upon reservation.</p>
        <p>Special Promotions and Events: Customers may qualify for exclusive promotions or events with VIP reservations, communicated via our app and social media. Special events may have different terms, which will be clearly outlined at booking.</p>
        <p>Customer Responsibility: Customers are responsible for any damage to the VIP Room and equipment during their reservation. A security deposit may be required, refundable after the reservation if no damage occurs.</p>
        <p>Payment: Payment is due at the time of reservation or upon bill presentation. Accepted payment methods include GCash or cash. All payments are non-refundable.</p>
        <p>Food and Beverage Policy: No outside food or drinks are allowed on the premises. Customers are encouraged to inquire about special VIP menu items and exclusive offerings.</p>
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




