package com.yuen.rank.entity;

/**
 * 排行榜子类型
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public interface IEntity<K> {

    RankKey getId();

    boolean serialize();

    boolean deserialize();
}

