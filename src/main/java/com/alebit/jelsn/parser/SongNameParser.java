package com.alebit.jelsn.parser;

import com.alebit.jelsn.songname.SongList;

public interface SongNameParser {
    public SongList parse(String directoryPath) throws SongNameFileIOException, SongNameFileParseException;

    public SongList parseFile(String filename) throws SongNameFileIOException, SongNameFileParseException;
}
