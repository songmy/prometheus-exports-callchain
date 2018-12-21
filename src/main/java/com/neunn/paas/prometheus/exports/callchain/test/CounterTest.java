package com.neunn.paas.prometheus.exports.callchain.test;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author songmy
 */
@Component
public class CounterTest {
    private Counter counter = Counter.build().name("prometheus_callchain_test_counter").help("帮助文档-Counter")
            .labelNames("user","test").namespace("PAAS").subsystem("TEST")
            .register();
    private ScheduledFuture<?> applicationRegisterFuture = null;
    private Random random = new Random(10);

    @PostConstruct
    public void init() {
        applicationRegisterFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    try {
                        counter.labels("sonmy","dd").inc(random.nextInt(5) + 1);
                        counter.labels("wubg","ee").inc(random.nextInt(5) + 1);
                        counter.labels("lp","ff").inc(random.nextInt(5) + 1);
                        counter.labels("tr","gg").inc(random.nextInt(5) + 1);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                , 0, 1, TimeUnit.SECONDS
        );
    }

    @PreDestroy
    public void destory() {
        if (applicationRegisterFuture != null)
            applicationRegisterFuture.cancel(true);
    }
}
