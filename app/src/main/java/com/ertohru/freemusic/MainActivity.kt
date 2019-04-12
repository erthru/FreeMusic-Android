package com.ertohru.freemusic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        loadMusicFromApi()

        registerReceiver(bcPlay, IntentFilter("MUSIC_PLAY"))

        viewHandling()

    }

    override fun onResume() {
        super.onResume()

        if(MusicController.mp() != null){

            resetView()

            if(MusicController.isPlaying()){
                btnPlayMain.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)
            }else{
                btnPlayMain.background = resources.getDrawable(R.drawable.ic_play_arrow_white_24dp)
            }
        }else{
            layoutOnPlayingMain.visibility = View.GONE
        }

        MusicController.mp()?.setOnCompletionListener {
            MusicController.stop()
            layoutOnPlayingMain.visibility = View.GONE
        }

    }

    private fun viewHandling(){

        btnPlayMain.setOnClickListener {

            if(MusicController.mp() != null){
                if(MusicController.isPlaying()){
                    btnPlayMain.background = resources.getDrawable(R.drawable.ic_play_arrow_white_24dp)
                    MusicController.pause()
                }else{
                    btnPlayMain.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)
                    MusicController.resume()
                }
            }else{

                MusicController.replay()
                btnPlayMain.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)

            }

        }

        btnNextMain.setOnClickListener {
            MusicController.next()
            resetView()
        }

        btnPreviousMain.setOnClickListener {
            MusicController.previous()
            resetView()
        }

        layoutOnPlayingMain.setOnClickListener {
            startActivity(Intent(applicationContext, DetailActivity::class.java))
        }


    }

    private fun resetView(){
        tvSongOnPlayingMain.text = MusicController.title()
        tvArtistOnPlayingMain.text = MusicController.artist()
        Glide.with(this@MainActivity).load(MusicController.art()).into(imgOnPlayingMain)
    }

    private val bcPlay = object : BroadcastReceiver(){

        override fun onReceive(p0: Context?, p1: Intent?) {

            layoutOnPlayingMain.visibility = View.VISIBLE
            btnPlayMain.background = resources.getDrawable(R.drawable.ic_pause_white_24dp)

            var musicIndex = p1?.getStringExtra("position")
            MusicController.play(musicIndex?.toInt()!!)

            tvSongOnPlayingMain.text = MusicController.title()
            tvArtistOnPlayingMain.text = MusicController.artist()
            Glide.with(this@MainActivity).load(MusicController.art()).into(imgOnPlayingMain)

        }

    }


    private fun loadMusicFromApi(){

        pbMain.visibility = View.VISIBLE

        AndroidNetworking.get("http://192.168.56.1/anows/freemusic/music.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{

                override fun onResponse(response: JSONObject?) {

                    pbMain.visibility = View.GONE

                    var data = ArrayList<Music>()

                    val jsonArray = response?.getJSONArray("music")

                    for(i in 0 until jsonArray?.length()!!){

                        data.add(Music(
                            jsonArray.getJSONObject(i).getString("title"),
                            jsonArray.getJSONObject(i).getString("artist"),
                            jsonArray.getJSONObject(i).getString("file"),
                            jsonArray.getJSONObject(i).getString("art")
                        ))

                    }

                    MusicController.setTrack(data)
                    val adapter = MusicRecyclerView(applicationContext,data)
                    rvMusicMain.setHasFixedSize(true)
                    rvMusicMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvMusicMain.adapter = adapter

                }

                override fun onError(anError: ANError?) {

                    pbMain.visibility = View.GONE
                    Toast.makeText(applicationContext,"Connection failed.",Toast.LENGTH_SHORT).show()
                    Log.d("ONERROR",anError.toString())

                }

            })

    }

}
