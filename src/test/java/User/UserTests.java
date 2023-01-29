package User;

import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {

    private static User user;
    public static Faker faker;
    public static RequestSpecification request;

    @BeforeAll
    public void setup(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        faker = new Faker();

        user = new User(faker.name().username(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.internet().safeEmailAddress(),
        faker.internet().password(8,10),
        faker.phoneNumber().toString());

    }

    @BeforeEach
    void setRequest() {
        request = given()
                .header("api-key", "special-key")
                .contentType(ContentType.JSON);

    }

    @Test
    public void CreateNewUser_WithValidData_ReturnOk() {

    }

}
