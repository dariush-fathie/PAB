package ir.paad.audiobook.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.net.Uri
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import android.support.v4.media.AudioAttributesCompat
import android.util.Log
import com.google.android.exoplayer2.DefaultControlDispatcher
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import ir.paad.audiobook.MainActivity
import ir.paad.audiobook.R
import ir.paad.audiobook.customClass.CustomFileDataSourceFactory
import ir.paad.audiobook.models.events.PlayerEvent
import ir.paad.audiobook.utils.Colors
import ir.paad.audiobook.utils.audio.AudioFocusAwarePlayer
import ir.paad.audiobook.utils.audio.AudioFocusHelper
import ir.paad.audiobook.utils.audio.AudioFocusRequestCompat
import org.greenrobot.eventbus.EventBus
import java.io.File

class PlayerService : Service() {

    val tag = "PlayerService"
    val notificationId = 1078

    private lateinit var mBinder: MyBinder
    private lateinit var dispatcher:CustomControlDispatcher
    var player: SimpleExoPlayer? = null
    private lateinit var mNotification: Notification
    private var playerNotificationManager: PlayerNotificationManager? = null
    private lateinit var mFocusHelper: AudioFocusHelper

    inner class MyBinder : Binder() {
        var playerService = this@PlayerService
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }


    fun getDispatcher() : CustomControlDispatcher{
        return dispatcher
    }

    override fun onCreate() {
        super.onCreate()

        mBinder = MyBinder()

        val file = File(Environment.getExternalStorageDirectory(), "server.mp3")
        val file2 = File(Environment.getExternalStorageDirectory(), "android.mp3")

        player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        val factory = CustomFileDataSourceFactory()

        val concatenatingMediaSource = ConcatenatingMediaSource()
        val mediaSource = ExtractorMediaSource.Factory(factory).createMediaSource(Uri.parse(file.path))

        concatenatingMediaSource.addMediaSource(mediaSource)

        val factory1 = CustomFileDataSourceFactory()
        val mediaSource1 = ExtractorMediaSource.Factory(factory1).createMediaSource(Uri.parse(file2.path))

        concatenatingMediaSource.addMediaSource(mediaSource1)

        player!!.prepare(concatenatingMediaSource)
        //player!!.playWhenReady = true


        playerNotificationManager = PlayerNotificationManager
                .createWithNotificationChannel(this,
                        "exo-channel",
                        R.string.exo_download_notification_channel_name,
                        notificationId,
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
                                return BitmapFactory.decodeResource(this@PlayerService.resources, R.drawable.track_image)
                            }
                        })

        playerNotificationManager!!.setNotificationListener(object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationStarted(notificationId: Int, notification: Notification) {
                Log.e(tag, "notifi started")
                mNotification = notification
                startForeground(notificationId, notification)
            }

            override fun onNotificationCancelled(notificationId: Int) {
                Log.e(tag, "notifi canceled")
                stopForeground(true)
            }
        })

        dispatcher = CustomControlDispatcher()

        playerNotificationManager!!.setStopAction(PlayerNotificationManager.ACTION_STOP)
        playerNotificationManager!!.setOngoing(false)
        playerNotificationManager!!.setSmallIcon(R.drawable.ic_music)
        playerNotificationManager!!.setColorized(true)
        playerNotificationManager!!.setColor(Colors(this).colorPrimary)
        playerNotificationManager!!.setUseNavigationActions(false)
        playerNotificationManager!!.setControlDispatcher(dispatcher)
        //playerNotificationManager!!.setPlayer(player)


        /*val mediaSessionCompat = MediaSessionCompat(this, "MyPlayerService")
        mediaSessionCompat.isActive = true
        playerNotificationManager!!.setMediaSessionToken(mediaSessionCompat.sessionToken)
        val playBackController = DefaultPlaybackController()
        val mediaSessionConnector = MediaSessionConnector(mediaSessionCompat, playBackController)
        mediaSessionConnector.setPlayer(player, null, object : MediaSessionConnector.CustomActionProvider {
            override fun getCustomAction(): PlaybackStateCompat.CustomAction {
                val a = PlaybackStateCompat.CustomAction.Builder("ACTION_NEW", "MY_ACTION", R.drawable.ic_home)
                return a.build()
            }

            override fun onCustomAction(action: String?, extras: Bundle?) {
            }
        })*/


        mFocusHelper = AudioFocusHelper(this)
    }


    private fun getDefaultAudioAttribute(): AudioAttributesCompat = AudioAttributesCompat.Builder()
            .setContentType(AudioAttributesCompat.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributesCompat.USAGE_MEDIA)
            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
            .build()

    var focusFlag = false

    /**
     * when you want to play you most to gain focus
     */

    fun play() {
        val tag1 = "FocusHelper"
        if (!focusFlag) {
            focusFlag = mFocusHelper.requestAudioFocus(
                    AudioFocusRequestCompat.Builder(AudioManager.AUDIOFOCUS_GAIN)
                            .setAudioAttributes(getDefaultAudioAttribute())
                            .setOnAudioFocusChangeListener(mFocusHelper.getListenerForPlayer(object : AudioFocusAwarePlayer {
                                override fun isPlaying(): Boolean {
                                    Log.e(tag1, "isPlaying")
                                    return player!!.playWhenReady
                                }

                                override fun play() {
                                    Log.e(tag1, "play")
                                    player!!.playWhenReady = true
                                }

                                override fun stop() {
                                    Log.e(tag1, "stop")
                                    player!!.playWhenReady = false
                                    focusFlag = false
                                }

                                override fun pause() {
                                    Log.e(tag1, "pause")
                                    player!!.playWhenReady = false
                                }

                                override fun setVolume(volume: Float) {
                                    Log.e(tag1, "setVolume")
                                    player!!.volume = volume

                                }

                            }))
                            .setWillPauseWhenDucked(true)
                            .build()
            )
        }

        if (focusFlag) {
            player!!.playWhenReady = true
        }
    }

    fun showNotification() {
        playerNotificationManager!!.setPlayer(player)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(tag, "/********** ON_DESTROY ********************/")
        playerNotificationManager!!.setPlayer(null)
        player!!.release()
        player = null
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(tag, "OnUnbind")
        return super.onUnbind(intent)
    }


    inner class CustomControlDispatcher : DefaultControlDispatcher() {

        override fun dispatchSetPlayWhenReady(player: Player?, playWhenReady: Boolean): Boolean {
            if (playWhenReady) {
                play()
            } else {
                player!!.playWhenReady = false
            }
            return true
        }

        override fun dispatchStop(player: Player?, reset: Boolean): Boolean {
            player!!.playWhenReady = false
            EventBus.getDefault().post(PlayerEvent(playerStop = "player has been stop"))
            return true
        }
    }

}
