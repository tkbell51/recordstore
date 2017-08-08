package com.tbell.recordstore.Repository;

import com.tbell.recordstore.models.Album;
import com.tbell.recordstore.models.Band;
import com.tbell.recordstore.models.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    Song findBySongTitle (String songTitle);

    Iterable<Song> findAllByBand(Band band);

    Iterable<Song> findAllByAlbum(long albumId);

    Iterable<Song> findAllByAlbum(Album album);
}
