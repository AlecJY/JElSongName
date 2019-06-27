package com.alebit.jelsongname.songname;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ElSongList implements SongList {
    protected ElSong[] songList = new ElSong[40];

    @Override
    public ElSong getSong(int index) {
        return songList[index - 1];
    }

    public void setSong(int index, ElSong song) {
        songList[index - 1] = song;
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
