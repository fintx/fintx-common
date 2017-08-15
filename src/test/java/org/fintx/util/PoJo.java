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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class PoJo {
    /**
     * @return the str
     */
    public String getStr() {
        return str;
    }
    /**
     * @param str the str to set
     */
    public void setStr(String str) {
        this.str = str;
    }
    /**
     * @return the strs
     */
    public String[] getStrs() {
        return strs;
    }
    /**
     * @param strs the strs to set
     */
    public void setStrs(String[] strs) {
        this.strs = strs;
    }
    /**
     * @return the in
     */
    public int getIn() {
        return in;
    }
    /**
     * @param in the in to set
     */
    public void setIn(int in) {
        this.in = in;
    }
    /**
     * @return the ins
     */
    public int[] getIns() {
        return ins;
    }
    /**
     * @param ins the ins to set
     */
    public void setIns(int[] ins) {
        this.ins = ins;
    }
    /**
     * @return the objs
     */
    public Object[] getObjs() {
        return objs;
    }
    /**
     * @param objs the objs to set
     */
    public void setObjs(Object[] objs) {
        this.objs = objs;
    }
    private String str;
    String[] strs;
    protected int in;
    public int[] ins;
    byte[] bytes=new byte[10];

    /**
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }
    /**
     * @param bytes the bytes to set
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    Object[] objs=new Object[1];
//    PoJo pojo=this;
   
    

}
