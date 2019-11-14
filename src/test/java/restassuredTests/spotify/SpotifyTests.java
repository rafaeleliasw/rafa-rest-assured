package restassuredTests.spotify;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import restassuredTests.entities.SearchArtist;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static io.restassured.RestAssured.given;

import static restassuredTests.TestUtils.generateStringFromResource;
import static restassuredTests.TestUtils.getSpotifyAccessToken;


/**
 * @author Rafael Elias
 */

public class SpotifyTests {

    public static final String clientId = "6aa4abdbfa974048883221cf82d67cc8";
    public static final String redirect_uri = "http://mysite.com/callback/";
    public static final String scope = "user-read-private user-read-email";
    public static final String response_type = "token";
    public static final int  state = 123;
    public static final String userId = "xpyan8kpzi8nfu9nci6gfc1nq";
    public static String bearer = "";
    public static final String username = "codingclubrosario2019@gmail.com";
    public static final String password = "C@ding.club.2k19";

    @BeforeClass
    public static void authenticationSpotify() throws InterruptedException, UnsupportedEncodingException {
        bearer = getSpotifyAccessToken(username, password, clientId, redirect_uri, scope, response_type, state);
    }


    @BeforeClass
    public static void beforeTests() {
        RestAssured.baseURI = "https://api.spotify.com/";
        RestAssured.basePath = "/v1/";
    }


    @Test
    public void getUser() {
        given().
                headers("Authorization", "Bearer "+bearer, "Accept", "application/xml").
                accept(ContentType.JSON).
                pathParam("userId", this.userId).
                log().all().
                when().
                get("users/{userId}").
                then().
                log().all().
                statusCode(200).
                body("display_name", CoreMatchers.equalTo("Coding Club Rosario"));

    }

    @Test
    public void createPlaylist() throws IOException {

        String payload = generateStringFromResource("./Files/CreatePlayList.json");

        given().
                header("Authorization", "Bearer "+bearer).
                accept(ContentType.JSON).
                pathParam("userId", this.userId).
                body(payload).
                when().
                post("users/{userId}/playlists").
                then().
                assertThat().
                statusCode(201);
    }

    @Test
    public void searchShouldReturnBabasonicos() {

        SearchArtist searchArtist =

                given().
                        header("Authorization", "Bearer "+bearer).
                        accept(ContentType.JSON).
                        queryParam("q", "Babasonicos").
                        queryParam("type", "artist").
                        log().all().
                        when().
                        get("search").
                        as(SearchArtist.class);

        Assert.assertEquals(
                "Babasónicos"
                , searchArtist.getArtist().getItems().get(0).getName());
    }
}
