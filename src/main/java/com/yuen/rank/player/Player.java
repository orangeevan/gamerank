package com.yuen.rank.player;

import com.alibaba.fastjson.JSON;

/**
 * @author: yuanchengyan
 * @description:
 * @since 14:19 2021/4/26
 */
public class Player {
    private int level;
    private long objectId;
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public void sendPacket(Object resp) {
        //TODO sendPacket
        System.out.println("sent " + JSON.toJSONString(resp));
    }
}

