package restassuredTests.coop;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import restassured.spotify.Token;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class theCoopApiTests {

    public static String accessToken;
    public static String clientId = "RafaTest";
    public static String clientSecret = "bedc2730b436d3414c63a9476f62373a";
    public static String userId = "461";

    @BeforeClass
    public static void authenticationCoop() {
        String authToken = Token.getEncodedToken(clientId, clientSecret);
        generateAccessToken(authToken);
    }

    private static void generateAccessToken(String authToken) {
        RestAssured.baseURI = "http://coop.apps.symfonycasts.com/";

        Response response =
                given().
                        formParam("client_id", clientId).
                        formParam("client_secret", clientSecret).
                        formParam("grant_type", "client_credentials").
                        log().all().
                when().
                        post("token");

        accessToken = response.jsonPath().get("access_token");
    }

    @BeforeClass
    public static void beforeTests() {
        RestAssured.baseURI = "http://coop.apps.symfonycasts.com";
        RestAssured.basePath = "/api/";
    }

    @Test
    public void getUser() {

        given().
                auth().oauth2(this.accessToken).
        when().
                get("me").
        then().
                statusCode(200).
                assertThat().
                        body("id", equalTo(userId)).
                        body("email", equalTo("rafaeleliasw@gmail.com")).
                        body("firstName", equalTo("Rafael")).
                        body("lastName", equalTo("Elias"));
    }

    @Test
    public void unlockTheBarn(){
        given().
                auth().oauth2(this.accessToken).
                pathParam("userId", userId).
        when().
                post("{userId}/barn-unlock").
        then().
                statusCode(200).
                assertThat().
                    body("action", equalTo("barn-unlock")).
                    body("success", equalTo(true)).
                    body("message", equalTo("You just unlocked your barn! Watch out for strangers!")).
                    body("data", equalTo(null));
    }

    @Test
    public void getNumberOfEggsCollectedToday_shouldReturn401(){
        given().
                auth().oauth2(this.accessToken).
                pathParam("userId", userId).
                when().
                post("{userId}/eggs-count").
                then().
                statusCode(401).
                assertThat().
                body("error", equalTo("insufficient_scope")).
                body("error_description", equalTo("The request requires higher privileges than provided by the access token"));
    }
}
