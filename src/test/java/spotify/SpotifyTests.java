package spotify;

import entities.SearchArtist;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @author Rafael Elias
 */

public class SpotifyTests {

    public static String accessToken = "";
    public static String userId = "xpyan8kpzi8nfu9nci6gfc1nq";
    public static String bearer = "Bearer BQAYcZLsyMv_GXIz1attJ9LvxezgnqNLlWnbXieM1IAM-UP-SD3AOqbQRVDEthfjN3rsfOI7EYSPUeOp4VhMOws65zvgzgIDYY8pgoy7klEXKgf70s4A4Oox4eIWY74sTFe_wW5li40sDDGXM94V2xaL9xjXWPZvxXx3xPscQqXlfWm0v2oL3xs1gtTW4D9zhxJTUc_zIgCdXyzEQtVNBuwQPQLCzwMMr-HlDIl3QKOeTiIrw3TnL47dr9LjJv2_kH4l5whzrk-b2D8BQ1GKm3qsv_kZbviaw5-dHfECgYlFTg";

    public String generateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));

    }

    @BeforeClass
    public static void authenticationSpotify() {
        String authToken = Token.getEncodedToken("6aa4abdbfa974048883221cf82d67cc8", "71185044846946b68a7b379f67dd561f");
        generateAccessToken(authToken);
    }

    private static void generateAccessToken(String authToken) {
        RestAssured.baseURI = "https://accounts.spotify.com/api";

        Response response = given().
                header("Authorization", "Basic " + authToken).
                contentType("application/x-www-form-urlencoded").
                formParam("grant_type", "client_credentials").
                log().all().
                when().
                post("token");

        accessToken = response.jsonPath().get("access_token");
    }

    @BeforeClass
    public static void beforeTests() {
        RestAssured.baseURI = "https://api.spotify.com/";
        RestAssured.basePath = "/v1/";
    }

    @Test
    public void getUser() {
        given().
                auth().oauth2(this.accessToken).
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
                header("Authorization", bearer).
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
                        auth().oauth2(this.accessToken).
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
