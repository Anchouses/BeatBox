package com.bignerdranch.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.beatbox.databinding.ActivityMainBinding
import com.bignerdranch.beatbox.databinding.ListItemSoundBinding



class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox //= ViewModelProvider(this)[BeatBox::class.java]
    private lateinit var seek: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        beatBox = BeatBox(assets)

        binding.recyclerView.apply{
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }

        seek = binding.seekBar

//            .setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//                TODO("Not yet implemented")
//            }
//        })

    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding):
            RecyclerView.ViewHolder(binding.root){
                init{
                    binding.viewModel = SoundViewModel(beatBox)
                }

                fun bind(sound: Sound){
                    binding.apply{
                        viewModel?.sound = sound
                        executePendingBindings()  // дает указание функции обновить себя немедленно
                    }
                }
            }

    private inner class SoundAdapter(private val sounds: List<Sound>): RecyclerView.Adapter<SoundHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size
    }
}