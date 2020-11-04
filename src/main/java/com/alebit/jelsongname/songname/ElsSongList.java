package com.alebit.jelsongname.songname;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

public class ElsSongList implements SongList {
    protected ElsSong[] songList = new ElsSong[999];

    @Override
    public ElsSong getSong(int index) throws SongListIndexOutOfBoundsException {
        if (index > 999 || index < 1) {
            throw new SongListIndexOutOfBoundsException("Index " + index + " is out of bounds. It should be between 1 and 999.");
        }
        return songList[index - 1];
    }

    public void setSong(int index, ElsSong song) {
        if (index > 999 || index < 1) {
            throw new SongListIndexOutOfBoundsException("Index " + index + " is out of bounds. It should be between 1 and 999.");
        }
        songList[index - 1] = song;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(toString().getBytes("SHIFT-JIS"));
        outputStream.flush();
    }

    @Override
    public void write(String directoryPath) {
        writeToFile(directoryPath + File.separator + "ELS_SONG.NAM");
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
        StringBuilder songListBuilder = new StringBuilder();
        for (int i = 0; i < songList.length; i++) {
            if (songList[i] != null) {
                songListBuilder.append(infoLine(i + 1, "SONGNAME", songList[i].getSongName()));
                songListBuilder.append(infoLine(i + 1, "FOLDER", songList[i].getFolder()));
                songListBuilder.append(infoLine(i + 1, "SECURITY", boolToOnOff(songList[i].getSecurity())));
                songListBuilder.append(infoLine(i + 1, "MODEL", songList[i].getModel()));
                songListBuilder.append(infoLine(i + 1, "PART_UK", boolToPlayOff(songList[i].getPartUk())));
                songListBuilder.append(infoLine(i + 1, "PART_LK", boolToPlayOff(songList[i].getPartLk())));
                songListBuilder.append(infoLine(i + 1, "PART_PK", boolToPlayOff(songList[i].getPartPk())));
                songListBuilder.append(infoLine(i + 1, "PART_LEAD", boolToPlayOff(songList[i].getPartLead())));
                songListBuilder.append(infoLine(i + 1, "PART_KBP", boolToPlayOff(songList[i].getPartKbp())));
                songListBuilder.append(infoLine(i + 1, "PART_CTRL", boolToPlayOff(songList[i].getPartCtrl())));
                songListBuilder.append(infoLine(i + 1, "PART_XG", boolToPlayOff(songList[i].getPartXg())));
                if (!songList[i].getMidFile().equals("")) {
                    songListBuilder.append(infoLine(i + 1, "MIDFILE", songList[i].getMidFile()));
                }
                for (int j = 0; j < songList[i].blkFile.length; j++) {
                    if (songList[i].getBlkFile(j + 1) != null) {
                        songListBuilder.append(infoLine(i + 1, "BLKFILE_" + String.format("%03d", j + 1), songList[i].getBlkFile(j + 1)));
                    }
                }
                if (songList[i].getSecurity()) {
                    String secFile = songList[i].getSecFile();
                    for (int j = 2; j <= songList[i].secFile.length; j++) {
                        if (songList[i].getSecFile(j) != null) {
                            if (secFile != null) {
                                songListBuilder.append(infoLine(i + 1, "SECFILE_" + String.format("%03d", 1), secFile));
                                secFile = null;
                            }
                            songListBuilder.append(infoLine(i + 1, "SECFILE_" + String.format("%03d", j), songList[i].getSecFile(j)));
                        }
                    }
                    if (secFile != null) {
                        songListBuilder.append(infoLine(i + 1, "SECFILE", secFile));
                    }
                }
            }
        }
        songListBuilder.append("\r\n");
        return songListBuilder.toString();
    }

    private String infoLine(int index, String songInfoName, String songInfo) {
        return songNum(index) + formattedSongInfoName(songInfoName) + "= " + songInfo + "\r\n";
    }

    private String songNum(int index) {
        return "S" + String.format("%03d", index) + ":";
    }

    private String formattedSongInfoName(String songInfoName) {
        return String.format("%1$-13s", songInfoName);
    }

    private String boolToOnOff(boolean setting) {
        if (setting) {
            return "ON";
        }
        return "OFF";
    }

    private String boolToPlayOff(boolean setting) {
        if (setting) {
            return "PLAY";
        }
        return "OFF";
    }
}
