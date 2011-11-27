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

import org.easymock.EasyMock;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class SandwichMakerTest {
    @Test
    public void testMockPeanutButter() throws Exception {

        PeanutButter pbMock = EasyMock.createStrictMock(PeanutButter.class);
        pbMock.applyToSandwich(EasyMock.<Sandwich>anyObject(), eq(300));

        replay(pbMock);

        SandwichMaker sandwichMaker = new SandwichMaker(pbMock);
        sandwichMaker.makeSandwich(300, 200);
        verify(pbMock);
    }
}
