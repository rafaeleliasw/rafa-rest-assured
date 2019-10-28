package restassuredTests.spotify;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import restassuredTests.BaseTest;
import restassuredTests.entities.SearchArtist;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import restassured.spotify.*;
import static io.restassured.RestAssured.given;


/**
 * @author Rafael Elias
 */

public class SpotifyTests extends BaseTest {

    public static final String clientId = "";
    public static final String redirect_uri = "";
    public static final String scope = "u";
    public static final String response_type = "";
    public static final int  state = 123;
    public static final String userId = "";
    public static String bearer = "";
    public static final String username = "";
    public static final String password = "";

    @BeforeClass
    public static void authenticationSpotify() throws InterruptedException, UnsupportedEncodingException {
        bearer = Token.getSpotifyAccessToken(username, password, clientId, redirect_uri, scope, response_type, state);
    }


    @BeforeClass
    public static void beforeTests() {
        RestAssured.baseURI = "https://api.spotify.com/";
        RestAssured.basePath = "/v1/";
    }


    @Test
    public void getUser() {
        given().
                header("Authorization", "Bearer "+bearer).
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
