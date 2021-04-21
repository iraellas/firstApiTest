import io.restassured.response.Response;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class FirstAPiTest {
    String getUser200SchemaPath = "src/test/resources/schemas/getUserSchemas200.json";
    String getListUser200SchemaPath = "src/test/resources/schemas/getListUserSchemas200.json";
    String getNonFoundUser404SchemaPath = "src/test/resources/schemas/getNotFoundUserSchemas404.json";
    String getCreateUser401SchemaPath = "src/test/resources/schemas/getCreateNewUserSchema.json";
    String getCreateNewUserDataPath = "src/test/resources/schemas/getCreateNewUserData.json";

    @Test
    public void getSingleUserTest() throws IOException {

        String jsonSchema = FileUtils.getJsonFromFile(getUser200SchemaPath);
        Response response = when().get("https://reqres.in/api/users/2");
        response.then().statusCode(200).assertThat().body(matchesJsonSchema(jsonSchema));

    }

    @Test
    public void getListUserTest() throws IOException {

        String jsonSchema = FileUtils.getJsonFromFile((getListUser200SchemaPath));
        Response response = when().get("https://reqres.in/api/users?page=2");
        response.then().statusCode(200).assertThat().body(matchesJsonSchema(jsonSchema));

    }

    @Test
    public void getNonFoundUserTest() throws IOException {

        String jsonSchema = FileUtils.getJsonFromFile((getNonFoundUser404SchemaPath));
        Response response = when().get("https://reqres.in/api/users/23");
        response.then().statusCode(404).assertThat().body(matchesJsonSchema(jsonSchema));

    }

    @Test
    public void postNewUserTest() throws IOException {

        String jsonSchema = FileUtils.getJsonFromFile((getCreateUser401SchemaPath));
        String jsonData = FileUtils.getJsonFromFile((getCreateNewUserDataPath));
        Response response = given()
                .body(jsonData)
                .contentType("application/json\r\n")
                .when()
                .post("https://reqres.in/api/users");
        response.then().statusCode(201).assertThat().body(matchesJsonSchema(jsonSchema));

    }
}
