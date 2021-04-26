package com.yuen.rank.listener;

import com.yuen.rank.entity.RankKey;
import com.yuen.rank.enums.RankSubType;
import com.yuen.rank.enums.RankType;
import com.yuen.rank.manager.RankManager;
import com.yuen.rank.model.IRankingObject;
import com.yuen.rank.resource.RankResource;

import java.util.List;

/**
 * @author: yuanchengyan
 * @description:
 * @since 20:59 2021/4/7
 */
public abstract class AbstractRankingListener {

    public abstract RankType rankType();

    public void commit(RankType type, IRankingObject rankingObject) {
        RankResource rankResource = RankManager.getInstance().getRankResource(type);
        List<RankSubType> subTypes = rankResource.getRankSubTypes();
        for (RankSubType subType : subTypes) {
            RankManager.getInstance().commit(RankKey.valueOf(type, subType), rankingObject);
        }
    }
}

