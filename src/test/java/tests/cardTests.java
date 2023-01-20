package tests;

import baseUrl.v1;
import data.dataTeams;
import helper.accessFile;
import helper.companyHelper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class cardTests {

    String token = "";
    String cardId = "";
    String teamName = "";

    dataTeams dataTeams = new dataTeams();
    v1 baseUrl = new v1();
    accessFile accessFile = new accessFile();
    String file_token = "src/test/resources/data/token.txt";

    @BeforeMethod
    public void beforeTest() {
        RestAssured.baseURI = baseUrl.getStagingCicle();
        token = accessFile.readFromFile(file_token);
    }

    @Test(priority = 1)
    public void createAttachment() {

        Response response = given()
                .when()
                .header("Content-Type", "multipart/form-data")
                .header("Authorization", token)
                .multiPart(new File("src/test/resources/attachments.png"))
                .post("/cards/63c8fbafd466fda200aa2bc6/attachments")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        JsonPath responseParsed = response.jsonPath();
        Assert.assertEquals("Upload attachments is success", responseParsed.getString("message"));
        cardId = responseParsed.getString("card._id");
    }

}
