package restassuredTests.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "artists"
})
public class SearchArtist {

    private Artist artist = null;

    public Artist getArtist() {
        return artist;
    }

    public void setArtists(Artist artist) {
        this.artist = artist;
    }
}
