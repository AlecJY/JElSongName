package com.alebit.jelsn.songname;

public class ElSong implements Song {
    protected String songName = "";

    @Override
    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public String getSongName() {
        return songName;
    }
}
