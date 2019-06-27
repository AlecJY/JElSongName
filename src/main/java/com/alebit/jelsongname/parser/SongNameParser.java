package com.alebit.jelsongname.parser;

import com.alebit.jelsongname.songname.SongList;

import java.io.InputStream;

public interface SongNameParser {
    public SongList parse(InputStream inputStream) throws SongNameFileIOException, SongNameFileParseException;

    public SongList parse(String directoryPath) throws SongNameFileIOException, SongNameFileParseException;

    public SongList parseFile(String filename) throws SongNameFileIOException, SongNameFileParseException;
}
