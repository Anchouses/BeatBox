package com.bignerdranch.beatbox

import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {

    fun onButtonClicked(){
        sound?.let {
            beatBox.play(it)
        }
    }



    var sound: Sound? = null
        set(sound){
            field = sound
            notifyChange()  //Уведомляет (класс привязки) слушателей об изменении всех свойств данного экземпляра
        }

    @get:Bindable
    val title: String?
        get() = sound?.name
}