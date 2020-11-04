package com.alebit.jelsongname;

import com.alebit.jelsongname.songname.ElsSong;
import com.alebit.jelsongname.songname.ElsSongList;
import com.alebit.jelsongname.songname.IndexOutOfBoundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ElsSongNameListTest {
    private InputStream getResource(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(name);
    }

    private byte[] readAllBytes(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        return outputStream.toByteArray();
    }

    private ElsSong getTwinkle() {
        ElsSong song = new ElsSong();
        song.setSongName("Twinkle, Twinkle, Little Star");
        song.setFolder("SONG_001");
        song.setBlkFile(1, "REG_001.B00");
        song.setMidFile("MDR_000.MID");
        song.setPartUk(true);
        song.setPartLk(true);
        song.setPartPk(true);
        song.setPartCtrl(true);
        return song;
    }

    private ElsSong getAkaTombo() {
        ElsSong song = new ElsSong();
        song.setSongName("赤とんぼ");
        song.setFolder("SONG_001");
        song.setSecurity(true);
        song.setModel("ELS-01");
        song.setBlkFile(1, "REG_001.B00");
        song.setBlkFile(2, "REG_002.B00");
        song.setMidFile("MDR_000.MID");
        song.setPartLead(true);
        song.setPartKbp(true);
        song.setPartXg(true);
        song.setSecFile("AKATOMBO.C02");
        return song;
    }

    @Test
    public void testSongListString() throws Exception {
        ElsSong song = getTwinkle();
        ElsSongList songList = new ElsSongList();
        songList.setSong(1, song);
        String listString = songList.toString().trim();
        String expectedListString = new String(readAllBytes(getResource("TWINKLE.NAM")), "SHIFT-JIS").trim();
        assertEquals(expectedListString, listString);
    }

    @Test
    public void testWriteSongList() throws Exception {
        ElsSong song = getTwinkle();
        ElsSongList songList = new ElsSongList();
        songList.setSong(1, song);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        songList.write(outputStream);
        byte[] list = outputStream.toByteArray();
        byte[] expectedList = readAllBytes(getResource("TWINKLE.NAM"));
        assertArrayEquals(expectedList, list);
    }

    @Test
    public void testWriteSongListToDir(@TempDir Path tempDir) throws Exception {
        ElsSong song = getTwinkle();
        ElsSongList songList = new ElsSongList();
        songList.setSong(1, song);
        songList.write(tempDir.toString());
        byte[] list = Files.readAllBytes(tempDir.resolve("ELS_SONG.NAM"));
        byte[] expectedList = readAllBytes(getResource("TWINKLE.NAM"));
        assertArrayEquals(expectedList, list);
    }

    @Test
    public void testWriteSongListToFile(@TempDir Path tempDir) throws Exception {
        ElsSong song = getTwinkle();
        ElsSongList songList = new ElsSongList();
        songList.setSong(1, song);
        songList.writeToFile(tempDir.resolve("TWINKLE.NAM").toString());
        byte[] list = Files.readAllBytes(tempDir.resolve("TWINKLE.NAM"));
        byte[] expectedList = readAllBytes(getResource("TWINKLE.NAM"));
        assertArrayEquals(expectedList, list);
    }

    @Test
    public void testWriteSongListJPChar() throws Exception {
        ElsSong song = getAkaTombo();
        ElsSongList songList = new ElsSongList();
        songList.setSong(1, song);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        songList.write(outputStream);
        byte[] list = outputStream.toByteArray();
        byte[] expectedList = readAllBytes(getResource("AKATOMBO.NAM"));
        assertArrayEquals(expectedList, list);
    }

    @Test
    public void testWriteTwoSongs() throws Exception {
        ElsSong song1 = getTwinkle();
        ElsSong song2 = getAkaTombo();
        ElsSongList songList = new ElsSongList();
        songList.setSong(1, song1);
        songList.setSong(2, song2);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        songList.write(outputStream);
        byte[] list = outputStream.toByteArray();
        byte[] expectedList = readAllBytes(getResource("TWO_SONGS.NAM"));
        assertArrayEquals(expectedList, list);
    }

    @Test
    public void testSongListOutOfBounds() {
        ElsSongList songList = new ElsSongList();
        assertThrows(IndexOutOfBoundsException.class, () -> {songList.getSong(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {songList.getSong(1000);});
        assertThrows(IndexOutOfBoundsException.class, () -> {songList.setSong(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {songList.setSong(1000, null);});
    }

    @Test
    public void testSongOutOfBounds() {
        ElsSong song = new ElsSong();
        assertThrows(IndexOutOfBoundsException.class, () -> {song.getBlkFile(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.getBlkFile(1000);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.setBlkFile(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.setBlkFile(1000, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.getSecFile(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.getSecFile(1000);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.setSecFile(0, null);});
        assertThrows(IndexOutOfBoundsException.class, () -> {song.setSecFile(1000, null);});
    }
}
