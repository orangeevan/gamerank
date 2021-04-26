package com.yuen.rank.model.value;


import com.yuen.rank.enums.WaveType;
import com.yuen.rank.packet.vo.IRankingItemVo;

/**
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public interface IRankingValue<PK, T extends IRankingValue, VO extends IRankingItemVo> {
    WaveType compareTo(T o);
}

