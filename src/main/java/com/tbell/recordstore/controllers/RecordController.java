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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/searchBand", method = RequestMethod.POST)
    private String searchBand(@RequestParam("bandTitle")String bandTitle,
                              Model model){

    Band band = bandRepo.findByBandTitle(bandTitle);
    model.addAttribute("band", band);

    Iterable<Album> albums = albumRepo.findAllByBand(band);
    model.addAttribute("album", albums);
//    Iterable<Song> songs = songRepo.findAllByAlbum();
////        for (Album album : albums)
////              {
////
////        }

    Iterable<Song> bandSongs = songRepo.findAllByBand(band);
    model.addAttribute("song", bandSongs);


    return "searchResults" ;

    }
    @RequestMapping(value = "/searchAlbum", method = RequestMethod.POST)
    public String searchAlbum(@RequestParam("albumTitle")String albumTitle,
                              Model model){
        Album album = albumRepo.findByAlbumTitle(albumTitle);
        Iterable<Song> songs = songRepo.findAllByAlbum(album);
        model.addAttribute("album", album);
        model.addAttribute("song", songs);
        return"searchResults";
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

    @RequestMapping("/band/{bandId}")
    public String bandDetails(@PathVariable("bandId")long bandId,
                               Model model){
        Band band = bandRepo.findOne(bandId);
        Iterable<Album> albums = albumRepo.findAllByBand(band);
        model.addAttribute("band", band);
        model.addAttribute("album", albums);
        Iterable<Song> songs = songRepo.findAllByBand(band);
        model.addAttribute("song", songs);
        return "bandDetails";
    }
    @RequestMapping(value = "/band/{bandId}/edit", method = RequestMethod.POST)
    public String editBand(@PathVariable("bandId")long bandId,
                           @RequestParam("title")String title,
                           @RequestParam("origin")String origin,
                           @RequestParam("bandArt")String bandArt
    ){

        Band band = bandRepo.findOne(bandId);
        band.setBandTitle(title);
        band.setOrigin(origin);
        band.setBandArtUrl(bandArt);
        bandRepo.save(band);
        return "redirect:/band/" + bandId;
    }
    @RequestMapping(value = "/band/{bandId}/delete", method = RequestMethod.POST)
    public String deleteBand(@PathVariable("bandId")long bandId){
        Band band = bandRepo.findOne(bandId);
        bandRepo.delete(band);
        return "redirect:/band/";
    }
    @RequestMapping("/album/{albumId}")
    public String albumDetails(@PathVariable("albumId")long albumId,
                               Model model){
        Album album = albumRepo.findOne(albumId);
        Iterable<Song> songs = songRepo.findAllByAlbum(albumId);
        model.addAttribute("album", album);
        model.addAttribute("song", songs);
        return "albumDetails";
    }

    @RequestMapping(value = "/album/{albumId}/edit", method = RequestMethod.POST)
    public String createAlbum(@PathVariable("albumId")long albumId,
                              @RequestParam("title")String title,
                              @RequestParam("releaseDate")Date releaseDate,
                              @RequestParam("albumArt")String albumArt,
                              @RequestParam("band")String bandId,
                              Model model){


        Album album = albumRepo.findOne(albumId);
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
        return "redirect:/album/" + albumId;
    }
    @RequestMapping(value = "/album/{albumId}/delete", method = RequestMethod.POST)
    public String deleteAlbum(@PathVariable("albumId")long albumId){
        Album album = albumRepo.findOne(albumId);
        albumRepo.delete(album);
        return "redirect:/album/";
    }

    @RequestMapping(value = "/song/{songId}/edit", method = RequestMethod.POST)
    public String createSong(@PathVariable("songId")long songId,
                             @RequestParam("title")String title,
                             @RequestParam("genre")String genre,
                             @RequestParam("releaseDate")Date releaseDate,
                             @RequestParam("band")String bandId,
                             @RequestParam("album")String albumId,
                             Model model, HttpServletRequest request){

        String referer = request.getHeader("Referer");
        Iterable<Band> bands = bandRepo.findAll();
        model.addAttribute("bands", bands);

        Iterable<Album> albums = albumRepo.findAll();
        model.addAttribute("albums", albums);

        Song song = songRepo.findOne(songId);
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
        return "redirect:" + referer;
    }
    @RequestMapping(value = "/song/{songId}/delete", method = RequestMethod.POST)
    public String deleteSong(@PathVariable("songId")long songId){
        Song song = songRepo.findOne(songId);
        songRepo.delete(song);
        return "redirect:/album/";
    }

}
