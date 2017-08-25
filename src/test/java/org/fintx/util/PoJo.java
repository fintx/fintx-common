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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class PoJo {

    private String str;
    String[] strs;
    protected int in;
    public int[] ins;
    byte[] bytes=new byte[10];
    List<String> list;
 
    Object[] objs=new Object[1];
//    PoJo pojo=this;
   
    

}
