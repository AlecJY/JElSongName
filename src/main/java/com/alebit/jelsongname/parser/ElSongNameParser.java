package com.alebit.jelsongname.parser;

import com.alebit.jelsongname.songname.ElSong;
import com.alebit.jelsongname.songname.ElSongList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ElSongNameParser implements SongNameParser {
    public ElSongList parse(InputStream inputStream) throws SongNameFileIOException, SongNameFileParseException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read;
        try {
            while ((read = inputStream.read(buffer)) > 1) {
                outputStream.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new SongNameFileIOException();
        }
        return parseBytes(outputStream.toByteArray());
    }

    public ElSongList parse(String directoryPath) throws SongNameFileIOException, SongNameFileParseException {
        return parseFile(directoryPath + File.separator + "SONG.NAM");
    }

    public ElSongList parseFile(String filename) throws SongNameFileIOException, SongNameFileParseException {
        Path songNamePath = Paths.get(filename);
        if (!songNamePath.toFile().exists()) {
            throw new SongNameFileIOException();
        }
        byte[] songNameBytes;
        try {
            songNameBytes = Files.readAllBytes(songNamePath);
        } catch (IOException e) {
            throw new SongNameFileIOException();
        }
        return parseBytes(songNameBytes);
    }

    private ElSongList parseBytes(byte[] songNameBytes) throws SongNameFileParseException {
        String songNameStr;
        try {
            songNameStr = new String(songNameBytes, "SHIFT-JIS");
        } catch (UnsupportedEncodingException e) {
            throw new SongNameFileParseException();
        }
        String[] songNames = songNameStr.split("[\\r\\n]+");
        ElSongList songList = new ElSongList();
        for (int i = 0; i < 40; i++) {
            ElSong song = new ElSong();
            song.setSongName(songNames[i]);
            songList.setSong(i + 1, song);
        }
        return songList;
    }
}
