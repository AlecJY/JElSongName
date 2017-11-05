package com.alebit.jelsn.parser;

import com.alebit.jelsn.songname.SongList;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface SongNameParser {
    public SongList parse(String directoryPath) throws SongNameFileIOException, SongNameFileParseException;

    public SongList parseFile(String filename) throws SongNameFileIOException, SongNameFileParseException;
}
