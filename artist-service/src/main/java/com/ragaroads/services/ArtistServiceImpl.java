package com.ragaroads.services;

import com.ragaroads.models.Artist;
import com.ragaroads.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Optional<Artist> getArtist(Long artistId) {
        return artistRepository.findById(artistId);
    }
}
