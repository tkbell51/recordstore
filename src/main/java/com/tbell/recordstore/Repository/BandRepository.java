package com.tbell.recordstore.Repository;

import com.tbell.recordstore.models.Album;
import com.tbell.recordstore.models.Band;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandRepository extends CrudRepository<Band, Long>{

    Band findByBandTitleIgnoreCaseContaining (String bandTitle);


    Band findByAlbums(Album album);


}
