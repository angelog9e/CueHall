package com.importrandom.cuehall

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class ContactUs : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contact_us, container, false)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        dialog?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        // Find the TextView after the view is inflated
        val facebookTextView = view.findViewById<TextView>(R.id.facebook_text)

        // Set the click listener for the Facebook link
        facebookTextView.setOnClickListener {
            val url = "https://www.facebook.com/profile.php?id=61567148382028&mibextid=LQQJ4d"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Find the TextView after the view is inflated
        val instagramTextView = view.findViewById<TextView>(R.id.instagram_text)

        // Set the click listener for the Instagram link
        instagramTextView.setOnClickListener {
            val url = "https://www.instagram.com/cuehall_?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Find the TextView after the view is inflated
        val numberTextView = view.findViewById<TextView>(R.id.number_text)

        // Set the click listener for the number link
        numberTextView.setOnClickListener {
            val phoneNumber = "tel: +639563629014"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(phoneNumber)
            startActivity(intent)
        }

        // Find the TextView after the view is inflated
        val telegramTextView = view.findViewById<TextView>(R.id.telegram_text)

        // Set the click listener for the Telegram link
        telegramTextView.setOnClickListener {
            val url = "https://t.me/CueHall"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Find the TextView after the view is inflated
        val tiktokTextView = view.findViewById<TextView>(R.id.tiktok_text)

        // Set the click listener for the Tiktok link
        tiktokTextView.setOnClickListener {
            val url = "https://www.tiktok.com/@cuehall?is_from_webapp=1&sender_device=pc"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

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

