package ir.paad.audiobook.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.NetworkInfo
import android.net.Uri
import android.os.Environment
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.Surface
import com.google.android.exoplayer2.*

import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.decoder.DecoderCounters
import com.google.android.exoplayer2.ext.mediasession.DefaultPlaybackController
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerNotificationManager

import java.io.File

import ir.paad.audiobook.MainActivity
import ir.paad.audiobook.R
import ir.paad.audiobook.customClass.CustomFileDataSourceFactory
import java.io.IOException
import java.lang.Exception

class PlayerService : Service() {


    private var player: SimpleExoPlayer? = null

    private var playerNotificationManager: PlayerNotificationManager? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val file = File(Environment.getExternalStorageDirectory(), "a.mp3")
        val file2 = File(Environment.getExternalStorageDirectory(), "b.mp3")

        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        val factory = CustomFileDataSourceFactory()
        val concatenatingMediaSource = ConcatenatingMediaSource()
        var mediaSource = ExtractorMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(file.path))
        concatenatingMediaSource.addMediaSource(mediaSource)
        mediaSource = ExtractorMediaSource.Factory(factory).createMediaSource(Uri.parse(file2.path))
        concatenatingMediaSource.addMediaSource(mediaSource)

        player!!.prepare(concatenatingMediaSource)
        player!!.playWhenReady = true


        playerNotificationManager = PlayerNotificationManager
                .createWithNotificationChannel(this,
                        "exo-channel",
                        R.string.exo_download_notification_channel_name,
                        100,
                        object : PlayerNotificationManager.MediaDescriptionAdapter {
                            override fun getCurrentContentTitle(player: Player): String {
                                return if (player.currentWindowIndex == 0) {
                                    "first file"
                                } else {
                                    "second file"
                                }
                            }

                            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                                val intent = Intent(this@PlayerService, MainActivity::class.java)
                                return PendingIntent.getActivity(this@PlayerService, 0, intent, 0)
                            }


                            override fun getCurrentContentText(player: Player): String? {
                                return if (player.currentWindowIndex == 0) {
                                    "first file description"
                                } else {
                                    "second file description"
                                }
                            }

                            override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback): Bitmap? {
                                return BitmapFactory.decodeResource(this@PlayerService.resources, R.drawable.ic_home)
                            }
                        })

        playerNotificationManager!!.setNotificationListener(object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationStarted(notificationId: Int, notification: Notification) {
                startForeground(notificationId, notification)
            }

            override fun onNotificationCancelled(notificationId: Int) {
                stopSelf()
            }
        })

        playerNotificationManager!!.setPlayer(player)


        val mediaSessionCompat = MediaSessionCompat(this , "MediaSession")
        val playbackController = DefaultPlaybackController()
        val connector  = MediaSessionConnector(mediaSessionCompat)


    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        playerNotificationManager!!.setPlayer(null)
        player!!.release()
        player = null
    }
}
