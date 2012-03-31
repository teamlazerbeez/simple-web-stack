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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.core.MetricsRegistry;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Singleton;

@ThreadSafe
@Singleton
public class SandwichStats {

    private int sandwichesMade;
    private int gramsOfJam;
    private int gramsOfPeanutButter;
    private final Counter jamCounter;
    private final Counter pbCounter;

    @Inject
    SandwichStats(MetricsRegistry metricsRegistry) {
        metricsRegistry.newGauge(SandwichStats.class, "sandwich-count", new Gauge<Integer>() {
            @Override
            public Integer value() {
                return sandwichesMade;
            }
        });
        jamCounter = metricsRegistry.newCounter(SandwichStats.class, "grams-of-jam");
        pbCounter = metricsRegistry.newCounter(SandwichStats.class, "grams-of-pb");
    }

    synchronized void recordSandwich(Sandwich sandwich) {
        sandwichesMade++;
        gramsOfJam += sandwich.getGramsOfJam();
        gramsOfPeanutButter += sandwich.getGramsOfPeanutButter();

        jamCounter.inc(sandwich.getGramsOfJam());
        pbCounter.inc(sandwich.getGramsOfPeanutButter());
    }

    synchronized StatsSnapshot getStats() {
        return new StatsSnapshot(sandwichesMade, gramsOfJam, gramsOfPeanutButter);
    }

    public static class StatsSnapshot {
        private final int sandwichesMade;
        private final int gramsOfJam;
        private final int gramsOfPeanutButter;

        private StatsSnapshot(int sandwichesMade, int gramsOfJam, int gramsOfPeanutButter) {
            this.sandwichesMade = sandwichesMade;
            this.gramsOfJam = gramsOfJam;
            this.gramsOfPeanutButter = gramsOfPeanutButter;
        }

        @JsonProperty
        public int getSandwichesMade() {
            return sandwichesMade;
        }

        @JsonProperty
        public int getGramsOfJam() {
            return gramsOfJam;
        }

        @JsonProperty
        public int getGramsOfPeanutButter() {
            return gramsOfPeanutButter;
        }
    }
}
