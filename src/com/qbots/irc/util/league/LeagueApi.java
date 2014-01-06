package com.qbots.irc.util.league;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;


public class LeagueApi {

    private final static String KEY = "6490a24c-bccc-4a05-8a0e-b3752aa35da2";

    public static Summoner getSummoner(String region,String name) throws IOException {
        InputStream is = new URL("https://prod.api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.2/summoner/by-name/" + name + "?api_key=" + KEY).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = JsonObject.readFrom(jsonText);
            return getOtherInfo(json.get("name").asString(),json.get("id").asInt(),region);
        } finally {
            is.close();
        }
    }

    private static Summoner getOtherInfo(String name,int id,String region) throws IOException {

        InputStream is = new URL("https://prod.api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.2/stats/by-name/" + name + "/summary?season=SEASON3&api_key=" + KEY).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = JsonObject.readFrom(jsonText);
            return new Summoner(name,id,json.get("wins").asInt(),json.get("losses").asInt(),json.get("totalAssists").asInt(),json.get("totalChampionKills").asInt(),json.get("averageNumDeaths").asInt(),json.get("totalPentaKills").asInt());
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
