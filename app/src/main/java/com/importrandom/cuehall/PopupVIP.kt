package com.importrandom.cuehall

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

@Suppress("RemoveSingleExpressionStringTemplate")
class PopupVIP : DialogFragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val view = inflater.inflate(R.layout.popup_vip, container, false)

        val roomNumber = arguments?.getInt("room_number") ?: 0
        val inputName = arguments?.getString("input_name")?: ""
        val selectedDate = arguments?.getString("selected_date") ?: ""
        val selectedTimeIn = arguments?.getString("selected_time_in") ?: ""
        val selectedTimeOut = arguments?.getString("selected_time_out") ?: "" // Example time out

        val roomNoTextView: TextView = view.findViewById(R.id.room_no)
        val nameTextView: TextView = view.findViewById(R.id.name_view)
        val dateTextView: TextView = view.findViewById(R.id.date_view_vip) // Assuming you have a TextView for date
        val timeInTextView: TextView = view.findViewById(R.id.timeIn_view_vip) // Assuming you have a TextView for time
        val timeOutTextView: TextView = view.findViewById(R.id.timeOut_view_vip) // Assuming you have a TextView for time out
        val proceedButton: Button = view.findViewById(R.id.proceed2_button)

        val client = OkHttpClient()

        proceedButton.setOnClickListener {

            val requestBody = FormBody.Builder()
                .add("room_number", roomNumber.toString())
                .add("name", inputName)
                .add("date", selectedDate)
                .add("time_in", selectedTimeIn)
                .add("time_out", selectedTimeOut)
                .build()

            val request = Request.Builder()
                .url("http://192.168.1.193/cuehall/reserve-rooms.php") // Replace with your server URL
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle request failure
                    println("Request failed: ${e.message}")
                    // You might want to show an error message to the user here, e.g., using a Toast
                    activity?.runOnUiThread {
                        Toast.makeText(
                            context,
                            "Booking failed: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody =
                        response.body()?.string() // Read the response body into a variable
                    // Handle request success
                    println("Request successful: $responseBody")
                    // You might want to show a success message to the user here, e.g., using a Toast
                    activity?.runOnUiThread {
                        Toast.makeText(context, "Booking successful!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
        roomNoTextView.text = "ROOM # $roomNumber"
        nameTextView.text = "$inputName"
        dateTextView.text = "$selectedDate"
        timeInTextView.text = "$selectedTimeIn"
        timeOutTextView.text = "$selectedTimeOut" // Example time out

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(300.dpToPx2(requireContext()), 550.dpToPx2(requireContext()))
    }

    companion object {
        fun newInstance(roomNumber: Int, inputName: String, selectedDate: String, selectedTimeIn: String, selectedTimeOut: String): PopupVIP {
            val args = Bundle()
            args.putInt("room_number", roomNumber)
            args.putString("input_name", inputName) // Add the inputName argument
            args.putString("selected_date", selectedDate) // Add the selectedDate argument
            args.putString("selected_time_in", selectedTimeIn) // Add the selectedTime argument
            args.putString("selected_time_out", selectedTimeOut) // Add the selectedTime argument
            val fragment = PopupVIP()
            fragment.arguments = args
            return fragment
        }
    }
}

fun Int.dpToPx2(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
