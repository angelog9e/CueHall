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
        </style>
    </head>
    <body>
    <p>
    Welcome to CueHall, your ultimate app for billiards and bar reservations! We are dedicated to connecting billiards enthusiasts and bar lovers through a seamless and enjoyable experience. 
    At CueHall, we understand the excitement of sinking that perfect shot and the joy of sharing good times with friends. Our app is designed to make it easier than ever to find and reserve your favorite billiards spots, whether you're planning a casual night out or a special event.
    We created CueHall to support aspiring billiard players and those eager to learn the game, offering resources and a community for growth. Our platform aims to attract both amateurs and seasoned professionals, fostering an environment that promotes skill development, friendly competition, and a shared passion for billiards. We believe billiards is a pleasant game that brings people together, and by connecting players of all levels, we strive to enhance the billiards community and contribute to the growth of the industry as a whole. We offer respectful behavior, which is required at all times. Profanity, loud noise, and disruptive actions are prohibited. 
    Join us at CueHall, where every shot counts and every gathering is a celebration. Let's make your next event unforgettable! 
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
    }
}

fun Int.dpToPx3(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
