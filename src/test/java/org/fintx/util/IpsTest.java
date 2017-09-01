/**
 *  Copyright 2017 FinTx
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
package org.fintx.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class IpsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void test() {
        Ips.getLocalAddress();
        Request request=new Request();
        Ips.getRemoteAddress(request);
        Request2 request2=new Request2();
        Ips.getRemoteAddress(request2);
        Request3 request3=new Request3();
        Ips.getRemoteAddress(request3);
        Request4 request4=new Request4();
        Ips.getRemoteAddress(request4);
        Request5 request5=new Request5();
        Ips.getRemoteAddress(request5);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Argument request should not be null!");
        Ips.getRemoteAddress(null);
    }

}
