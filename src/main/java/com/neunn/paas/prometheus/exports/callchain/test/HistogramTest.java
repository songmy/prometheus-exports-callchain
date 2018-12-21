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
    private Histogram histogram = Histogram.build().name("prometheus_callchain_test_counter").help("帮助文档-Histogram")
            .labelNames("user","test").namespace("PAAS").subsystem("TEST").buckets(1,2,3,4,5,6,7,8,9,10)
            .register();
    private ScheduledFuture<?> applicationRegisterFuture = null;
    private Random random = new Random(10);

    @PostConstruct
    public void init() {
        applicationRegisterFuture=Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            Histogram.Timer timerSongmy=histogram.labels("songmy","dd").startTimer();
            Histogram.Timer timerWbg=histogram.labels("wbg","ee").startTimer();
            Histogram.Timer timerLp=histogram.labels("lp","ff").startTimer();
            Histogram.Timer timerTr=histogram.labels("tr","hh").startTimer();



        },0,1,TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destory() {
        if (applicationRegisterFuture != null)
            applicationRegisterFuture.cancel(true);
    }
}
