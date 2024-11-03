package com.importrandom.cuehall

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class Bookings : AppCompatActivity() {

    private lateinit var footer: View

    data class Booking(
        val id: Int,
        val number: Int,
        val name: String,
        val date: String,
        val timeIn: String,
        val timeOut: String,
        val type: String // "table" or "room"
    )

    private suspend fun fetchBookingsData(): List<Booking> {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://192.168.1.193/cuehall/fetch-bookings.php") // Replace with your server URL
                .build()

            val response = client.newCall(request).execute()
            val json = response.body()?.string()

            if (json != null) {
                val gson = Gson()
                val bookingsData: Map<String, List<Map<String, Any>>> = gson.fromJson(json, object : TypeToken<Map<String, List<Map<String, Any>>>>() {}.type)
                val tables = bookingsData["tables"]?.map { bookingMap ->
                    Booking(
                        id = bookingMap["id"]?.toString()?.toIntOrNull() ?: 0,
                        number = bookingMap["table_number"]?.toString()?.toIntOrNull() ?: 0,
                        name = bookingMap["name"] as String,
                        date = bookingMap["date"] as String,
                        timeIn = bookingMap["time_in"] as String,
                        timeOut = bookingMap["time_out"] as String,
                        type = "table"
                    )
                } ?: emptyList()
                val rooms = bookingsData["rooms"]?.map { bookingMap ->
                    Booking(
                        id = bookingMap["id"]?.toString()?.toIntOrNull() ?: 0,
                        number = bookingMap["room_number"]?.toString()?.toIntOrNull() ?: 0,
                        name = bookingMap["name"] as String,
                        date = bookingMap["date"] as String,
                        timeIn = bookingMap["time_in"] as String,
                        timeOut = bookingMap["time_out"] as String,
                        type = "room"
                    )
                } ?: emptyList()
                val bookings = tables + rooms
                if (bookings.isEmpty()) {
                    // No bookings found message
                    Log.d("Bookings", "No bookings found!") // Replace with your desired logging approach
                    return@withContext emptyList()
                } else {
                    return@withContext bookings
                }
            } else {
                emptyList()
            }
        }
    }

    class BookingsAdapter(private val bookings: List<Booking>) :
        RecyclerView.Adapter<BookingsAdapter.BookingViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.booking_item, parent, false)
            return BookingViewHolder(view)
        }

        override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
            val booking = bookings[position]
            holder.bind(booking)
        }

        override fun getItemCount(): Int {
            return bookings.size
        }

        inner class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val bookingIdTextView: TextView = itemView.findViewById(R.id.bookingIdTextView)
            private val bookingNumberTextView: TextView = itemView.findViewById(R.id.bookingNumberTextView)
            private val bookingNameTextView: TextView = itemView.findViewById(R.id.bookingNameTextView)
            private val bookingDateTextView: TextView = itemView.findViewById(R.id.bookingDateTextView)
            private val bookingTimeInTextView: TextView = itemView.findViewById(R.id.bookingTimeInTextView)
            private val bookingTimeOutTextView: TextView = itemView.findViewById(R.id.bookingTimeOutTextView)
            private val bookingTypeTextView: TextView = itemView.findViewById(R.id.bookingTypeTextView)

            @SuppressLint("SetTextI18n")
            fun bind(booking: Booking) {
                bookingIdTextView.text = "Booking ID: ${booking.id}"
                val numberText: String = if (booking.type == "table") {
                    "Table Number: ${booking.number}"
                } else {
                    "Room Number: ${booking.number}"
                }
                bookingNumberTextView.text = numberText
                bookingNameTextView.text = "Name: ${booking.name}"
                bookingDateTextView.text = "Date: ${booking.date}"
                bookingTimeInTextView.text = "Time In: ${booking.timeIn}"
                bookingTimeOutTextView.text = "Time Out: ${booking.timeOut}"
                bookingTypeTextView.text = "Type: ${booking.type}"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookings_page)

        // EDGE TO EDGE DISPLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val insetsController = window.insetsController
            insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        val bookingsRecyclerView: RecyclerView = findViewById(R.id.bookingsRecyclerView)
        bookingsRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val bookings = withContext(Dispatchers.IO) { fetchBookingsData() }
            withContext(Dispatchers.Main) {
                if (bookings.isEmpty()) {
                    Toast.makeText(this@Bookings, "No bookings found", Toast.LENGTH_SHORT).show()
                } else {
                    val adapter = BookingsAdapter(bookings)
                    bookingsRecyclerView.adapter = adapter
                }
            }
        }

        // BUTTONS
        val accButton = findViewById<Button>(R.id.account_button)
        accButton.setOnClickListener {
            val intent = Intent(this, AccountSettings::class.java)
            startActivity(intent)
        }

        // TABLES BUTTON
        val tablesButton = findViewById<Button>(R.id.tables_button)
        tablesButton.setOnClickListener {
            val intent = Intent(this, MainInterface::class.java)
            startActivity(intent)

        }

        // BOOKINGS BUTTON
        val bookingsButton = findViewById<Button>(R.id.bookings_button)
        // bookingsButton.setOnClickListener {
        //    val intent = Intent(this, Bookings::class.java)
        //    startActivity(intent)
        //}

        // Bookings button attributes
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 54f // Adjust the radius here
            setColor(Color.parseColor("#8024242C")) // Semi-transparent color
        }
        bookingsButton.background = drawable
        bookingsButton.setOnClickListener {
            it.animate().alpha(0.7f).setDuration(300).withEndAction {
                it.alpha = 1f
            }.start()
        }

        // FOOTER ANIMATION
        footer = findViewById(R.id.footer)
        footer.post {
            val animator = ObjectAnimator.ofFloat(footer, "translationY", footer.height.toFloat(), 0f)
            animator.duration = 500
            animator.start()
        }

        // EDGE TO EDGE DISPLAY PADDING
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bookings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}
