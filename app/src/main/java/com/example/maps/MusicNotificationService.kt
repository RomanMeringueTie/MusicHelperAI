package com.example.maps

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository
import com.example.maps.domain.GetNotificationSettingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MusicNotificationService : NotificationListenerService() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras
        val sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE)
        val pickedApps = sharedPreferences.getStringSet("PICKED_APPS", emptySet<String>())
        if (pickedApps?.contains(sbn.packageName) == true) {
            val artist = extras.getString("android.text")
            val track = extras.getString("android.title")
            if (artist != null && track != null && artist != "Музыка скоро начнётся") {
                val repository: ListensRepository = get()
                val listen = ListenFull(
                    artist = artist,
                    title = track,
                    playedAt = System.currentTimeMillis()
                )
                scope.launch { repository.insert(listen) }
                getLastListenDate(sharedPreferences, track)
            }
        }
    }

    private fun getLastListenDate(sharedPreferences: SharedPreferences, trackTitle: String) {
        val sdf = SimpleDateFormat("dd.M.yy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        val lastListenDate = sharedPreferences.getString("LAST_DATE", null)
        val getNotificationSettingUseCase: GetNotificationSettingUseCase = get()
        scope.launch {
            val isNotificationsAllowed = getNotificationSettingUseCase()
            if (currentDate != lastListenDate && isNotificationsAllowed) {
                sendNotification(sharedPreferences, currentDate, trackTitle)
            }
        }
    }

    private fun sendNotification(
        sharedPreferences: SharedPreferences,
        currentDate: String,
        trackTitle: String,
    ) {
        sharedPreferences.edit(commit = true) {
            putString("LAST_DATE", currentDate)
            apply()
        }
        createNotificationChannel()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        val builder = NotificationCompat.Builder(this, "CHANNEL")
            .setSmallIcon(R.drawable.music_icon)
            .setContentTitle(getString(R.string.notification_title, trackTitle))
            .setContentText(getString(R.string.notification_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MusicNotificationService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
                notify(0, builder.build())
        }
    }

    private fun createNotificationChannel() {
        val name = "CHANNEL"
        val descriptionText = "CHANNEL FOR MUSIC_HELPER"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}