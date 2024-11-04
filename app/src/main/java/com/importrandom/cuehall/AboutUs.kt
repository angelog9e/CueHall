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

class AboutUs : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.about_us, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        dialog?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        val webView: WebView = view.findViewById(R.id.justified_text)
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
            p {
                text-align: justify;
                margin: 0 auto;
                width: 90%;
                line-height: 1.5;
            }
        </style>
    </head>
    <body>
    <p>
    Welcome to CueHall, your go-to app for billiards and bar reservations! We connect billiards enthusiasts and bar lovers for an enjoyable experience. Whether you're planning a casual night out or a special event, our app simplifies finding and reserving your favorite spots.
CueHall supports aspiring players with resources and a community for growth, promoting skill development and friendly competition. We value respectful behavior—profanity, loud noise, and disruptive actions are prohibited.
Join us at CueHall, where every shot counts and every gathering is a celebration!
 
    </p>
    </body>
    </html>
""".trimIndent()


        webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null)

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(300.dpToPx3(requireContext()), 480.dpToPx3(requireContext()))

        dialog?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }
}

fun Int.dpToPx3(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
