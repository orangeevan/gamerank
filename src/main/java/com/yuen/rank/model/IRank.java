package com.yuen.rank.model;


import com.yuen.rank.model.value.IRankingValue;

/**
 * 排行榜子类型
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public interface IRank<PK, T extends IRank, V extends IRankingValue>{

    /**
     * 获取唯一ID
     */
    PK getId();

    /**
     * 获取数值
     */
    V getValue();

}
