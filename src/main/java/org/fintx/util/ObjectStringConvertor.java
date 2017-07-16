/**
 * www.beebank.com Inc.
 * Copyright (c) 2017 All Rights Reserved.
 */
package org.fintx.util;

//import java.util.Properties;

/**
 * @author admin
 *
 */
public interface ObjectStringConvertor {
    public <T> String toString(T obj) throws Exception;

    public <T> T toObject(String text, Class<T> clazz) throws Exception;

    //public <T> String toString(T obj, Properties prop) throws Exception;

    //public <T> T toObject(String text, Class<T> clazz, Properties prop) throws Exception;
}
