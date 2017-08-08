package com.tbell.recordstore.Repository;

import com.tbell.recordstore.models.Album;
import com.tbell.recordstore.models.Band;
import com.tbell.recordstore.models.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album,Long> {
    Album findByAlbumTitle(String albumTitle);



    List<Album> findAllByBand(Band band);

    Album findByBand(Band band);
}
