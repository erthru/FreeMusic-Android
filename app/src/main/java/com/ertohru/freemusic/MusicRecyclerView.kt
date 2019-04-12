package com.ertohru.freemusic

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_music_main.view.*

class MusicRecyclerView(val context:Context, val data:ArrayList<Music>?) : RecyclerView.Adapter<MusicRecyclerView.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_music_main,parent,false))

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.create(context,data,position)

    }


    class Holder(val v: View): RecyclerView.ViewHolder(v){

        fun create(context:Context, data: ArrayList<Music>?, position: Int){

            v.tvNameLMM.text = data?.get(position)?.title
            v.tvArtistLMM.text = data?.get(position)?.artist
            Glide.with(context).load(data?.get(position)?.art).into(v.imgArtLMM)

            v.layoutLMM.setOnClickListener {

                val intent = Intent()
                intent.putExtra("position",position.toString())
                intent.action = "MUSIC_PLAY"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.sendBroadcast(intent)

            }

        }

    }
}