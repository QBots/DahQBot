package com.qbots.irc.util.league;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.google.gson.Gson;


public class LeagueApi {

    private final static String KEY = "6490a24c-bccc-4a05-8a0e-b3752aa35da2";

    public Summoner getSummoner(String region,String name) throws IOException {
        InputStream is = new URL("https://prod.api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.2/summoner/by-name/" + name + "?api_key=" + KEY).openStream();
        //InputStream is = new URL("https://prod.api.pvp.net/api/lol/na/v1.2/summoner/by-name/Players?api_key=6490a24c-bccc-4a05-8a0e-b3752aa35da2").openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = JsonObject.readFrom(jsonText);
            return getOtherInfo(json.get("name").asString(),json.get("id").asInt(),region);
        } finally {
            is.close();
        }
    }

    public PlayerStatsSummaryDto getNormalStats() {
        for(PlayerStatsSummaryDto stat : playerStatsSummaryListDto.getPlayerStatSummaries()) {
            if(stat.getPlayerStatSummaryType().equals("Unranked")) {
                return stat;
            }
        }
        return null;
    }
    private PlayerStatsSummaryListDto playerStatsSummaryListDto;

    public Summoner getOtherInfo(String name,int id,String region) throws IOException {

        InputStream is = new URL("https://prod.api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.2/stats/by-summoner/" + id + "/summary?season=SEASON3&api_key=" + KEY).openStream();
        //InputStream is = new URL("http://prod.api.pvp.net/api/lol/na/v1.2/stats/by-summoner/44869168/summary?season=SEASON3&api_key=6490a24c-bccc-4a05-8a0e-b3752aa35da2").openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JsonObject json = JsonObject.readFrom(jsonText);
            playerStatsSummaryListDto = new Gson().fromJson(json.toString(), PlayerStatsSummaryListDto.class);
            PlayerStatsSummaryDto player = getNormalStats();
            //public Summoner(String name, int id, int wins, int losses, int assists, int kills, int deaths, int pentas) {
            return new Summoner(name,playerStatsSummaryListDto.getSummonerId(),player.getWins(),player.getLosses(),player.getAggregatedStats().getTotalAssists(),player.getAggregatedStats().getTotalChampionKills(),0,0);
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
