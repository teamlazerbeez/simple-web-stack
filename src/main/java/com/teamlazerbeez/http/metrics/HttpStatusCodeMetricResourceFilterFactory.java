package com.teamlazerbeez.http.metrics;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.api.model.AbstractResourceMethod;
import com.sun.jersey.api.model.AbstractSubResourceLocator;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.api.model.PathValue;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;

@Singleton
public final class HttpStatusCodeMetricResourceFilterFactory implements ResourceFilterFactory {

    private static final Logger logger = LoggerFactory.getLogger(HttpStatusCodeMetricResourceFilterFactory.class);

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {

        // documented to only be AbstractSubResourceLocator, AbstractResourceMethod, or AbstractSubResourceMethod
        if (am instanceof AbstractSubResourceLocator) {
            // not actually invoked per request, nothing to do
            logger.debug("Ignoring AbstractSubResourceLocator " + am);
            return null;
        } else if (am instanceof AbstractResourceMethod) {
            String metricBaseName = getMetricBaseName((AbstractResourceMethod) am);
            Class<?> resourceClass = am.getResource().getResourceClass();

            return Lists
                .<ResourceFilter>newArrayList(
                    new HttpStatusCodeMetricResourceFilter(metricBaseName, resourceClass));
        } else {
            logger.warn("Got an unexpected instance of " + am.getClass().getName() + ": " + am);
            return null;
        }
    }

    private String getMetricBaseName(AbstractResourceMethod am) {

        String metricId = getPathWithoutSurroundingSlashes(am.getResource().getPath());

        if (!metricId.isEmpty()) {
            metricId = "/" + metricId;
        }

        String httpMethod;
        if (am instanceof AbstractSubResourceMethod) {
            // if this is a subresource, add on the subresource's path component
            AbstractSubResourceMethod asrm = (AbstractSubResourceMethod) am;
            metricId += "/" + getPathWithoutSurroundingSlashes(asrm.getPath());
            httpMethod = asrm.getHttpMethod();
        } else {
            httpMethod = am.getHttpMethod();
        }

        if (metricId.isEmpty()) {
            // this happens for WadlResource -- that case actually exists at "application.wadl" though
            metricId = "(no path)";
        }

        metricId += " " + httpMethod;

        return metricId;
    }

    private String getPathWithoutSurroundingSlashes(@Nullable PathValue pathValue) {
        if (pathValue == null) {
            return "";
        }
        String value = pathValue.getValue();
        if (value.startsWith("/")) {
            value = value.substring(1);
        }
        if (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }
}