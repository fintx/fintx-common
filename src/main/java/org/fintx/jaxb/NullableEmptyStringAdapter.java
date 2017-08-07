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
package org.fintx.jaxb;

import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public final class NullableEmptyStringAdapter extends XmlAdapter<String, BigInteger> {

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public BigInteger unmarshal(String v) throws Exception {
        // TODO Auto-generated method stub
        if (null == v || v.trim().equals("")) {
            return null;
        } else {
            return new BigInteger(v);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(BigInteger v) throws Exception {
        if (null == v) {
            return "";
        } else {
            return v.toString();
        }

    }

}
