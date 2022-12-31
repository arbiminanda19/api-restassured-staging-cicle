package data;

import org.json.simple.JSONObject;

import java.util.Random;

public class dataCompanies {

    Random rand = new Random();

    public JSONObject createCompany() {

        JSONObject payload = new JSONObject();
        String companyName = "Company" + rand.nextInt(1000);
        payload.put("name", companyName);
        payload.put("desc", "description");

        return payload;

    }

    public JSONObject updateCompany() {

        JSONObject payload = new JSONObject();
        String companyName = "Company" + rand.nextInt(1000);
        payload.put("name", companyName);
        payload.put("desc", "description");
        payload.put("workloadCapacity", rand.nextInt(10));

        return payload;

    }

}
