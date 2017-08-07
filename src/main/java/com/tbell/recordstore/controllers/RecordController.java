package com.tbell.recordstore.controllers;

import com.tbell.recordstore.Repository.AlbumRepository;
import com.tbell.recordstore.Repository.BandRepository;
import com.tbell.recordstore.Repository.SongRepository;
import com.tbell.recordstore.models.Album;
import com.tbell.recordstore.models.Band;
import com.tbell.recordstore.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class RecordController {

    @Autowired
    BandRepository bandRepo;

    @Autowired
    AlbumRepository albumRepo;

    @Autowired
    SongRepository songRepo;

    @RequestMapping("/")
    private String index(){return "index"; }

    @RequestMapping("/search")
    private String searchPage(){ return "search";}

    @RequestMapping(value = "/searchResults", method = RequestMethod.POST)
    private String searchBand(@RequestParam("bandTitle")String bandTitle,
                              Model model){
    Band band = bandRepo.findByBandTitle(bandTitle);
    model.addAttribute("band", band);

    Iterable<Album> albums = albumRepo.findAllByBand(band);
    model.addAttribute("album", albums);

//    Album bandAlbum = albumRepo.findAllByBand(band);

//    Iterable<Song> albumSongs = songRepo.findAllByAlbum(band);
//    model.addAttribute("albumSong", albumSongs);

    Iterable<Song> songs = songRepo.findAllByBand(band);
    model.addAttribute("song", songs);
    return "searchResults" ;

    }
    @RequestMapping("/band")
    public String band(Model model){

        Iterable<Band> bands = bandRepo.findAll();
        model.addAttribute("band", bands);
        Iterable<Album> albums = albumRepo.findAll();
        model.addAttribute("album", albums);
        Iterable<Song> songs = songRepo.findAll();
        model.addAttribute("song", songs);

        return "band";
    }
    @RequestMapping("/album")
    public String album(Model model){

        Iterable<Band> bands = bandRepo.findAll();
        model.addAttribute("band", bands);
        Iterable<Album> albums = albumRepo.findAll();
        model.addAttribute("album", albums);
        Iterable<Song> songs = songRepo.findAll();
        model.addAttribute("song", songs);

        return "album";
    }
    @RequestMapping("/song")
    public String song(Model model){

        Iterable<Band> bands = bandRepo.findAll();
        model.addAttribute("band", bands);
        Iterable<Album> albums = albumRepo.findAll();
        model.addAttribute("album", albums);
        Iterable<Song> songs = songRepo.findAll();
        model.addAttribute("song", songs);

        return "song";
    }

    @RequestMapping(value = "/createBand", method = RequestMethod.POST)
    public String createBand(@RequestParam("title")String title,
                             @RequestParam("origin")String origin,
                             @RequestParam("bandArt")String bandArt
                             ){

        Band band = new Band();
        band.setBandTitle(title);
        band.setOrigin(origin);
        band.setBandArtUrl(bandArt);
        bandRepo.save(band);
        return "redirect:/band";
    }

    @RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
    public String createAlbum(@RequestParam("title")String title,
                             @RequestParam("releaseDate")Date releaseDate,
                             @RequestParam("albumArt")String albumArt,
                              @RequestParam("band")String bandId,
                              Model model){


        Album album = new Album();
        album.setAlbumTitle(title);
        album.setAlbumArtUrl(albumArt);
        album.setReleaseDate(releaseDate);
        try {
            long listId = Long.parseLong(bandId);
            Band band = bandRepo.findOne(listId);
            album.setBand(band);
            albumRepo.save(album);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/album";
    }

    @RequestMapping(value = "/createSong", method = RequestMethod.POST)
    public String createSong(@RequestParam("title")String title,
                             @RequestParam("genre")String genre,
                             @RequestParam("releaseDate")Date releaseDate,
                             @RequestParam("band")String bandId,
                             @RequestParam("album")String albumId,
                             Model model){
        Iterable<Band> bands = bandRepo.findAll();
        model.addAttribute("bands", bands);

        Iterable<Album> albums = albumRepo.findAll();
        model.addAttribute("albums", albums);

        Song song = new Song();
        song.setSongTitle(title);
        song.setGenre(genre);
        try {
            long listId = Long.parseLong(bandId);
            Band band = bandRepo.findOne(listId);
            song.setBand(band);

            long albId = Long.parseLong(albumId);
            Album album = albumRepo.findOne(albId);
            song.setAlbum(album);
            songRepo.save(song);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/song";
    }

}
