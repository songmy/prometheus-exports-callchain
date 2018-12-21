package com.neunn.paas.prometheus.exports.callchain.config;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.filter.MetricsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songmy
 */
@Configuration
public class MetricsConfig implements WebMvcConfigurer {
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean<MetricsServlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new MetricsServlet());
        servletRegistrationBean.addUrlMappings("/metrics");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        MetricsFilter metricsFilter = new MetricsFilter();
        FilterRegistrationBean<MetricsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(metricsFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("metric-name","metricName");
        filterRegistrationBean.setInitParameters(initParams);
        return filterRegistrationBean;
    }
}
