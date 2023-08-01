package com.bignerdranch.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import java.io.IOException


private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sounds"
private const val MAX_SOUND = 5

class BeatBox(private val assets: AssetManager) {

    lateinit var seekBar: SeekBar
    var rate: Int = 1
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUND)
        //.setAudioAttributes()
        .build()

    val sounds: List<Sound> = loadSounds()
//    init {
//        sounds = loadSounds()
//    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, rate.toFloat())
        }

    }

//    seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
//        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//
//        }
//
//        override fun onStartTrackingTouch(p0: SeekBar?) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onStopTrackingTouch(p0: SeekBar?) {
//            TODO("Not yet implemented")
//        }
//    })


    fun release(){
        soundPool.release()
    }

    private fun loadSounds(): List<Sound>{
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUND_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()

        soundNames.forEach { filename ->
            val assetPath = "$SOUND_FOLDER/$filename"
            val sound = Sound(assetPath)
            //sounds.add(sound)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException){
                Log.e(TAG, "Could not load sound $filename, ioe")
            }
        }
        return sounds
    }


    private fun load(sound: Sound){
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}