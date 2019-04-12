package com.ertohru.freemusic

data class Music(var title:String, var artist:String, var file:String, var art:String){

    override fun toString(): String {
        return "$title, $artist, $file, $art"
    }

}