package com.teamlazerbeez.http.metrics;

import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.spi.container.ResourceMethodDispatchProvider;
import com.sun.jersey.spi.dispatch.RequestDispatcher;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Timer;

import static com.teamlazerbeez.http.metrics.HttpStatusCodeMetricResourceFilterFactory.getMetricBaseName;

final class TimingResourceMethodDispatchProvider implements ResourceMethodDispatchProvider {

    private final MetricsRegistry metricsRegistry;
    private final ResourceMethodDispatchProvider wrappedProvider;

    TimingResourceMethodDispatchProvider(MetricsRegistry metricsRegistry,
                                         ResourceMethodDispatchProvider wrappedProvider) {
        this.metricsRegistry = metricsRegistry;
        this.wrappedProvider = wrappedProvider;
    }

    @Override
    public RequestDispatcher create(AbstractResourceMethod abstractResourceMethod) {
        String metricBaseName = getMetricBaseName(abstractResourceMethod);
        Timer timer = metricsRegistry.newTimer(abstractResourceMethod.getResource().getResourceClass(),
            metricBaseName + " timer");
        return new TimingRequestDispatcher(wrappedProvider.create(abstractResourceMethod), timer);
    }
}
