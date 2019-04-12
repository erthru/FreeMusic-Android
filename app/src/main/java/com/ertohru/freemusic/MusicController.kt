package com.ertohru.freemusic

import android.media.MediaPlayer

class MusicController {

    companion object {
        private var mp: MediaPlayer? = null
        private var isPlaying = false
        private var tracks = ArrayList<Music>()
        private var currentIndex = 0

        private var onPlayingTitle = ""
        private var onPlayingArtist = ""
        private var onPlayingFile = ""
        private var onPlayingArt = ""

        fun play(index:Int){

            onPlayingTitle = tracks[index].title
            onPlayingArtist = tracks[index].artist
            onPlayingArt = tracks[index].art
            onPlayingFile = tracks[index].file
            currentIndex = index

            if(MusicController.isPlaying){
                stop()
            }

            mp = MediaPlayer()
            mp?.setDataSource(onPlayingFile)
            mp?.prepare()
            mp?.start()
            isPlaying = true

        }

        fun next(){

            if((tracks.size - 1) == currentIndex){
                play(currentIndex)
            }else{
                play(currentIndex + 1)
            }

        }

        fun previous(){

            if(currentIndex == 0){
                play(currentIndex)
            }else{
                play(currentIndex - 1)
            }

        }

        fun stop(){
            mp?.reset()
            mp?.stop()
            mp = null
            isPlaying = false
        }

        fun replay(){
            onPlayingTitle = tracks[currentIndex].title
            onPlayingArtist = tracks[currentIndex].artist
            onPlayingArt = tracks[currentIndex].art
            onPlayingFile = tracks[currentIndex].file

            if(MusicController.isPlaying){
                stop()
            }

            mp = MediaPlayer()
            mp?.setDataSource(onPlayingFile)
            mp?.prepare()
            mp?.start()
            isPlaying = true
        }

        fun isPlaying() : Boolean{
            return isPlaying
        }

        fun pause(){

            if(mp != null){
                mp?.pause()
                isPlaying = false
            }

        }

        fun resume(){

            if(mp != null){
                mp?.start()
                isPlaying = true
            }

        }

        fun setTrack(tracks:ArrayList<Music>?){
            this.tracks.clear()
            this.tracks = tracks!!
        }

        fun seekTo(to:Int){
            if(mp != null){
                mp?.seekTo(to)
            }
        }

        fun currentPosition() : Int = mp?.currentPosition ?: 0
        fun totalDuration() : Int = mp?.duration ?: 0
        fun title() : String = onPlayingTitle
        fun artist() : String = onPlayingArtist
        fun art() : String = onPlayingArt
        fun file() : String = onPlayingFile
        fun mp() : MediaPlayer? = mp

    }



}