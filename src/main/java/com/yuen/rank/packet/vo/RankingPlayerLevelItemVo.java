package com.yuen.rank.packet.vo;

import com.mmorpg.qx.module.rank.model.value.impl.PlayerLevelRankingValue;

/**
 * @author: yuanchengyan
 * @description:
 * @since 19:05 2021/4/7
 */
public class RankingPlayerLevelItemVo implements IRankingItemVo {

    private long playerId;

    private String playerName;

    private PlayerLevelRankingValue val;

    public static RankingPlayerLevelItemVo valueOf(long playerId, String playerName, PlayerLevelRankingValue val) {
        RankingPlayerLevelItemVo vo = new RankingPlayerLevelItemVo();
        vo.playerId = playerId;
        vo.playerName = playerName;
        vo.val = val;
        return vo;
    }
}

