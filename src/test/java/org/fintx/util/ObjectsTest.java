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

import org.fintx.lang.Encoding;

import net.sf.cglib.beans.BeanCopier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class ObjectsTest {

   

    @Test
    public void testDeepClone() {
        BeanCopier bc=BeanCopier.create(String.class, String.class, false);
        String str=null;
        bc.copy("", str, null);
        Assert.assertTrue(String.class.equals("a".getClass()));
        Assert.assertTrue(Objects.isBaseType("a".getClass()));
        Assert.assertTrue(Objects.isBaseType(String.class));
        Assert.assertFalse(Objects.isBaseType(Date.class));
        Assert.assertFalse(Objects.isWrapperType("a".getClass()));
        Assert.assertTrue(Objects.isWrapperType(new Boolean(false).getClass()));
        // String a="a";
        // String b="a";
        // String c=new String("a");
        // Assert.assertTrue(a==b);
        // Assert.assertFalse(a==c);
        byte b = 1;
        b = Objects.deepClone(b);
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
        Assert.assertTrue("aa".equals(pojo.getStr()));
        Assert.assertTrue(10 == pojo.getBytes()[9]);
        Assert.assertTrue(Objects.deepClone("a").equals("a"));
        String[] strs=Objects.deepClone(new String[] {"a","b"});
        Assert.assertTrue(strs[0].equals("a"));
    }

    @Test
    public void testCopyProperties() {
        int[] ints = new int[10];
        PoJo pojo = new PoJo();
        pojo.setIn(0);
        ints[0] = 1;
        pojo.setIns(ints);
        pojo.setObjs(new Object[10]);
        pojo.setStr("aa");
        pojo.setStrs(new String[3]);
        byte[] bytes = new byte[10];
        bytes[9] = 10;
        pojo.setBytes(bytes);
        pojo.getBytes()[2] = 1;
        APoJo aPojo = new APoJo();
        aPojo.setAnotherProperties("bb");
        Objects.copyProperties(pojo, aPojo);
        Assert.assertTrue(0 == aPojo.getIn());
        Assert.assertTrue(1 == aPojo.getIns()[0]);
        Assert.assertTrue("aa" == aPojo.getStr());
        Assert.assertTrue(10 == aPojo.getBytes()[9]);
        Assert.assertTrue("bb".equals(aPojo.getAnotherProperties()));
        Assert.assertTrue(null == aPojo.getAnotherPropertiesToo());
    }

    @Test
    public void testXML() throws JAXBException {
        byte b = 1;
        b = Objects.deepClone(b);
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
        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        pojo.setList(list);
        String xml = Objects.Xml.toString(pojo);
        System.out.println(xml);
        PoJo pojo2 = Objects.Xml.toObject(xml, PoJo.class);
        Assert.assertTrue(0 == pojo2.getIn());
        Assert.assertTrue(1 == pojo2.getIns()[0]);
        Assert.assertTrue("aa".equals(pojo2.getStr()));
        Assert.assertTrue(10 == pojo2.getBytes()[9]);
        Assert.assertTrue("b".equals(pojo2.getList().get(1)) );

        Map<String,String> mapper=new HashMap<String,String>();
        mapper.put("http://www.w3.org/2001/XMLSchema-instance", "mynsprefix");
        ObjectsXml xmlConvertor = Objects.Xml.custom(mapper, false, Encoding.GB18030, false, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml = xmlConvertor.toString(pojo);
        System.out.println(xml);
        pojo2 = xmlConvertor.toObject(xml, PoJo.class);
        Assert.assertTrue(0 == pojo2.getIn());
        Assert.assertTrue(1 == pojo2.getIns()[0]);
        Assert.assertTrue("aa".equals(pojo2.getStr()));
        Assert.assertTrue(10 == pojo2.getBytes()[9]);
        Assert.assertTrue("b".equals(pojo2.getList().get(1)) );

    }

    @Test
    public void testText() throws ReflectiveOperationException {
        TextPoJo pojo = new TextPoJo();
        pojo.setF1("a");
        pojo.setF3("b");
        String text = null;
        text = Objects.Text.toString(pojo);

        System.out.println(text);
        TextPoJo pojo2 = Objects.Text.toObject(text, TextPoJo.class);
        Assert.assertTrue(pojo2.getF1() == pojo2.getF1() && pojo2.getF1().equals("a"));
        Assert.assertTrue(pojo2.getF3() == pojo2.getF3() && pojo2.getF3().equals("b"));

        ObjectsText convertor = Objects.Text.custom(Encoding.GB18030, '|', "\r\n", true, '=');
        text = convertor.toString(pojo);
        System.out.println(text);
        pojo2 = convertor.toObject(text, TextPoJo.class);
        Assert.assertTrue(pojo2.getF1() == pojo2.getF1() && pojo2.getF1().equals("a"));
        Assert.assertTrue(pojo2.getF3() == pojo2.getF3() && pojo2.getF3().equals("b"));

        TextPoJo2 textPoJo2 = new TextPoJo2();
        textPoJo2.setList(new ArrayList<TextPoJo>());
        textPoJo2.getList().add(pojo2);
        textPoJo2.getList().add(pojo);
        textPoJo2.getList().add(pojo);
        text = Objects.Text.toString(textPoJo2);
        System.out.println(text);
        TextPoJo2 textPoJo3 = Objects.Text.toObject(text, TextPoJo2.class);
        Assert.assertTrue(textPoJo2.getList().get(0).getF1() == pojo2.getF1() && pojo2.getF1().equals("a"));
        Assert.assertTrue(textPoJo2.getList().get(0).getF3() == pojo2.getF3() && pojo2.getF3().equals("b"));
        text = convertor.toString(textPoJo2);
        System.out.println(text);
        textPoJo3 = convertor.toObject(text, TextPoJo2.class);
        Assert.assertTrue(textPoJo2.getList().get(0).getF1() == pojo2.getF1() && pojo2.getF1().equals("a"));
        Assert.assertTrue(textPoJo2.getList().get(0).getF3() == pojo2.getF3() && pojo2.getF3().equals("b"));
    }
    
    @Test
    public void testHashAndHashCode() {
        System.out.println(Objects.hash());
        System.out.println(Objects.hash(null));
        Objects.hashCode(null);
        
        System.out.println(Objects.hash(new Object()));
        Objects.hashCode(new Object());
        
        System.out.println(Objects.hash(new Object[] {}));
        Objects.hashCode(new Object[] {});
        
        System.out.println(Objects.hash(new Object[] {new Object()}));
        Objects.hashCode(new Object[] {new Object()});
        
        System.out.println(Objects.hash(new int[] {}));
        Objects.hashCode(new int[] {});
        
        System.out.println(Objects.hash(new int[] {0}));
        Objects.hashCode(new int[] {0});
        
        System.out.println(Objects.hash(new long[] {}));
        Objects.hashCode(new long[] {});
        
        System.out.println(Objects.hash(new long[] {0}));
        Objects.hashCode(new long[] {0});
        
        System.out.println(Objects.hash(new double[] {}));
        Objects.hashCode(new double[] {});
        
        System.out.println(Objects.hash(new double[] {0}));
        Objects.hashCode(new double[] {0});
        
        System.out.println(Objects.hash(new float[] {}));
        Objects.hashCode(new float[] {});
        
        System.out.println(Objects.hash(new float[] {0}));
        Objects.hashCode(new float[] {0});
        
        System.out.println(Objects.hash(new short[] {}));
        Objects.hashCode(new short[] {});
        
        System.out.println(Objects.hash(new short[] {0}));
        Objects.hashCode(new short[] {0});
        
        System.out.println(Objects.hash(new boolean[] {}));
        Objects.hashCode(new boolean[] {});
        
        System.out.println(Objects.hash(new boolean[] {true}));
        Objects.hashCode(new boolean[] {false});
        
        System.out.println(Objects.hash(new byte[] {}));
        Objects.hashCode(new byte[] {});
        
        System.out.println(Objects.hash(new byte[] {(byte) 0xff}));
        Objects.hashCode(new byte[] {(byte) 0xff});
    }

}
