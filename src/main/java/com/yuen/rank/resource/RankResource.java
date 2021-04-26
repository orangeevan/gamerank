package com.yuen.rank.resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuen.rank.enums.RankSubType;
import com.yuen.rank.enums.RankType;
import com.yuen.rank.model.value.IRankingValue;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author: yuanchengyan
 * @description:
 * @since 11:00 2021/4/7
 */
public class RankResource {
    private static TypeReference<List<RankSubType>> LIST_RANK_SUB_TYPE = new TypeReference<List<RankSubType>>() {
    };
    private RankType rankType;
    private String subTypes;
    private List<RankSubType> rankSubTypes;
    private String minValue;
    private IRankingValue minRankValue;
    private boolean fallOut;
    private int max;

    public RankType getRankType() {
        return rankType;
    }

    public void setRankType(RankType rankType) {
        this.rankType = rankType;
    }

    public String getSubTypes() {
        return subTypes;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;

    }

    public void setSubTypes(String subTypes) {

        this.subTypes = subTypes;
    }

    public boolean isFallOut() {
        return fallOut;
    }

    public void setFallOut(boolean fallOut) {
        this.fallOut = fallOut;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public IRankingValue getMinRankValue() {
        if (Objects.isNull(minRankValue)) {
            synchronized (this) {
                if (Objects.isNull(minRankValue)) {
                    minRankValue = JSON.parseObject(minValue, rankType.getValueClass());
                }
            }
        }
        return minRankValue;
    }

    public void setMinRankValue(IRankingValue minRankValue) {
        this.minRankValue = minRankValue;
    }

    public List<RankSubType> getRankSubTypes() {
        if (Objects.isNull(rankSubTypes)) {
            synchronized (this) {
                if (Objects.isNull(rankSubTypes)) {
                    rankSubTypes = JSON.parseObject(subTypes, LIST_RANK_SUB_TYPE);
                }
            }
        }
        return rankSubTypes;
    }

    public void setRankSubTypes(List<RankSubType> rankSubTypes) {
        this.rankSubTypes = rankSubTypes;
    }
}

