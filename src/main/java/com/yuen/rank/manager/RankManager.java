package com.yuen.rank.manager;


import com.yuen.rank.entity.RankEntity;
import com.yuen.rank.entity.RankKey;
import com.yuen.rank.enums.RankSubType;
import com.yuen.rank.enums.RankType;
import com.yuen.rank.enums.WaveType;
import com.yuen.rank.model.CommRankData;
import com.yuen.rank.model.IRankingObject;
import com.yuen.rank.model.LinkedList;
import com.yuen.rank.resource.RankResource;
import com.yuen.rank.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 排行榜子类型
 *
 * @author: yuanchengyan
 * @description:
 * @since 10:23 2021/4/7
 */
@Component
public class RankManager {

    /**
     * 排行榜数据容器
     */
    private final ConcurrentMap<RankKey, CommRankData> rankDatas = new ConcurrentHashMap<>();

    private static RankManager self;

    private AtomicBoolean startRank = new AtomicBoolean(false);


    @Autowired
    private RankService rankService;

    @PostConstruct
    private void init() {
        if (startRank.compareAndSet(false, true)) {
            initRankDatas();
            rankService.init();
            rankService.startRank();
        }
        self = this;
    }

    /**
     * 获取排行榜
     *
     * @param key 排行榜类型
     * @return
     */
    public CommRankData getCommRankData(RankKey key) {
        return rankDatas.computeIfAbsent(key, k -> {
            throw new NullPointerException();
        });
    }

    private void initRankDatas() {
        for (RankType type : RankType.values()) {
            for (RankSubType subType : RankSubType.values()) {
                RankKey key = RankKey.valueOf(type, subType);
                CommRankData data = rankDatas.computeIfAbsent(key, k -> type.createCommRankData(subType));
                if (type.isNeedSvDB()) {
                    RankEntity rankEntity = getOrCreateRankEntity(RankKey.valueOf(type, subType));
                    data.initData(rankEntity);
                }
            }
        }
    }

    public RankEntity getOrCreateRankEntity(RankKey key) {
        //TODO 获取排行信息
        RankEntity entity = new RankEntity();
        entity.setKey(key);
        return entity;
    }


    public void update(RankKey key) {
        if (!key.getType().isNeedSvDB()) {
            return;
        }

        CommRankData commRankData = getCommRankData(key);
        RankEntity rankEntity = commRankData.toEntity();
        //TODO 入库

    }


    public boolean startRanking() {
        return startRank.get();
    }

    public static RankManager getInstance() {
        return self;
    }

    public final void shutdown() {
        if (startRank.compareAndSet(true, false)) {
            //BeanService.getBean(RankService.class).processShutdown();
        }
    }


    /**
     * 提交成员
     *
     * @param data
     */
    public void commit(RankKey key, IRankingObject data) {
        if (!RankManager.getInstance().startRanking()) {
            return;
        }

        CommRankData rankData = RankManager.getInstance().getCommRankData(key);
        rankData.putCommit(data.getId(), data);
        if (rankData.getRankPeriod() == 0) {
            flushRanks(key, true);
        }
    }

    private void submit(CommRankData rankData, IRankingObject newObject) {
        RankType rankType = rankData.getKey().getType();
        RankSubType subType = rankData.getKey().getSubType();
        RankResource rankResource = RankManager.getInstance().getRankResource(rankType);
        if (rankType.isLowMin(newObject.getValue())) {
            return;
        }
        LinkedList<IRankingObject> list = rankData.getLinkedList();
        IRankingObject oldObject = rankData.getRankObjectInLinkedList(newObject.getId());
        WaveType waveType;
        //有旧数据，先移除，再把新值放在刚才位置。否则加到末尾
        if (!Objects.isNull(oldObject)) {
            waveType = newObject.getValue().compareTo(oldObject.getValue());
            LinkedList.Node<IRankingObject> oldNode = list.get(oldObject);
            list.replace(oldNode, newObject);
        } else {
            LinkedList.Node<IRankingObject> last = list.getLast();
            boolean isFull = list.size() >= rankResource.getMax();
            if (isFull) {
                boolean low2last = newObject.getValue().compareTo(last.getItem().getValue()) == WaveType.DOWM;
                if (low2last) {
                    return;
                }
                list.remove(last);
            }
            waveType = WaveType.UP;
            list.addLast(newObject);
        }
        sortRank(list, newObject, waveType);
        LinkedList.Node<IRankingObject> last = list.getLast();
        boolean fallout = rankResource.isFallOut() && rankType.isLowMin(last.getItem().getValue());
        if (fallout || list.size() > rankResource.getMax()) {
            list.remove(last);
        }

    }

    private void sortRank(LinkedList<IRankingObject> orderList, IRankingObject newObject, WaveType waveType) {
        if (orderList.size() <= 1 || waveType == WaveType.NONE) {
            return;
        }
        LinkedList.Node<IRankingObject> curNode = orderList.get(newObject);
        LinkedList.Node<IRankingObject> nextNode;
        if (waveType == WaveType.UP) {
            nextNode = orderList.getPrev(curNode);
            while (!orderList.isHead(nextNode) && curNode.getItem().getValue().compareTo(nextNode.getItem().getValue()) == waveType) {
                nextNode = orderList.getPrev(nextNode);
            }
            orderList.remove(curNode);
            orderList.addAfter(nextNode, newObject);
        } else {
            nextNode = orderList.getNext(curNode);
            while (!orderList.isTail(nextNode) && curNode.getItem().getValue().compareTo(nextNode.getItem().getValue()) == waveType) {
                nextNode = orderList.getNext(nextNode);
            }
            orderList.remove(curNode);
            orderList.addBefore(nextNode, newObject);
        }
    }


    public void rank(RankKey key, boolean forceRank) {
        CommRankData rankData = RankManager.getInstance().getCommRankData(key);
        synchronized (rankData) {
            if (rankData.getCommitSize() == 0) {
                return;
            }
            long now = System.currentTimeMillis();
            if (!forceRank && rankData.getLastRankTime() + rankData.getRankPeriod() > now) {
                return;
            }
            rankData.setLastRankTime(now);
            Object[] keys = rankData.getCommitKeys();

            for (Object k : keys) {
                IRankingObject rankingObject = rankData.removeCommits(k);
                submit(rankData, rankingObject);
            }
            rankData.updateOrderRankData();
            RankManager.getInstance().update(key);
        }

    }

    /**
     * 强制排序所有数据
     */
    public void flushRanks(boolean forceRank) {
        for (RankType type : RankType.values()) {
            for (RankSubType subType : RankSubType.values()) {
                flushRanks(RankKey.valueOf(type, subType), forceRank);
            }
        }
    }

    public void flushRanks(RankKey key, boolean forceRank) {
        rank(key, forceRank);
    }

    public RankResource getRankResource(RankType type) {
        //TODO GET RankResource;
        RankResource resource = new RankResource();
        return resource;
    }
}
