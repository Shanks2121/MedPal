package com.shanks.medpal.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.shanks.medpal.database.MedicineDatabase
import com.shanks.medpal.databinding.ActivityAddAlarmBinding
import com.shanks.medpal.models.Medicine
import kotlinx.coroutines.launch
import java.util.*

class AddAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAlarmBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var alarmManager : AlarmManager
    private lateinit var calendar : Calendar
    private lateinit var pendingIntent : PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()

        binding.btnAddReminder.setOnClickListener {
            addReminder()
           val intent = Intent(this@AddAlarmActivity, MainActivity::class.java)
           startActivity(intent)
        }

        binding.btnSetAlarm.setOnClickListener {
            setAlarm()
        }



        binding.btnTimePicker.setOnClickListener {
            showTimePicker()
        }


    }

    private fun addReminder(){
        val medicineName = binding.etMedicineName.editText?.text.toString()
        val medicineType = binding.etMedicineType.editText?.text.toString()
        val dayFormat = binding.etMedicineDay.editText?.text.toString()

        lifecycleScope.launch {
            val medicine = Medicine(0, medicineName, medicineType, dayFormat)
            MedicineDatabase(this@AddAlarmActivity).medicineDao().insertMedicine(medicine)
            finish()
        }
    }

    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "Alarm")

        picker.addOnPositiveButtonClickListener{
            if (picker.hour >= 12){
                binding.tvAlarmTime.text =
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + " PM "
            } else {

                binding.tvAlarmTime.text = String.format("%02d", picker.hour) + " : " + String.format(
                    "%02d",
                    picker.minute
                ) + " AM "

            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }


    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        calendar = Calendar.getInstance()

        val intent = Intent(this, AlarmReciever::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_HALF_DAY,
            pendingIntent
        )

//        AlarmManager.RTC_WAKEUP,
//        calendar.timeInMillis,
//        AlarmManager.INTERVAL_DAY,
//        pendingIntent

        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()



    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),101)
        }

    }
}