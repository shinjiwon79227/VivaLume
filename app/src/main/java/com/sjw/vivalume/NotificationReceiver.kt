package com.sjw.vivalume

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.Manifest
import android.content.pm.PackageManager
import com.sjw.vivalume.WiseSayingManager

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val wiseSayingManager = WiseSayingManager(context)
        val wiseSaying = wiseSayingManager.getRandomWiseSaying()

        if (wiseSaying != null) {
            showNotification(context, wiseSaying)
            Log.d("test", "알림 온다")
        } else {
            Log.e("NotificationReceiver", "명언을 찾을 수 없습니다.")
        }
    }

    private fun showNotification(context: Context, wiseSaying: WiseSaying) {
        val channelId = "default_channel" // 알림 채널 ID
        val channelName = "명언 알림" // 알림 채널 이름

        // Android 8.0 이상에서는 알림 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 알림 채널이 없을 경우 생성
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "명언 알림을 위한 기본 채널"
                }
                notificationManager.createNotificationChannel(channel)
            }
        }

        // 알림 생성
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.bookmark)
            .setContentTitle("VivaLume")
            .setContentText(wiseSaying.wiseSaying)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)

        // 알림 권한 확인
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("NotificationReceiver", "알림 권한이 없습니다.")
            return
        }

        // 알림 표시
        notificationManager.notify(1, notificationBuilder.build())
    }
}
