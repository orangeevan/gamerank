package com.yuen.rank.listener;

import com.mmorpg.qx.module.rank.model.value.impl.PlayerLevelRankingValue;
import com.yuen.rank.enums.RankType;
import com.yuen.rank.event.PlayerLevelUpEvent;
import com.yuen.rank.model.rankimpl.LevelRankingObject;
import com.yuen.rank.player.Player;
import org.springframework.stereotype.Component;

/**
 * @author: yuanchengyan
 * @description:
 * @since 20:59 2021/4/7
 */
@Component
public class PlayerLevelRankingListener extends AbstractRankingListener {
    @Override
    public RankType rankType() {
        return RankType.PlayerLevel;
    }


    public void onPlayerLevelUp(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        int level = player.getLevel();
        PlayerLevelRankingValue value = PlayerLevelRankingValue.valueOf(level);
        LevelRankingObject obj = LevelRankingObject.valueOf(player.getObjectId(), value);
        commit(rankType(), obj);
    }
}

