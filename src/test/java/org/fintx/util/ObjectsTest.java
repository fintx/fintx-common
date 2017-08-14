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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class ObjectsTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDeepClone() {
        
       
        // String a="a";
        // String b="a";
        // String c=new String("a");
        // Assert.assertTrue(a==b);
        // Assert.assertFalse(a==c);
        byte b=1;
        b=Objects.deepClone(b);
        byte[] bytes = Objects.deepClone(new byte[10]);
        Array.getLength(bytes);
        Assert.assertTrue(10 == bytes.length);
        int[] ints = Objects.deepClone(new int[10]);
        Assert.assertTrue(10 == ints.length);
        PoJo pojo = new PoJo();
        pojo.setIn(0);
        ints[0] = 1;
        pojo.setIns(ints);
        pojo.setObjs(new Object[10]);
        pojo.setStr("aa");

        pojo.setStrs(new String[3]);
        bytes[9] = 10;
        pojo.setBytes(bytes);
        pojo.getBytes()[2] = 1;
        Assert.assertFalse(pojo == Objects.deepClone(pojo));
        Assert.assertTrue(0 == pojo.getIn());
        Assert.assertTrue(1 == pojo.getIns()[0]);
        Assert.assertTrue("aa" == pojo.getStr());
        Assert.assertTrue(10 == pojo.getBytes()[9]);
    }

}
