package helper;

import baseUrl.v1;
import data.dataCompanies;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class companyHelper {

    String token = "";
    String companyId = "";
    data.dataCompanies dataCompanies = new dataCompanies();
    v1 baseUrl = new v1();
    accessFile accessFile = new accessFile();
    String file_token = "src/test/resources/data/token.txt";

    public void before() {
        RestAssured.baseURI = baseUrl.getStagingCicle();
        token = accessFile.readFromFile(file_token);
    }

    public String getNewCompanyId() {

        before();
        JSONObject payload = dataCompanies.createCompany();
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
        return companyId;

    }

    public void deleteCompany(String companyId) {

        before();
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
