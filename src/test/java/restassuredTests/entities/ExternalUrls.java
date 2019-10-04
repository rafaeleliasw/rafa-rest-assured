package restassuredTests.entities;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
    "spotify"
})
public class ExternalUrls {

    @JsonProperty("spotify")
    private String spotify;

    @JsonProperty("spotify")
    public String getSpotify() {
        return spotify;
    }

    @JsonProperty("spotify")
    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }


}
