package com.yuen.rank.model.rankimpl;

import com.mmorpg.qx.module.rank.model.value.impl.PlayerLevelRankingValue;
import com.yuen.rank.model.IRankingObject;
import com.yuen.rank.packet.vo.RankingPlayerLevelItemVo;
import com.yuen.rank.player.PlayerShortInfo;
/**
 * 排行榜子类型
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public class LevelRankingObject extends IRankingObject<Long, LevelRankingObject, PlayerLevelRankingValue, RankingPlayerLevelItemVo> {

    public static LevelRankingObject valueOf(Long id, PlayerLevelRankingValue value) {
        LevelRankingObject object = new LevelRankingObject(id, value);
        return object;
    }

    public LevelRankingObject(Long id, PlayerLevelRankingValue value) {
        super(id, value);
    }

    @Override
    public RankingPlayerLevelItemVo convert() {

        PlayerShortInfo playerShortInfo = new PlayerShortInfo(1L, "玩家1");
        String playerName = playerShortInfo.getName();
        return RankingPlayerLevelItemVo.valueOf(id, playerName, value);
    }
}
