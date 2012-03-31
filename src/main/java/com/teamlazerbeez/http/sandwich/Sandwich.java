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

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class Sandwich {

    private int gramsOfPeanutButter;

    private int gramsOfJam;

    void addPeanutButter(int grams) {
        gramsOfPeanutButter += grams;
    }

    void addJam(int grams) {
        gramsOfJam += grams;
    }

    @JsonProperty
    public int getGramsOfPeanutButter() {
        return gramsOfPeanutButter;
    }

    @JsonProperty
    public int getGramsOfJam() {
        return gramsOfJam;
    }
}
