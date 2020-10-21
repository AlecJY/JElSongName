package com.alebit.jelsongname.songname;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ElSongList implements SongList {
    protected ElSong[] songList = new ElSong[40];

    @Override
    public ElSong getSong(int index) {
        if (index > 40 || index < 1) {
            throw new SongListIndexOutOfBoundsException("Index " + index + " is out of bounds. It should be between 1 and 40.");
        }
        return songList[index - 1];
    }

    public void setSong(int index, ElSong song) {
        if (index > 40 || index < 1) {
            throw new SongListIndexOutOfBoundsException("Index " + index + " is out of bounds. It should be between 1 and 40.");
        }
        songList[index - 1] = song;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(toString().getBytes("SHIFT-JIS"));
        outputStream.flush();
    }

    @Override
    public void write(String directory) {
        writeToFile(directory + File.separator + "SONG.NAM");
    }

    @Override
    public void writeToFile(String filename) {
        Path path = Paths.get(filename);
        try {
            Files.write(path, toString().getBytes("SHIFT-JIS"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder songNameBilder = new StringBuilder();
        for (ElSong song: songList) {
            songNameBilder.append(String.format("%1$-17s", song.getSongName()) + "\r\n");
        }
        return songNameBilder.toString();
    }
}
