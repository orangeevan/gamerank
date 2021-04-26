package com.yuen.rank.player;

/**
 * @author: yuanchengyan
 * @description:
 * @since 14:49 2021/4/26
 */
public class PlayerShortInfo {
    private long playerId;
    private String name;

    public PlayerShortInfo(long playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

