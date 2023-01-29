package User;

import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests {

    private static User user;
    public static Faker faker;
    public static RequestSpecification request;

    @BeforeAll
    public static void setup(){
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
    void setRequest(){
        request = given()
                .header("api=key", "special-key")
                .contentType(ContentType.JSON);

    }

    @Test
    public void CreateNewUser_WithValidData_ReturnOk() {
        request
                .body(user)
                .when()
                .post("/user")
                .then()
                .assertThat().statusCode(200).and()
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", isA(String.class))
                .body("size()", equalTo(3));


    }

    @Test
    public void GetLogin_ValidUser_ReturnOk() {
        request
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .when()
                .get("/user/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and().time(lessThan(2000L))
                .and().body
    }

}
