package restassuredTests.restful.booker;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kohsuke.rngom.parse.host.Base;
import restassuredTests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.openqa.grid.common.RegistrationRequest.build;

/**
 * @author Rafael Elias
 * Request and Response Specification
 */

public class Chapter4ResponseRequestSpecification {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com/booking/").
                build();
    }

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                log(LogDetail.ALL).
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    @Test
    public void requestBookingIdWithRequestSpec() {

        given().
                spec(requestSpec).
                when().
                get("1").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void requestBookingIdWithRequestSpecAndResponseSpec() {

        given().
                spec(requestSpec).
                when().
                get("1").
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("depositpaid", equalTo(true));
    }

    @Test
    public void extractFirstNameFromResponseBody_assertEqualToEric() {

        String firstName =

                given().
                        spec(requestSpec).
                        when().
                        get("1").
                        then().
                        spec(responseSpec).
                        and().
                        extract().
                        path("firstname");

        Assert.assertEquals("Eric", firstName);
    }
}
