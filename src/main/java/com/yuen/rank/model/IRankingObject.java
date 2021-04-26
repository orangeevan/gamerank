package com.yuen.rank.model;

import com.yuen.rank.model.value.IRankingValue;
import com.yuen.rank.packet.vo.IRankingItemVo;

import java.util.Objects;

/**
 * 排行榜子类型
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public abstract class IRankingObject<PK, T extends IRankingObject, V extends IRankingValue, VO extends IRankingItemVo> implements IRank<PK, T, V> {

    // 唯一标志
    final protected PK id;

    // 排行榜数值
    protected V value;

    public IRankingObject(PK id, V value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public PK getId() {
        return id;
    }

    @Override
    public V getValue() {
        return value;
    }

    public abstract VO convert();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IRankingObject)) return false;
        IRankingObject<?, ?, ?, ?> that = (IRankingObject<?, ?, ?, ?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
