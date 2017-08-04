package com.tbell.recordstore.Repository;

import com.tbell.recordstore.models.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album,Long> {
}
