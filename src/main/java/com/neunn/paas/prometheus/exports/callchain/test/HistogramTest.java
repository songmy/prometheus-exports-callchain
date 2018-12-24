package com.neunn.paas.prometheus.exports.callchain.test;

import io.prometheus.client.Histogram;
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
public class HistogramTest {
    private ScheduledFuture<?> applicationRegisterFuture = null;
    private Random random = new Random(1);
    private double [] buckets={0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1};
    private Histogram histogram = Histogram.build().name("prometheus_callchain_test_histogram").help("帮助文档-Histogram")
            .labelNames("user","test").namespace("PAAS").subsystem("TEST").buckets(buckets)
            .register();
    @PostConstruct
    public void init() {
        applicationRegisterFuture=Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            histogram.labels("songmy","dd").time(()->{
                try {
                    Thread.sleep((long) (1000*buckets[random.nextInt(10)]));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            histogram.labels("wbg","ee").time(()->{
                try {
                    Thread.sleep(1000*random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            histogram.labels("lp","ff").time(()->{
                try {
                    Thread.sleep(1000*random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            histogram.labels("wz","gg").time(()->{
                try {
                    Thread.sleep(1000*random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        },0,1,TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destory() {
        if (applicationRegisterFuture != null)
            applicationRegisterFuture.cancel(true);
    }
}
