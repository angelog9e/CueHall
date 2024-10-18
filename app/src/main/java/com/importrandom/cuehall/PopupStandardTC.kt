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
    private var selectedDate: String = ""
    private var selectedTime: String = ""

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
            // Pass the table number, date, and time to the next popup (Popup_standard)
            val popup = PopupStandard.newInstance(tableNumber, selectedDate, selectedTime)
            popup.show(parentFragmentManager, "popup_standard")
            dismiss()  // Close this dialog after showing the next popup
        }

        val webView: WebView = view.findViewById(com.importrandom.cuehall.R.id.justified_text)
        webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
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
            }
        </style>
    </head>
    <body>
        <p>1.&nbsp;General: These terms apply to all customers reserving a standard billiard table. By reserving, customers confirm they have read, understood, and accepted these terms. Customers must be at least 18 years old to make a reservation.</p>
        <p>2.&nbsp;Reservations: Reservations can be made online, by phone, or in person with a required down payment via GCash. Reservations are subject to availability and may be modified or cancelled by management without notice. Customers are encouraged to book in advance, especially during peak hours and weekends.</p>
        <p>3.&nbsp;Billiard Tables: Standard billiard tables are available on a first-come, first-served basis. Customers are liable for any damage to tables, chairs, or equipment during their reservation period. A maximum of 6 players is allowed per table to ensure a comfortable experience.</p>
        <p>4.&nbsp;Conduct: Respectful behavior is required at all times. Profanity, loud noise, and disruptive actions are prohibited. Management reserves the right to refuse service to violators and may ask disruptive customers to leave the premises without a refund. Any form of harassment or inappropriate behavior will not be tolerated.</p>
        <p>5.&nbsp;Liability: Management is not responsible for personal injuries or property damage occurring on-site. Customers are responsible for their belongings. Customers are encouraged to report any unsafe conditions or incidents to management immediately.</p>
        <p>6.&nbsp;Payment: Payment is due at the time of reservation or upon bill presentation. Accepted methods include GCash or cash. All payments are non-refundable. In the event of a cancellation or no-show, customers will not be entitled to a refund.</p>
        <p>7.&nbsp;Food and Beverage Policy: No Outside Food and Drinks Allowed: Outside food and drinks are strictly prohibited within the premises. Customers must purchase food and beverages from our menu. A minimum food and beverage purchase may be required for table reservations, especially during peak hours.</p>
        <p>8.&nbsp;Changes to Terms and Conditions: These terms may change without notice. Customers should review the latest version prior to making a reservation. Management reserves the right to update these terms to improve customer experience and safety.</p>
        <p>9.&nbsp;Special Promotions and Events: Customers may be eligible for special promotions or events. Details will be communicated through our app and social media channels. Reservations for special events may have different terms and conditions, which will be clearly stated at the time of booking.</p>
        <p>10.&nbsp;Contact Information: For any inquiries or assistance, customers can contact our support team via the app or at the provided contact number.</p>
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
        fun newInstance(tableNumber: Int, selectedDate: String, selectedTime: String): PopupStandardTC {
            val args = Bundle()
            args.putInt("table_number", tableNumber)
            args.putString("selected_date", selectedDate)
            args.putString("selected_time", selectedTime)
            val fragment = PopupStandardTC()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx5(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
