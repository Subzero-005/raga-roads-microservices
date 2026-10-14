package com.ragaroads.services;

import com.ragaroads.models.Artist;
import java.util.Optional;

public interface ArtistService {
    Optional<Artist> getArtist(Long artistId);
}
