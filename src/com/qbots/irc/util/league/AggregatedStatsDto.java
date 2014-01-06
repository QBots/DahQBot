package com.qbots.irc.util.league;

/**
 * Created by Tyler on 1/6/14.
 */
public class AggregatedStatsDto {

    private int totalNeutralMinionsKilled;
    private int totalMinionKills;
    private int totalChampionKills;
    private int totalAssists;
    private int totalTurretsKilled;

    public int getTotalNeutralMinionsKilled() {
        return totalNeutralMinionsKilled;
    }

    public int getTotalMinionKills() {
        return totalMinionKills;
    }

    public int getTotalChampionKills() {
        return totalChampionKills;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public int getTotalTurretsKilled() {
        return totalTurretsKilled;
    }
}
