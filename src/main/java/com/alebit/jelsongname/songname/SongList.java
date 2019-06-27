package com.alebit.jelsongname.songname;

public interface SongList {
    public Song getSong(int index);

    public void write(String directory);

    public void writeToFile(String filename);
}
