/**
 * www.beebank.com Inc.
 * Copyright (c) 2017 All Rights Reserved.
 */
package org.fintx.util;

import java.math.BigInteger;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author admin
 *
 */
public final class NullableEmptyStringAdapter extends XmlAdapter<String,BigInteger> {

    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
     */
    @Override
    public BigInteger unmarshal(String v) throws Exception {
        // TODO Auto-generated method stub
        if(null==v||v.trim().equals("")){
            return null;
        }else{
            return new BigInteger(v);
        }
    }

    /* (non-Javadoc)
     * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
     */
    @Override
    public String marshal(BigInteger v) throws Exception {
        if(null==v){
            return "";
        }else{
            return v.toString();
        }
        
    }

}
