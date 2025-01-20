package com.example.rollhelper3.audio

import android.content.Context
import android.media.MediaPlayer
import com.example.rollhelper3.ui.storage.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AudioManager(private val context: Context, private val dataStoreManager: DataStoreManager) {

    private var mediaPlayer: MediaPlayer? = null

    // Play audio if audio is enabled
    fun playAudio(audioResId: Int) {
        if (isAudioEnabled()) {
            stopAudio() // Stop any currently playing audio
            mediaPlayer = MediaPlayer.create(context, audioResId).apply {
                start()
                setOnCompletionListener {
                    release()
                    mediaPlayer = null
                }
            }
        }
    }

    // Stop any currently playing audio
    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // Check if audio is enabled
    private fun isAudioEnabled(): Boolean {
        return runBlocking {
            dataStoreManager.getAudioEnabled().first()
        }
    }
}
