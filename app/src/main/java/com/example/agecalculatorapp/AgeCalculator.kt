package com.example.agecalculatorapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.Period

class AgeCalculator : AppCompatActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var ageResult: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_age_calculator)

        datePicker = findViewById(R.id.datePicker)
        ageResult = findViewById(R.id.ageResult)

        // Calculate initial age with current date set in DatePicker
        calculateAge(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)

        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calculateAge(year, monthOfYear + 1, dayOfMonth)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(year: Int, month: Int, day: Int) {
        val birthDate = LocalDate.of(year, month, day)
        val currentDate = LocalDate.now()

        if (!birthDate.isAfter(currentDate)) {
            val age = Period.between(birthDate, currentDate)
            ageResult.text = "You are ${age.years} Years, ${age.months} Months, and ${age.days} Days"
        } else {
            Toast.makeText(this, "Invalid date. Please select a valid date.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}
