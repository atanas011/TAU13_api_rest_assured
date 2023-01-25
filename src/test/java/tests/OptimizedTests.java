package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OptimizedTests {

    private static RequestSpecification reqSpec;
    private static ResponseSpecification resSpec;

    @BeforeClass
    public static void createReqSpec() {
        reqSpec = new RequestSpecBuilder().
            setBaseUri("http://api.zippopotam.us").
            build();
    }

    @BeforeClass
    public static void createResSpec() {
        resSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            build();
    }

    @Test
    public void checkResBody_withReqSpec() {
        given().
            spec(reqSpec).
        when().
            get("us/90210").
        then().
            assertThat().
            statusCode(200);
    }

    @Test
    public void checkResBody_withResSpec() {
        given().
            spec(reqSpec).
        when().
            get("http://zippopotam.us/us/90210").
        then().
            spec(resSpec).
        and().
            assertThat().
            body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void extractPlaceNameFromResBody() {
        String place =
            given().
                spec(reqSpec).
            when().
                get("http://zippopotam.us/us/90210").
            then().
                extract().
                path("places[0].'place name'");

        Assert.assertEquals("Beverly Hills", place);
    }
}