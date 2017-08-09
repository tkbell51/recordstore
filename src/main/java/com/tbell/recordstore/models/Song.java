package com.tbell.recordstore.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String songTitle;
    private String genre;
    private Date songReleaseDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;

    public Song() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Date getSongReleaseDate() {
        return songReleaseDate;
    }

    public void setSongReleaseDate(Date songReleaseDate) {
        this.songReleaseDate = songReleaseDate;
    }
}
