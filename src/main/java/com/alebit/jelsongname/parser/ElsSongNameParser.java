package com.alebit.jelsongname.parser;

import com.alebit.jelsongname.songname.ElsSongList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class ElsSongNameParser implements SongNameParser {
    public ElsSongList parse(InputStream inputStream) throws SongNameFileIOException, SongNameFileParseException {
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

    public ElsSongList parse(String directoryPath) throws SongNameFileIOException, SongNameFileParseException {
        return parseFile(directoryPath + File.separator + "ELS_SONG.NAM");
    }

    public ElsSongList parseFile(String filename) throws SongNameFileIOException, SongNameFileParseException {
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

    private ElsSongList parseBytes(byte[] songNameBytes) throws SongNameFileParseException {
        String songNameStr;
        try {
            songNameStr = new String(songNameBytes, "SHIFT-JIS");
        } catch (UnsupportedEncodingException e) {
            throw new SongNameFileParseException();
        }
        return  parser(songNameStr);
    }

    protected ElsSongList parser(String rawStr) throws SongNameFileParseException {
        ElsSongList elsSongList = new ElsSongList();
        StringTokenizer tokenizer = new StringTokenizer(rawStr);
        while (tokenizer.hasMoreTokens()) {
            String songNumStr = tokenizer.nextToken(":").trim();
            if (songNumStr.equals("")) {
                continue;
            }

            int songNum;
            try {
                songNum = Integer.parseInt(songNumStr.substring(1));
            } catch (Exception e) {
                throw new SongNameFileParseException();
            }

            String songInfoName = tokenizer.nextToken("=").substring(1).trim();
            String songInfo = tokenizer.nextToken("\r\n").substring(2).trim();
            int blkNum = 0;
            if (songInfoName.startsWith("BLKFILE")) {
                try {
                    blkNum = Integer.parseInt(songInfoName.substring(8));
                    songInfoName = "BLKFILE";
                } catch (Exception e) {
                    throw new SongNameFileParseException();
                }
            }
            switch (songInfoName) {
                case "SONGNAME":
                    elsSongList.getSong(songNum).setSongName(songInfo);
                    break;
                case "FOLDER":
                    elsSongList.getSong(songNum).setFolder(songInfo);
                    break;
                case "SECURITY":
                    if (songInfo.equals("ON")) {
                        elsSongList.getSong(songNum).setSecurity(true);
                    } else if (songInfo.equals("OFF")) {
                        elsSongList.getSong(songNum).setSecurity(false);
                    } else {
                        throw new SongNameFileParseException();
                    }
                    break;
                case "MODEL":
                    elsSongList.getSong(songNum).setModel(songInfo);
                    break;
                case "PART_UK":
                    elsSongList.getSong(songNum).setPartUk(parsePlayOff(songInfo));
                    break;
                case "PART_LK":
                    elsSongList.getSong(songNum).setPartLk(parsePlayOff(songInfo));
                    break;
                case "PART_PK":
                    elsSongList.getSong(songNum).setPartPk(parsePlayOff(songInfo));
                    break;
                case "PART_LEAD":
                    elsSongList.getSong(songNum).setPartLead(parsePlayOff(songInfo));
                    break;
                case "PART_KBP":
                    elsSongList.getSong(songNum).setPartKbp(parsePlayOff(songInfo));
                    break;
                case "PART_CTRL":
                    elsSongList.getSong(songNum).setPartCtrl(parsePlayOff(songInfo));
                    break;
                case "PART_XG":
                    elsSongList.getSong(songNum).setPartXg(parsePlayOff(songInfo));
                    break;
                case "MIDFILE":
                    elsSongList.getSong(songNum).setMidFile(songInfo);
                    break;
                case "BLKFILE":
                    elsSongList.getSong(songNum).setBlkFile(blkNum, songInfo);
                    break;
                case "SECFILE":
                    elsSongList.getSong(songNum).setSecFile(songInfo);
                    break;
                default:
                    System.err.println(songInfoName);
                    throw new SongNameFileParseException();
            }
        }
        return elsSongList;
    }

    private boolean parsePlayOff(String songInfo) throws SongNameFileParseException {
        if (songInfo.equals("PLAY")) {
            return true;
        } else if (songInfo.equals("OFF")) {
            return false;
        } else {
            throw new SongNameFileParseException();
        }
    }
}
