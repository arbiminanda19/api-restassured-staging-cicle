package data;

import org.json.simple.JSONObject;

import java.util.Random;

public class dataTeams {

    Random rand = new Random();

    public JSONObject createTeam() {

        JSONObject payload = new JSONObject();
        String teamName = "team" + rand.nextInt(1000);
        payload.put("name", teamName);
        payload.put("desc", "description");
        payload.put("type", "team");

        return payload;

    }

}
