package com.ertohru.freemusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.hide()
        viewHandling()

    }

    override fun onResume() {
        super.onResume()

        if(MusicController.mp() != null){
            if(MusicController.isPlaying()){
                btnPlayDetail.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)
            }else{
                btnPlayDetail.background = resources.getDrawable(R.drawable.ic_play_arrow_white_24dp)
            }
        }

    }

    fun viewHandling(){

        setupSeekBar()

        btnBackDetail.setOnClickListener {
            this.finish()
        }

        resetView()

        btnPlayDetail.setOnClickListener {

            if(MusicController.mp() != null){
                if(MusicController.isPlaying()){
                    btnPlayDetail.background = resources.getDrawable(R.drawable.ic_play_arrow_white_24dp)
                    MusicController.pause()
                }else{
                    btnPlayDetail.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)
                    MusicController.resume()
                }
            }else{
                MusicController.replay()
                btnPlayDetail.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)
            }

        }

        btnNextDetail.setOnClickListener {
            MusicController.next()
            resetView()
        }

        btnPreviousDetail.setOnClickListener {
            MusicController.previous()
            resetView()
        }

        MusicController.mp()?.setOnCompletionListener {
            MusicController.stop()
            btnPlayDetail.background = resources.getDrawable(R.drawable.ic_play_arrow_white_24dp)
        }

    }

    private fun resetView(){
        tvTitleDetail.text = MusicController.title()
        tvArtistDetail.text = MusicController.artist()
        Glide.with(this).load(MusicController.art()).into(imgArtDetail)
    }

    private fun setupSeekBar(){

        sbDetail.max = MusicController.totalDuration()

        Timer().scheduleAtFixedRate(object : TimerTask(){

            override fun run() {

                sbDetail.progress = MusicController.currentPosition()

            }

        }, 0, 1000)

        sbDetail.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {

                MusicController.seekTo(progress)

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

}
