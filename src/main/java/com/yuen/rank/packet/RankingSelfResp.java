package com.yuen.rank.packet;


import com.yuen.rank.entity.RankKey;
import com.yuen.rank.packet.vo.RankingPlayerLevelItemVo;

/**
 * @author: yuanchengyan
 * @description:不能和排行列表一起请求（会出现排序不一致的可能性）
 * @since 19:04 2021/4/7
 */
public class RankingSelfResp {
    private RankKey key;
    /**
     * 所有排行榜类型列表往下加，虽然违反开闭原则，但减少协议数量
     */
    private RankingPlayerLevelItemVo playerLevelItemVo;

    public static RankingSelfResp valueOf() {
        RankingSelfResp resp = new RankingSelfResp();
        return resp;
    }

    public RankKey getKey() {
        return key;
    }

    public void setKey(RankKey key) {
        this.key = key;
    }

    public RankingPlayerLevelItemVo getPlayerLevelItemVo() {
        return playerLevelItemVo;
    }

    public void setPlayerLevelItemVo(RankingPlayerLevelItemVo playerLevelItemVo) {
        this.playerLevelItemVo = playerLevelItemVo;
    }
}

