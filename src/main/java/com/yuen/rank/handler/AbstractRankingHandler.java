package com.yuen.rank.handler;


import com.yuen.rank.entity.RankKey;
import com.yuen.rank.model.IRankingObject;
import com.yuen.rank.model.value.IRankingValue;
import com.yuen.rank.player.Player;

/**
 * @author: yuanchengyan
 * @description:
 * @since 20:58 2021/4/7
 */
public abstract class AbstractRankingHandler<PK, T extends IRankingObject, V extends IRankingValue> {

    public abstract void sentRankingListResp(Player player, RankKey key);

    public abstract void sentRankingSelfResp(Player player, RankKey key);

    public abstract T createEmptyRankingObject(PK pk, V v);

}

