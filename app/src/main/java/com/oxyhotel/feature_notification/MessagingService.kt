package com.oxyhotel.feature_notification

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.oxyhotel.MainActivity
import com.oxyhotel.OxyApplication
import com.oxyhotel.R
import com.oxyhotel.feature_home.domain.model.BookingStorage
import com.oxyhotel.feature_home.domain.use_cases.BookingUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class MessagingService : FirebaseMessagingService(

) {

    @Inject
    lateinit var bookingUseCases: BookingUseCases
    @Inject
    lateinit var json: Json

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("new token is $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val eventType = message.data["eventType"]

        if (eventType != null && Events.values().map { it.toString() }.contains(eventType)) {
            val event = Events.valueOf(eventType)
            val data = message.data["booking"]
            if (data != null) {
                try {
                    val bookingModel = json.decodeFromString<BookingStorage>(data)
                    postBookingUpdate(
                        Event(
                            bookingModel = bookingModel,
                            event = event
                        )
                    )
                    bookingModel?.let {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                bookingUseCases.addBooking(
                                    bookingStorage = bookingModel
                                )
                            }
                        }

                    }

                } catch (err: Exception) {
                    err.printStackTrace()
                }
            }

        }
    }

    private fun postBookingUpdate(event: Event) {
        val booking = event.bookingModel
        val rooms = booking.bookedRooms.map { it.value }.flatten().sum()
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)

        val infoText = "${
            bookingDateFormatter(
                booking.checkIn,
                booking.checkOut
            )
        } | $rooms Room - $rooms Night | INR ${booking.amount} | BookingId : ${booking.bookingId}"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            OxyApplication.callNotificationChannelId
        )
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Booking ${event.event}")
            .setContentText(infoText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setContentInfo(infoText)
            .setStyle(NotificationCompat.BigTextStyle())


        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify((Math.random() * 300).toInt(), builder.build())
    }
}


fun bookingDateFormatter(checkInTime: String, checkOutTime: String): String {

    val dateParserFormat = SimpleDateFormat("DD-MM-YYYY", Locale.getDefault())

    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val checkIn = dateParserFormat.parse(checkInTime)?.let { dateFormatter.format(it) }
    val checkOut = dateParserFormat.parse(checkOutTime)?.let { dateFormatter.format(it) }

    if (checkIn == null || checkOut == null) {
        return "$checkIn - $checkOut"
    }

    return if (checkIn.slice(2 until checkIn.length) == checkOut.slice(2 until checkIn.length)) {
        "${checkIn.slice(0 until 2)} - ${checkOut.slice(0 until 6)}"
    } else if (checkIn.slice(6 until checkIn.length) == checkOut.slice(6 until checkIn.length)) {
        "${checkIn.slice(0 until 6)} - ${checkOut.slice(0 until 6)}"
    } else {
        "$checkIn - $checkOut"
    }

}
