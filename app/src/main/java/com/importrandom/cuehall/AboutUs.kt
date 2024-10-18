package com.importrandom.cuehall

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment

class About_us : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.importrandom.cuehall.R.layout.about_us, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val webView: WebView = view.findViewById(com.importrandom.cuehall.R.id.justified_text)
        webView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        webView.webViewClient = WebViewClient()

        // HTML CONTENT
        val text = """
    <html>
    <head>
        <style>
            body {
                text-align: center;
                color: #fff4f0;
                font-family: 'quicksand_medium';
                font-size: 16;
                background-color: transparent;
                margin: 10;
                padding: 10;
            }
        </style>
    </head>
    <body>
        <p>CueHall is a mobile application that will change the traditional approach of booking and playing billiards in billiard halls. Due to the growing popularity of billiards as a form of entertainment, it is becoming increasingly difficult to identify the availability of tables for use, particularly for billiard hall owners. Conversely, clients frequently experience inconvenience and frustration as a result of unpredictable circumstances and extended waiting times. Import Random addresses these problems by offering an application that allows customers to browse available tables, join wait lists, receive notifications about their reservations, and choose their playing time and duration.</p>
    </body>
    </html>
""".trimIndent()


        webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null)

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(300.dpToPx3(requireContext()), 480.dpToPx3(requireContext()))
    }
}

fun Int.dpToPx3(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
