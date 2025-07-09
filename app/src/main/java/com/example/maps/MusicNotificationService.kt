package com.example.maps

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

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
            if (artist != null && track != null) {
                val repository: ListensRepository = get()
                val listen = ListenFull(artist = artist, title = track)
                scope.launch { repository.insert(listen) }
            }
        }
    }
}