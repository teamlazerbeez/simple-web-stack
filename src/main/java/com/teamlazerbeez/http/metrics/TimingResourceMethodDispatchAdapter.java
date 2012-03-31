package com.teamlazerbeez.http.metrics;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.spi.container.ResourceMethodDispatchAdapter;
import com.sun.jersey.spi.container.ResourceMethodDispatchProvider;
import com.yammer.metrics.core.MetricsRegistry;

import javax.ws.rs.ext.Provider;

@Singleton
@Provider
final class TimingResourceMethodDispatchAdapter implements ResourceMethodDispatchAdapter {

    private final MetricsRegistry metricsRegistry;

    @Inject
    TimingResourceMethodDispatchAdapter(MetricsRegistry metricsRegistry) {
        this.metricsRegistry = metricsRegistry;
    }

    @Override
    public ResourceMethodDispatchProvider adapt(ResourceMethodDispatchProvider provider) {
        return new TimingResourceMethodDispatchProvider(metricsRegistry, provider);
    }
}
