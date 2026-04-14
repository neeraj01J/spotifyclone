package com.example.spotifyclone

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class PlayerViewModel : ViewModel() {
    private var exoPlayer: ExoPlayer? = null
    
    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    fun initPlayer(context: Context) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                playWhenReady = true
                addListener(object : androidx.media3.common.Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _isPlaying.value = isPlaying
                        if (isPlaying) {
                            startProgressUpdate()
                        }
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == androidx.media3.common.Player.STATE_READY) {
                            _duration.value = duration
                        }
                    }
                })
            }
        }
    }

    private fun startProgressUpdate() {
        viewModelScope.launch {
            while (_isPlaying.value) {
                _currentPosition.value = exoPlayer?.currentPosition ?: 0L
                delay(1000)
            }
        }
    }

    fun playSong(song: Song) {
        _currentSong.value = song
        exoPlayer?.let { player ->
            val mediaItem = MediaItem.fromUri(song.url)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    fun seekTo(position: Long) {
        exoPlayer?.seekTo(position)
    }

    fun togglePlayPause() {
        exoPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        }
    }

    fun seekForward() {
        exoPlayer?.let { player ->
            val currentPos = player.currentPosition
            player.seekTo(currentPos + 10000) // Forward 10 seconds
        }
    }

    fun seekBackward() {
        exoPlayer?.let { player ->
            val currentPos = player.currentPosition
            player.seekTo(maxOf(0, currentPos - 10000)) // Backward 10 seconds
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
        exoPlayer = null
    }
}
