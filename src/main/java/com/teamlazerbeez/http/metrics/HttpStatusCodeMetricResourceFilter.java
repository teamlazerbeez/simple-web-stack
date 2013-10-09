package com.teamlazerbeez.http.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.sun.jersey.spi.container.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class HttpStatusCodeMetricResourceFilter implements ResourceFilter, ContainerResponseFilter {

    private final ConcurrentMap<Integer, Counter> counters = new ConcurrentHashMap<Integer, Counter>();

    private final Class<?> resourceClass;

    private final String metricBaseName;
    private MetricRegistry metricRegistry;

    HttpStatusCodeMetricResourceFilter(MetricRegistry metricRegistry, String metricBaseName, Class<?> resourceClass) {
        this.metricRegistry = metricRegistry;
        this.metricBaseName = metricBaseName;
        this.resourceClass = resourceClass;
    }

    @Override
    public ContainerRequestFilter getRequestFilter() {
        // don't filter requests
        return null;
    }

    @Override
    public ContainerResponseFilter getResponseFilter() {
        return this;
    }

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        Integer status = response.getStatus();

        Counter counter = counters.get(status);
        if (counter == null) {
            // despite the method name, this actually will return a previously created metric with the same name
            // TODO make this use an injected registry -- Guice instantiation of ResourceFilterFactory doesn't work yet
            Counter newCounter = metricRegistry.counter(MetricRegistry.name(resourceClass, metricBaseName + " " + status + " counter"));
            Counter otherCounter = counters.putIfAbsent(status, newCounter);
            if (otherCounter != null) {
                // we lost the race to set that counter, but shouldn't create a duplicate since Metrics.newCounter will do the right thing
                counter = otherCounter;
            } else {
                counter = newCounter;
            }
        }

        counter.inc();

        return response;
    }
}

