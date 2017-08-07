package com.tbell.recordstore.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "band")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String bandTitle;
    private String origin;
    private String bandArtUrl;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Album> albums;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL)
    private List<Song> songs;

    public Band() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBandTitle() {
        return bandTitle;
    }

    public void setBandTitle(String bandTitle) {
        this.bandTitle = bandTitle;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getBandArtUrl() {
        return bandArtUrl;
    }

    public void setBandArtUrl(String bandArtUrl) {
        this.bandArtUrl = bandArtUrl;
    }
}
