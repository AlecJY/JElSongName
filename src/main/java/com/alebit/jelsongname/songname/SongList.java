package com.alebit.jelsongname.songname;

import java.io.IOException;
import java.io.OutputStream;

public interface SongList {
    public Song getSong(int index);

    public void write(OutputStream outputStream) throws IOException;

    public void write(String directory);

    public void writeToFile(String filename);
}
