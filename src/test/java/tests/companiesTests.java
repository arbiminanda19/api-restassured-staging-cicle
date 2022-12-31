package tests;

import baseUrl.v1;
import data.dataCompanies;
import helper.accessFile;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class companiesTests {

    String token = "";
    String companyId = "";
    String companyName = "";

    dataCompanies dataCompanies = new dataCompanies();
    v1 baseUrl = new v1();
    accessFile accessFile = new accessFile();
    String file_token = "src/test/resources/data/token.txt";

    @BeforeMethod
    public void beforeTest() {
        RestAssured.baseURI = baseUrl.getStagingCicle();
        token = accessFile.readFromFile(file_token);
    }

    @Test(priority = 1)
    public void createCompany() {

        JSONObject payload = dataCompanies.createCompany();

        companyName = (String) payload.get("name");

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(payload.toJSONString())
                .post("/companies")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        JsonPath responseParsed = response.jsonPath();
        Assert.assertEquals(responseParsed.getString("message"), "Successfully create company user");
        companyId = responseParsed.getString("newCompany._id");

    }

    @Test(priority = 2)
    public void getAllCompanies(){

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .get("/companies")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        Boolean foundCompany = false;
        JsonPath responseParsed = response.jsonPath();
        Integer i = 0;
        while (!foundCompany) {
            foundCompany = (responseParsed.getString("companies[" + i + "]._id")).equals(companyId);
            i++;
        }

    }

    @Test(priority = 3)
    public void updateCompany() {

        JSONObject payload = dataCompanies.updateCompany();

        companyName = (String) payload.get("name");

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(payload.toJSONString())
                .patch("/companies/" + companyId)
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        JsonPath responseParsed = response.jsonPath();
        Assert.assertEquals(responseParsed.getString("company.name"), companyName);

    }

    @Test(priority = 4)
    public void deleteCompany() {

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .delete("/companies/" + companyId)
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        String responseString = response.toString();
        Assert.assertFalse(responseString.contains(companyId));

    }

}
