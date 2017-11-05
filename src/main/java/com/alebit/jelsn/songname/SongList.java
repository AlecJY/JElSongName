package com.alebit.jelsn.songname;

import java.util.ArrayList;

public interface SongList {
    public Song getSong(int index);

    public void write(String directory);

    public void writeToFile(String filename);
}
