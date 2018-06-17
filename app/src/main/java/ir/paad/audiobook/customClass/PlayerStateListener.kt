package ir.paad.audiobook.customClass

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray

class PlayerStateListener(private val stateChangeListener: OnPlayerStateChanged, private val playWhenReadyFomPlayer: Boolean) : Player.EventListener {

    private var mPlayWhenReady = playWhenReadyFomPlayer

    interface OnPlayerStateChanged {
        fun onPause()
        fun onPlay()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (!(mPlayWhenReady && playWhenReady)) {
            mPlayWhenReady = playWhenReady
            if (playWhenReady) {
                stateChangeListener.onPlay()
            } else {
                stateChangeListener.onPause()
            }
        }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

    }

    override fun onSeekProcessed() {

    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {

    }

    override fun onPlayerError(error: ExoPlaybackException?) {

    }

    override fun onLoadingChanged(isLoading: Boolean) {

    }

    override fun onPositionDiscontinuity(reason: Int) {

    }

    override fun onRepeatModeChanged(repeatMode: Int) {

    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

    }


}