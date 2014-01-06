package com.qbots.irc.util.league;

/**
 * Created by Tyler on 1/6/14.
 */
public class Summoner {
    String name;
    int id,wins,losses,assists,kills,deaths,pentas;

    public Summoner(String name, int id, int wins, int losses, int assists, int kills, int deaths, int pentas) {
        this.name = name;
        this.id = id;
        this.wins = wins;
        this.losses = losses;
        this.assists = assists;
        this.kills = kills;
        this.deaths = deaths;
        this.pentas = pentas;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getAssists() {
        return assists;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getPentas() {
        return pentas;
    }

}
