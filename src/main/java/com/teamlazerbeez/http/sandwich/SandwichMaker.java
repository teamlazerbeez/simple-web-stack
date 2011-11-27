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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

@ThreadSafe
public class SandwichMaker {

    private final PeanutButter peanutButter;

    @Inject
    SandwichMaker(@Nonnull PeanutButter peanutButter) {
        this.peanutButter = peanutButter;
    }

    Sandwich makeSandwich(int gramsOfPeanutButter, int gramsOfJam) {

        Sandwich sandwich = new Sandwich();
        peanutButter.applyToSandwich(sandwich, gramsOfPeanutButter);

        // let's not overcomplicate things... jam is easy
        sandwich.addJam(gramsOfJam);
        return sandwich;
    }
}
