package com.yuen.rank.packet;


import com.yuen.rank.entity.RankKey;

/**
 * @author: yuanchengyan
 * @description:
 * @since 19:04 2021/4/7
 */

public class RankingListReq {

    private RankKey key;


    public RankKey getKey() {
        return key;
    }

    public void setKey(RankKey key) {
        this.key = key;
    }

}
