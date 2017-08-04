package com.tbell.recordstore.Repository;

import com.tbell.recordstore.models.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
