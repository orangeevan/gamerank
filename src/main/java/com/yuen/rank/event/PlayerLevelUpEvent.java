package com.yuen.rank.event;

import com.yuen.rank.player.Player;

/**
 * @author: yuanchengyan
 * @description:
 * @since 16:24 2021/4/26
 */
public class PlayerLevelUpEvent {
    private Player player;

    public static PlayerLevelUpEvent valueOf(Player player) {
        PlayerLevelUpEvent event = new PlayerLevelUpEvent();
        event.player = player;
        return event;
    }

    public Player getPlayer() {
        return player;
    }
}

