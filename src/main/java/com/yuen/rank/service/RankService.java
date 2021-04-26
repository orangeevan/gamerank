package com.yuen.rank.service;

import com.yuen.rank.entity.RankKey;
import com.yuen.rank.manager.RankManager;
import com.yuen.rank.model.IRankingObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: yuanchengyan
 * @description:
 * @since 13:57 2021/3/11
 */
@Component
public class RankService {
    Logger logger = Logger.getLogger(RankService.class);

    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    private RankManager rankManager;

    @PostConstruct
    public void init() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "RankServiceRankingObjecthread"));
        shutdown();
    }

    public void startRank() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (!RankManager.getInstance().startRanking()) {
                return;
            }
            rankManager.flushRanks(false);

        }, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    private void shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> rankManager.flushRanks(true)));
    }
}
