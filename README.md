# JElSongName
[![Build Status](https://github.com/AlecJY/JElSongName/actions/workflows/gradle.yml/badge.svg)](https://github.com/AlecJY/JElSongName/actions/workflows/gradle.yml)

A Java Electone song name parser library for Electone EL and ELS Series 

## Supported Format
* SONG.NAM
* ELS_SONG.NAM

## Using JELSongName
JELSongName is now uploaded to Maven Central

### Maven
```xml
<dependency>
    <groupId>com.alebit.jelsongname</groupId>
    <artifactId>jelsongname</artifactId>
    <version>1.2</version>
</dependency>
```

### Gradle
```groovy
compile group: 'com.alebit.jelsongname', name: 'jelsongname', version: '1.2'
```

## Getting Started
Parse a ELS song name file and print the first song's name.
```java
// Create a new ELS song name parser
ElsSongNameParser parser = new ElsSongNameParser();
try {
    // Load ELS song name file from the specific directory
    ElsSongList songList = parser.parse("D:\\songs");
    // Print the first song's name
    System.out.println(songList.getSong(1).getSongName());
} catch (SongNameFileIOException|SongNameFileParseException e) {
    e.printStackTrace();
}
```

Create a new ELS song name list and write to a directory
```java
// Create a new ELS song then set properties
ElsSong song = new ElsSong();
song.setSongName("Twinkle, Twinkle, Little Star");
song.setFolder("SONG_001");
song.setBlkFile(1, "REG_001.B00");
song.setMidFile("MDR_000.MID");
song.setPartUk(true);
song.setPartLk(true);
song.setPartPk(true);
song.setPartCtrl(true);

// Create a new ELS song list then set the first song to the song created above
ElsSongList songList = new ElsSongList();
songList.setSong(1, song);

// Write song name file to the specific directory
songList.write("D:\\new_songs");
```

