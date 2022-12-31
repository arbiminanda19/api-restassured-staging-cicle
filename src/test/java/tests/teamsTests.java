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
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;

public class teamsTests {

    String token = "";
    String teamId = "";
    String teamName = "";
    String companyId = "";

    dataTeams dataTeams = new dataTeams();
    v1 baseUrl = new v1();
    accessFile accessFile = new accessFile();
    companyHelper companyHelper = new companyHelper();
    String file_token = "src/test/resources/data/token.txt";

    @BeforeClass
    public void beforeClass() {
        companyId = companyHelper.getNewCompanyId();
    }

    @BeforeMethod
    public void beforeTest() {
        RestAssured.baseURI = baseUrl.getStagingCicle();
        token = accessFile.readFromFile(file_token);
    }

    @AfterClass
    public void afterTest() {
        companyHelper.deleteCompany(companyId);
    }

    @Test(priority = 1)
    public void createTeam() {

        JSONObject payload = dataTeams.createTeam();
        teamName = (String) payload.get("name");

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(payload.toJSONString())
                .post("/teams?companyId=" + companyId)
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        JsonPath responseParsed = response.jsonPath();
        Assert.assertEquals(teamName, responseParsed.getString("newTeam.name"));
        teamId = responseParsed.getString("newTeam._id");
    }

}
