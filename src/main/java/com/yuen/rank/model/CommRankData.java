package com.yuen.rank.model;


import com.alibaba.fastjson.JSON;
import com.yuen.rank.entity.RankEntity;
import com.yuen.rank.entity.RankKey;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * 排行榜子类型
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
public class CommRankData<PK, T extends IRankingObject> {
    public final static IRankingObject[] RANK_DATA_ZERO = new IRankingObject[]{};
    private final RankKey key;

    private final int rankPeriod;

    private final LinkedList<T> linkedList = new LinkedList<>();

    private final ConcurrentMap<PK, T> commits = new ConcurrentHashMap<>();

    private long lastRankTime;

    /**
     * 用于查询的排行榜（每次刷新排序列表时，重新赋值）
     */
    private volatile List<T> cacheOrderRD = Collections.EMPTY_LIST;

    private transient RankEntity rankEntity;

    /**
     * 实例化
     *
     * @param key  名称
     * @param sync 刷新时间间隔,0表示实时刷新
     */
    public CommRankData(RankKey key, int sync) {
        this.key = key;
        this.rankPeriod = sync;
    }


    public RankEntity toEntity() {
        rankEntity.setJsonData(JSON.toJSONString(RANK_DATA_ZERO));
        return rankEntity;
    }

    /**
     * 当前容量
     */
    public int size() {
        return cacheOrderRD.size();
    }

    public LinkedList<T> getLinkedList() {
        return linkedList;
    }

    public void putCommit(PK pk, T t) {
        commits.put(pk, t);
    }

    public T removeCommits(PK pk) {
        return commits.remove(pk);
    }

    public Object[] getCommitKeys() {
        return commits.keySet().toArray();
    }

    public int getCommitSize() {
        return commits.size();
    }

    public RankKey getKey() {
        return key;
    }

    public int getRankPeriod() {
        return rankPeriod;
    }


    public long getLastRankTime() {
        return lastRankTime;
    }

    public void setLastRankTime(long lastRankTime) {
        this.lastRankTime = lastRankTime;
    }

    public void initData(RankEntity rankEntity) {
        if (rankEntity.getJsonData() == null || rankEntity.getJsonData().isEmpty()) {
            return;
        }
        List<T> tList = JSON.parseArray(rankEntity.getJsonData(), this.key.getType().getTypeClass());
        if (!CollectionUtils.isEmpty(tList)) {
            tList.forEach(e -> linkedList.addLast(e));
        }
        lastRankTime = System.currentTimeMillis();
        this.rankEntity = rankEntity;
    }

    public T getRankObjectInLinkedList(PK pk) {
        LinkedList.Node<T> node = linkedList.getFirst();
        while (!Objects.isNull(node)) {
            if (node.getItem().getId().equals(pk)) {
                break;
            }
            node = linkedList.getNext(node);
        }
        return node.getItem();
    }

    public T getRankingObject(PK pk) {
        List<T> copy = cacheOrderRD;
        for (T t : copy) {
            if (!t.getId().equals(pk)) {
                continue;
            }
            return t;
        }
        return null;
    }

    public List<T> getCacheOrderRD() {
        return cacheOrderRD;
    }

    public void updateOrderRankData() {
        List<T> copy = new ArrayList<>(linkedList.size());
        LinkedList.Node<T> node = linkedList.getFirst();
        while (!Objects.isNull(node) && !linkedList.isTail(node)) {
            copy.add(node.getItem());
            node = linkedList.getNext(node);
        }
        cacheOrderRD = copy;
    }
}
