package com.yuen.rank.handler.impl;

import com.mmorpg.qx.module.rank.model.value.impl.PlayerLevelRankingValue;
import com.yuen.rank.entity.RankKey;
import com.yuen.rank.enums.RankSubType;
import com.yuen.rank.enums.RankType;
import com.yuen.rank.handler.AbstractRankingHandler;
import com.yuen.rank.manager.RankManager;
import com.yuen.rank.model.CommRankData;
import com.yuen.rank.model.rankimpl.LevelRankingObject;
import com.yuen.rank.packet.RankingListResp;
import com.yuen.rank.packet.RankingSelfResp;
import com.yuen.rank.packet.vo.RankingPlayerLevelItemVo;
import com.yuen.rank.player.Player;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: yuanchengyan
 * @description:
 * @since 20:59 2021/4/7
 */
@Component
public class PlayerLevelRankingHandler extends AbstractRankingHandler<Long, LevelRankingObject, PlayerLevelRankingValue> {

    @Override
    public void sentRankingListResp(Player player, RankKey key) {
        CommRankData commRankData = RankManager.getInstance().getCommRankData(key);
        List<LevelRankingObject> rankingObjects = commRankData.getCacheOrderRD();
        List<RankingPlayerLevelItemVo> vos = rankingObjects.stream().map(obj -> obj.convert()).collect(Collectors.toList());
        RankingListResp resp = RankingListResp.valueOf();
        resp.setKey(commRankData.getKey());
        resp.setPlayerLevelItemVos(vos);
        player.sendPacket(resp);
    }

    @Override
    public void sentRankingSelfResp(Player player, RankKey key) {
        CommRankData commRankData = RankManager.getInstance().getCommRankData(key);
        LevelRankingObject rankingObject = (LevelRankingObject) commRankData.getRankingObject(player.getObjectId());
        if (Objects.isNull(rankingObject)) {
            int level = player.getLevel();
            PlayerLevelRankingValue value = PlayerLevelRankingValue.valueOf(level);
            rankingObject = createEmptyRankingObject(player.getObjectId(), value);
        }
        RankingSelfResp resp = RankingSelfResp.valueOf();
        resp.setKey(commRankData.getKey());
        resp.setPlayerLevelItemVo(rankingObject.convert());
        player.sendPacket(resp);

    }

    @Override
    public LevelRankingObject createEmptyRankingObject(Long pk, PlayerLevelRankingValue rankingValue) {
        return LevelRankingObject.valueOf(pk, rankingValue);
    }

}

