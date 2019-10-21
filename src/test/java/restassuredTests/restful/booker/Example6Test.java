package restassuredTests.restful.booker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.Assert;
import restassuredTests.BaseTest;
import io.restassured.response.Response;
import io.restassured.parsing.Parser;

import java.io.IOException;

import static io.restassured.RestAssured.*;

/**
 * @author Cintia Hetzer
 *
 * In this class I'm checking create a new booking
 */

public class Example6Test extends BaseTest {

    @BeforeClass
    public static void beforeTests() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    }

    @Test
    public void checkCreateNewBooking_OK() throws IOException {
        String payloadCreate = generateStringFromResource("./Files/BookingSuccessCreateData.json");
        RestAssured.defaultParser = Parser.JSON;
            Response response =
                    given().
                            header("Content-type", "application/json").
                            body(payloadCreate).
                            when().post("/booking").
                            then().contentType(ContentType.JSON).extract().response();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertNotNull(response.jsonPath().getString("bookingid"));
        }
}
