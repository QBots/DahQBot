package com.qbots.irc.util.league;


public class PlayerStatsSummaryDto {
    private AggregatedStatsDto aggregatedStats;
    private int losses;
    private long modifyDate;
    private String playerStatSummaryType;
    private int wins;

    public AggregatedStatsDto getAggregatedStats() {
        return aggregatedStats;
    }

    public int getLosses() {
        return losses;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public String getPlayerStatSummaryType() {
        return playerStatSummaryType;
    }

    public int getWins() {
        return wins;
    }
}
