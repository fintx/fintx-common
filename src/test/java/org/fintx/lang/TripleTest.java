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
package org.fintx.lang;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class TripleTest {

    @Test
    public void test() {
        Triple<String,String,String> pair1=new Triple<String,String,String>();
        pair1.setLeft("a");
        pair1.setRight("b");
        pair1.setMiddle("c");;
        assertTrue(pair1.getLeft()=="a");
        assertTrue(pair1.getRight()=="b");
        assertTrue(pair1.getMiddle()=="c");
        Triple<String,String,String> pair2=new Triple<String,String,String>("a","c","b");
        assertTrue(pair1.getLeft()=="a");
        assertTrue(pair1.getRight()=="b");
        assertTrue(pair1.getMiddle()=="c");
        pair2=Triple.of("a","c","b");
        assertTrue(pair1.getLeft()=="a");
        assertTrue(pair1.getRight()=="b");
        assertTrue(pair1.getMiddle()=="c");
    }

}
