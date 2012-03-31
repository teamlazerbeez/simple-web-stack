/*
 * Copyright © 2011. Team Lazer Beez (http://teamlazerbeez.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teamlazerbeez.http.sandwich;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.teamlazerbeez.http.metrics.HttpStatusCodeMetricResourceFilterFactory;

import java.util.HashMap;
import java.util.Map;

public class SandwichServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(SandwichStatsResource.class);
        bind(SandwichMakerResource.class);

        // hook Jersey into Guice Servlet
        bind(GuiceContainer.class);

        // hook Jackson into Jersey as the POJO <-> JSON mapper
        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);

        Map<String, String> guiceContainerConfig = new HashMap<String, String>();
        guiceContainerConfig.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
            HttpStatusCodeMetricResourceFilterFactory.class.getCanonicalName());
        serve("/*").with(GuiceContainer.class, guiceContainerConfig);
    }
}
