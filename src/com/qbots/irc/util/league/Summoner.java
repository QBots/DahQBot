package com.qbots.irc.util.league;


public class Summoner {
    String name;
    int wins,losses,assists,kills,deaths,pentas;
    long id;

    public Summoner(String name, long id, int wins, int losses, int assists, int kills, int deaths, int pentas) {
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

    public long getId() {
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

    public String toString() {
        return "Name: "+getName()+"\n"+
                "Total Wins: "+getWins()+"\n"+
                "Total losses: "+getLosses()+"\n"+
                "Total Kills: "+getKills()+"\n"+
                "Total Assits: "+getAssists();
    }

}
