package com.neunn.paas.prometheus.exports.callchain.test;

import io.prometheus.client.Gauge;
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
public class GaugeTest {
    private final Gauge gauge = Gauge.build().namespace("PAAS").subsystem("TEST").help("帮助文档-Gauge")
            .name("prometheus_callchain_test_gauge")
            .labelNames("user","test").register();
    private ScheduledFuture<?> applicationRegisterFuture = null;
    private Random random = new Random(10);

    @PostConstruct
    public void init() {
        applicationRegisterFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    try {
                        gauge.setToCurrentTime();
                        gauge.labels("sonmy","dd").inc(random.nextInt(5) + 1);
                        gauge.labels("wubg","ee").inc(random.nextInt(5) + 1);
                        gauge.labels("lp","ff").inc(random.nextInt(5) + 1);
                        gauge.labels("tr","gg").inc(random.nextInt(5) + 1);
                        gauge.labels("sonmy","dd").dec(random.nextInt(5) + 1);
                        gauge.labels("wubg","ee").dec(random.nextInt(5) + 1);
                        gauge.labels("lp","ff").dec(random.nextInt(5) + 1);
                        gauge.labels("tr","gg").dec(random.nextInt(5) + 1);
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
