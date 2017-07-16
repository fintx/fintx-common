/**
 * www.beebank.com Inc.
 * Copyright (c) 2017 All Rights Reserved.
 */
package org.fintx.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author admin
 *
 */
public class BeanTextUtil {
//TODO use for cached
    private static ThreadLocal<ObjectStringConvertor> tlConvertors = new ThreadLocal<ObjectStringConvertor>();
//TODO use as builder configuration
    private static String encoding="GBK";
    
    private static ObjectStringConvertor baseTypeConvertor=new DefaultBaseTypeObjectStringConvertor();
    
    private BeanTextUtil() {
        throw new AssertionError("No BeanTextUtil instances for you!");
    }

    /**
     * 
     * @param bean
     * @return String text of bean
     * @throws ReflectiveOperationException
     */

    public static <T> String toText(final T bean) throws ReflectiveOperationException {
        if (null == bean) {
            return "";
        } else {
            if (isBean(bean.getClass())) {
                return doToText(bean);
            } else {
                throw new ReflectiveOperationException("Object is not a bean");
            }
        }
    }

    /**
     * 
     * @param text text to be convert to object
     * @param clazz target class type of object
     * @return target object
     * @throws ReflectiveOperationException
     */

    public static <T> T toBean(final String text, final Class<T> clazz) throws ReflectiveOperationException {
        if (null == text || "".equals(text.trim())) {
            return null;
        } else {
            if (isBean(clazz)) {
                return doToBean(text, clazz);
            } else {
                throw new ReflectiveOperationException("Object is not a bean");
            }
        }
    }

    /**
     * @param bean
     * @return
     * @throws ReflectiveOperationException
     */
    private static <T> String doToText(final T bean) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) bean.getClass();
        if (isBean(clazz)) {
            // Method[] methods=clazz.getDeclaredMethods();
            Field[] fields = clazz.getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            Method method = null;
            String fieldName = null;
            Object fieldValue = null;
            Field f = null;
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                fieldName = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                method = clazz.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                fieldValue = method.invoke(bean, new Object[0]);
                sb.append(fieldName);
                sb.append("=");
                if (null != fieldValue) {
                    if (Iterable.class.isAssignableFrom(f.getType())) {
                        Iterator<?> it = ((Iterable<?>) fieldValue).iterator();
                        while (it.hasNext()) {
                            try {
                                sb.append(doToText(it.next()) + "\r\n");
                            } catch (Exception e) {
                                throw new ReflectiveOperationException(e.getMessage());
                            }
                        }
                    } else {
                        try {
                            sb.append(baseTypeConvertor.toString(fieldValue));
                        } catch (Exception e) {
                            throw new ReflectiveOperationException(e.getMessage());
                        }
                    }
                }
                if (i < (fields.length - 1)) {
                    sb.append("|");
                }
            }
            return sb.toString();
        } else {
            throw new ReflectiveOperationException("Object is not a bean!");
        }
    }

    /**
     * @param text text to be convert to object
     * @param clazz target class type of object
     * @return target object
     * @throws ReflectiveOperationException
     */
    private static <T> T doToBean(final String text, final Class<T> clazz) throws ReflectiveOperationException {
        Field[] fields = clazz.getDeclaredFields();
        T bean = clazz.newInstance();
        String fieldName = null;
        if (fields.length == 1 && List.class.isAssignableFrom(fields[0].getType())) {
            fieldName = fields[0].getName().substring(0, 1).toUpperCase() + fields[0].getName().substring(1);
            Class<?> genericClass = (Class<?>) ((ParameterizedType) fields[0].getGenericType()).getActualTypeArguments()[0];
            List<Object> list = new ArrayList<Object>();
            String subText = text.substring(text.indexOf("=") + 1);
            byte[] textBytes;
            try {
                textBytes = subText.getBytes(encoding);
                InputStream in = new ByteArrayInputStream(textBytes);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String subLine;
                while (null != (subLine = br.readLine())) {
                    list.add(doToBean(subLine, genericClass));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (list.size() > 0) {
                Method method = clazz.getMethod("set" + fieldName, fields[0].getType());
                method.invoke(bean, list);
            }
        } else {
            String[] fieldTexts = text.split("\\|");
            for (String fieldText : fieldTexts) {
                String[] fieldPair = null;
                int equalCharIndex = fieldText.indexOf('=');
                if (equalCharIndex > 0 && equalCharIndex < fieldText.length() - 1) {
                    fieldPair = new String[2];
                    fieldPair[0] = fieldText.substring(0, equalCharIndex);
                    fieldPair[1] = fieldText.substring(equalCharIndex + 1);
                    Method method = clazz.getDeclaredMethod("set" + fieldPair[0], fields[0].getType());
                    Field field = clazz.getDeclaredField(fieldPair[0].substring(0, 1).toLowerCase() + fieldPair[0].substring(1));
                    try {
                        method.invoke(bean, baseTypeConvertor.toObject(fieldPair[1], field.getType()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if(equalCharIndex!=fieldText.length() - 1){
                    throw new RuntimeException("Text format incorrect：" + fieldText);
                }
            }
        }
        return bean;
    }

    /**
     * Check whether the class is a java bean class
     */
    private static boolean isBean(Class<?> clazz) {
        boolean flag = true;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            String name = m.getName();
            //check whether there are methods that are nether set nor get
            if (!name.startsWith("set") && !name.startsWith("get")) {
                flag = false;
            }
        }
        return flag;
    }


}
