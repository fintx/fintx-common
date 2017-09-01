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

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
@Getter
@Setter
public class TextPoJo {
    String f1;
    String f2;
    String f3;
    boolean f4;
    byte[] bytes = "a".getBytes();
    Timestamp t = new Timestamp(System.currentTimeMillis());
    Integer in = new Integer(0);
    BigDecimal bd = new BigDecimal("0.0");
    char[] chars = "ab".toCharArray();
    char a = 'a';
    Character b = 'b';
    int i=1;
    float f=0.0f;
    double d=0.0d;
    java.util.Date date=new java.util.Date();
    java.sql.Date dat=new java.sql.Date(System.currentTimeMillis());
    Long long1=1L;
    short sh=1;
    byte by=(byte)0x1f;
    Calendar ca=Calendar.getInstance();
    BigInteger bi=new BigInteger("1123235436456546");
}
