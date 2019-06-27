package com.alebit.jelsongname.parser;

import com.alebit.jelsongname.songname.SongList;

public interface SongNameParser {
    public SongList parse(String directoryPath) throws SongNameFileIOException, SongNameFileParseException;

    public SongList parseFile(String filename) throws SongNameFileIOException, SongNameFileParseException;
}
