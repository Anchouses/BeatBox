package com.bignerdranch.beatbox

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.withSettings

class SoundViewModelTest {

    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel
    private lateinit var beatBox: BeatBox

    @Before
    fun setUp() {
        beatBox = mock(BeatBox::class.java, withSettings().serializable())  //функция mock  автоматически создает имитацию BeatBox
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle(){
        assertThat(subject.title, `is` (sound.name))
    }

    @Test
    fun callsBeatBoxPlayOnClicked(){
        subject.onButtonClicked()

        verify(beatBox).play(sound)
    }
}