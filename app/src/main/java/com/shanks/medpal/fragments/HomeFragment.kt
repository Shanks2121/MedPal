package com.shanks.medpal.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shanks.medpal.activities.AddAlarmActivity
import com.shanks.medpal.adapters.MedicineAdapter
import com.shanks.medpal.database.MedicineDatabase
import com.shanks.medpal.databinding.FragmentHomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var db : MedicineDatabase
    private var _binding : FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val someData = activity?.intent?.getStringExtra("STOP_ALARM") ?: false
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        createNotificationChannel()
//        val mediaPlayer = MediaPlayer.create(activity, R.raw.bird)


        binding.fabAlarm.setOnClickListener {
                val intent = Intent(activity, AddAlarmActivity::class.java)
                startActivity(intent)
            }

        binding.ivDeleteEveryThing.setOnClickListener {
            GlobalScope.launch {
                try {
                    MedicineDatabase(requireContext()).medicineDao().deleteEveryThing()
                } catch (e : Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

            }

        }


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MedicineAdapter()
        binding.rvMedicine.adapter = adapter
        binding.rvMedicine.layoutManager = LinearLayoutManager(requireContext())

        val database = MedicineDatabase(requireContext())
        val dao = database.medicineDao()

        dao.getMedicine().observe(viewLifecycleOwner, Observer {newDataList ->
                adapter.setData(newDataList)
        })
    }


//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "my_channel_id"
//            val channelName = "My Channel"
//            val channelDescription = "This is my channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, channelName, importance)
//            channel.description = channelDescription
//
//            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//
//    }

//    @SuppressLint("MissingPermission")
//    private fun showNotification() {
//        val notificationLayout = RemoteViews(activity?.packageName, R.layout.custom_notification_layout)
//        val channelId = "my_channel_id"
//        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            .setCustomContentView(notificationLayout)
//
//
//        val notificationManager = NotificationManagerCompat.from(requireContext())
//        notificationManager.notify(0, notificationBuilder.build())
//
//    }

    private fun createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name : CharSequence = "ReminderChannel"
            val description = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("VibrateAlarm",name,importance)
            channel.description = description
            val notificationManager = context?.getSystemService(NotificationManager::class.java)

            notificationManager?.createNotificationChannel(channel)

        }

    }

}

