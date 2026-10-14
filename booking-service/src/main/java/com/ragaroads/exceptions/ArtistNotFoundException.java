package com.ragaroads.exceptions;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(Long artistId) {
        super("Artist not found with id: " + artistId);
    }
}
