package com.alebit.jelsongname;

import com.alebit.jelsongname.parser.ElsSongNameParser;
import com.alebit.jelsongname.parser.SongNameFileParseException;
import com.alebit.jelsongname.songname.ElsSong;
import com.alebit.jelsongname.songname.ElsSongList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ElsSongNameParserTest {
    @TempDir
    Path tempDir;

    private InputStream getResource(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(name);
    }

    @BeforeEach
    public void init() throws Exception {
        // Copy TWINKLE.NAM to temp dir
        Files.copy(getResource("TWINKLE.NAM"), tempDir.resolve("TWINKLE.NAM"));

        // Copy TWINKLE.NAM to twinkle dir as ELS_SONG.NAM
        tempDir.resolve("twinkle").toFile().mkdir();
        Files.copy(getResource("TWINKLE.NAM"), tempDir.resolve("twinkle").resolve("ELS_SONG.NAM"));
    }

    private void testTwinkle(ElsSongList songList) {
        ElsSong song = songList.getSong(1);
        assertNotNull(song);
        for (int i = 2; i <= 999; i++) {
            assertNull(songList.getSong(i));
        }
        testTwinkle(song);
    }

    private void testTwinkle(ElsSong song) {
        assertEquals("Twinkle, Twinkle, Little Star", song.getSongName());
        assertEquals("SONG_001", song.getFolder());
        assertFalse(song.getSecurity());
        assertEquals("", song.getModel());
        assertTrue(song.getPartUk());
        assertTrue(song.getPartLk());
        assertTrue(song.getPartPk());
        assertFalse(song.getPartLead());
        assertFalse(song.getPartKbp());
        assertTrue(song.getPartCtrl());
        assertFalse(song.getPartXg());
        assertEquals("MDR_000.MID", song.getMidFile());
        assertEquals("REG_001.B00", song.getBlkFile(1));
        for (int i = 2; i <= 999; i++) {
            assertNull(song.getBlkFile(i));
        }
        assertNull(song.getSecFile());
        for (int i = 1; i <= 999; i++) {
            assertNull(song.getSecFile(i));
        }
    }

    private void testAkaTombo(ElsSongList songList) {
        ElsSong song = songList.getSong(1);
        assertNotNull(song);
        for (int i = 2; i <= 999; i++) {
            assertNull(songList.getSong(i));
        }
        testAkaTombo(song);
    }

    private void testAkaTombo(ElsSong song) {
        assertEquals("赤とんぼ", song.getSongName());
        assertEquals("SONG_001", song.getFolder());
        assertTrue(song.getSecurity());
        assertEquals("ELS-01", song.getModel());
        assertFalse(song.getPartUk());
        assertFalse(song.getPartLk());
        assertFalse(song.getPartPk());
        assertTrue(song.getPartLead());
        assertTrue(song.getPartKbp());
        assertFalse(song.getPartCtrl());
        assertTrue(song.getPartXg());
        assertEquals("MDR_000.MID", song.getMidFile());
        assertEquals("REG_001.B00", song.getBlkFile(1));
        assertEquals("REG_002.B00", song.getBlkFile(2));
        for (int i = 3; i <= 999; i++) {
            assertNull(song.getBlkFile(i));
        }
        assertEquals("AKATOMBO.C02", song.getSecFile());
        assertEquals("AKATOMBO.C02", song.getSecFile(1));
        for (int i = 2; i <= 999; i++) {
            assertNull(song.getSecFile(i));
        }
    }

    private void testOOKi(ElsSongList songList) {
        ElsSong song = songList.getSong(1);
        assertNotNull(song);
        for (int i = 2; i <= 999; i++) {
            assertNull(songList.getSong(i));
        }
        testOOKi(song);
    }

    private void testOOKi(ElsSong song) {
        assertEquals("大きな古時計", song.getSongName());
        assertEquals("SONG_001", song.getFolder());
        assertTrue(song.getSecurity());
        assertEquals("ELS-01", song.getModel());
        assertTrue(song.getPartUk());
        assertTrue(song.getPartLk());
        assertTrue(song.getPartPk());
        assertFalse(song.getPartLead());
        assertFalse(song.getPartKbp());
        assertTrue(song.getPartCtrl());
        assertFalse(song.getPartXg());
        assertEquals("MDR_000.MID", song.getMidFile());
        assertEquals("REG_001.B00", song.getBlkFile(1));
        for (int i = 2; i <= 999; i++) {
            assertNull(song.getBlkFile(i));
        }
        assertEquals("OOKI.C02", song.getSecFile());
        assertEquals("OOKI.C02", song.getSecFile(1));
        for (int i = 2; i <= 999; i++) {
            assertNull(song.getSecFile(i));
        }
    }

    @Test
    public void testReadSongFile() throws Exception {
        ElsSongNameParser parser = new ElsSongNameParser();
        ElsSongList songList = parser.parseFile(tempDir.resolve("TWINKLE.NAM").toString());
        testTwinkle(songList);

    }

    @Test
    public void testReadFromDirectory() throws Exception {
        ElsSongNameParser parser = new ElsSongNameParser();
        ElsSongList songList = parser.parse(tempDir.resolve("twinkle").toString());
        testTwinkle(songList);
    }

    @Test
    public void testReadFromInputStream() throws Exception {
        ElsSongNameParser parser = new ElsSongNameParser();
        ElsSongList songList = parser.parse(getResource("TWINKLE.NAM"));
        testTwinkle(songList);
    }

    @Test
    public void testReadJpChar() throws Exception {
        ElsSongNameParser parser = new ElsSongNameParser();
        ElsSongList songList = parser.parse(getResource("AKATOMBO.NAM"));
        testAkaTombo(songList);
    }

    @Test
    public void testTwoSongs() throws Exception {
        ElsSongNameParser parser = new ElsSongNameParser();
        ElsSongList songList = parser.parse(getResource("TWO_SONGS.NAM"));
        assertNotNull(songList.getSong(1));
        assertNotNull(songList.getSong(2));
        for (int i = 3; i <= 999; i++) {
            assertNull(songList.getSong(i));
        }
        testTwinkle(songList.getSong(1));
        testAkaTombo(songList.getSong(2));
    }

    @Test
    public void testNumSecFiles() throws Exception {
        ElsSongNameParser parser = new ElsSongNameParser();
        ElsSongList songList = parser.parse(getResource("OOKI.NAM"));
        testOOKi(songList);
    }

    @Test
    public void testIllegal() {
        ElsSongNameParser parser = new ElsSongNameParser();
        assertThrows(SongNameFileParseException.class, () -> {parser.parse(getResource("ILLEGAL.NAM"));});
    }
}
