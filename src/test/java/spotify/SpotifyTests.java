package spotify;

import entities.SearchArtist;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;



/**
 * @author Rafael Elias
 */

    public class SpotifyTests {

    public static String accessToken = "";
    public static String userId = "xpyan8kpzi8nfu9nci6gfc1nq";
    public static String bearer = "Bearer BQDo7Z7YdCpdZNSGwjn1JAarCe7u2LCY1zZj__4iegEz2-sPuxKB5WWjYvWb5wOveNxBzEeYKsPFF5t37qtNtNx6DVZB2jT3bh2o-S5cx5aPt7n9FEv8SoNdZxlv2H9chqIGS9Ix49clChE74s4-gXDeJ73dJFSe1dZXNTswNBfSoNMGVraJ24M-tPQZZCbprD3_kZjyfvyY_j-rhbdnhIrJ7YSTMWQWVELNEDwDJaIsfFrgNFEHiLs7J-3I44AoqvBQtZs_t7tDBdBbIDzFfLa-lp6UiWncHNymdxp8peWmTg";

    @BeforeClass
    public static void authenticationSpotify(){
        String authToken = Token.getEncodedToken("6aa4abdbfa974048883221cf82d67cc8", "71185044846946b68a7b379f67dd561f");
        generateAccessToken(authToken);
    }

    private static void generateAccessToken(String authToken) {
        RestAssured.baseURI = "https://accounts.spotify.com/api";

        Response response = given().
                header("Authorization","Basic "+authToken).
                contentType("application/x-www-form-urlencoded").
                formParam("grant_type","client_credentials").
                log().all().
                when().
                post("token");

        accessToken = response.jsonPath().get("access_token");
    }

    @BeforeClass
    public static void beforeTests(){
        RestAssured.baseURI = "https://api.spotify.com/";
        RestAssured.basePath = "/v1/";
    }

    @Test
    public void getUser(){
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
    public void createPlaylist(){

        String payload = "{\n" +
                "  \"name\": \"Testing List 2\",\n" +
                "  \"public\": 0\n" +
                "}";

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
