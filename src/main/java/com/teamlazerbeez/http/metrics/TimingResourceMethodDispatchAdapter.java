package com.teamlazerbeez.http.metrics;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.spi.container.ResourceMethodDispatchAdapter;
import com.sun.jersey.spi.container.ResourceMethodDispatchProvider;

import javax.ws.rs.ext.Provider;

@Singleton
@Provider
final class TimingResourceMethodDispatchAdapter implements ResourceMethodDispatchAdapter {

    private final MetricRegistry metricRegistry;

    @Inject
    TimingResourceMethodDispatchAdapter(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    public ResourceMethodDispatchProvider adapt(ResourceMethodDispatchProvider provider) {
        return new TimingResourceMethodDispatchProvider(metricRegistry, provider);
    }
}
