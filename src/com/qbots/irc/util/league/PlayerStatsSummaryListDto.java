package com.qbots.irc.util.league;

import java.util.Arrays;

public class PlayerStatsSummaryListDto {

    private PlayerStatsSummaryDto[] playerStatSummaries;
    private long summonerId;

    public PlayerStatsSummaryDto[] getPlayerStatSummaries() {
        return playerStatSummaries;
    }

    public long getSummonerId() {
        return summonerId;
    }
}