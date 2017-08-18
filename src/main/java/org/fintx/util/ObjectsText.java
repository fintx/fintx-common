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

import org.fintx.lang.Encoding;
import org.fintx.util.convertor.BaseTypeObjectStringConvertor;
import org.fintx.util.convertor.ObjectStringConvertor;

import lombok.AllArgsConstructor;

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
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class ObjectsText implements ObjectStringConvertor {
    // TODO use for cached
    // private static ThreadLocal<ObjectStringConvertor> tlConvertors = new ThreadLocal<ObjectStringConvertor>();
    // TODO use as builder configuration

    private final Encoding encoding;
    private final Character separator;
    private final Character associator;
    private final String linebreak;
    private final boolean withname;

    public ObjectsText(Encoding encoding, Character separator, String linebreak, boolean withname, Character...associator) {
        this.encoding = encoding;
        this.separator = separator;
        if (withname) {
            if (null == associator || null == associator[0]) {
                throw new IllegalArgumentException("Argument associate must have at leaset one value with length>1!");
                
            } else {
                this.associator = associator[0];
            }
        } else {
            this.associator = null;
        }
        this.linebreak = linebreak;
        this.baseTypeConvertor = new BaseTypeObjectStringConvertor(encoding);
        this.withname = withname;
    }

    private final ObjectStringConvertor baseTypeConvertor;

    /**
     * Convert from bean to string text.
     * 
     * @param bean the bean object to be convert
     * @return String text of bean
     * @throws ReflectiveOperationException when parameter bean is not a valid bean
     */

    public <T> String toString(final T bean) throws ReflectiveOperationException {
        if (null == bean) {
            return "";
        } else {
            if (isBean(bean.getClass())) {
                return doToText(bean);
            } else {
                throw new IllegalArgumentException("Object is not a bean");
            }
        }
    }

    /**
     * 
     * @param text text to be convert to object
     * @param clazz target class type of object
     * @return target object
     * @throws ReflectiveOperationException when parameter calzz is not a bean class
     */

    public <T> T toObject(final String text, final Class<T> clazz) throws ReflectiveOperationException {
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
     * @param bean to be converted to text string
     * @return the text string
     * @throws ReflectiveOperationException when there is any exception during conversion.
     */
    private <T> String doToText(final T bean) throws ReflectiveOperationException {
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
                // method = clazz.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                // fieldValue = method.invoke(bean, new Object[0]);
                f.setAccessible(true);
                fieldValue = f.get(bean);
                if (withname) {
                    sb.append(fieldName);
                    sb.append(associator);
                }

                if (null != fieldValue) {
                    if (0 == i && Iterable.class.isAssignableFrom(f.getType())) {
                        Iterator<?> it = ((Iterable<?>) fieldValue).iterator();
                        while (it.hasNext()) {
                            try {
                                sb.append(doToText(it.next()) + linebreak);
                            } catch (Exception e) {
                                if (e instanceof ReflectiveOperationException) {
                                    throw (ReflectiveOperationException) e;
                                } else {
                                    throw new ReflectiveOperationException(e.getMessage());
                                }
                            }
                        }
                    } else {
                        try {
                            sb.append(baseTypeConvertor.toString(fieldValue));
                        } catch (Exception e) {
                            if (e instanceof ReflectiveOperationException) {
                                throw (ReflectiveOperationException) e;
                            } else {
                                throw new ReflectiveOperationException(e.getMessage());
                            }
                        }
                    }
                }
                if (i < (fields.length - 1)) {
                    sb.append(separator);
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
     * @throws ReflectiveOperationException when there is any exception during conversion
     */
    private <T> T doToBean(final String text, final Class<T> clazz) throws ReflectiveOperationException {
        Field[] fields = clazz.getDeclaredFields();
        T bean = clazz.newInstance();
        String fieldName = null;
        if (fields.length == 1 && List.class.isAssignableFrom(fields[0].getType())) {
            fieldName = fields[0].getName().substring(0, 1).toUpperCase() + fields[0].getName().substring(1);
            Class<?> genericClass = (Class<?>) ((ParameterizedType) fields[0].getGenericType()).getActualTypeArguments()[0];
            List<Object> list = new ArrayList<Object>();
           
            String subText = null;
            if (withname) {
                subText=text.substring(text.indexOf(associator) + 1);
            }else {
                subText=text;
            }
            
            byte[] textBytes;
            try {
                textBytes = subText.getBytes(encoding.getCode());
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
            String[] fieldTexts = Strings.split(text, separator);
            if (withname) {
                for (String fieldText : fieldTexts) {
                    String[] fieldPair = null;
                    int associatorIndex = fieldText.indexOf(associator);
                    if (associatorIndex > 0 && associatorIndex < fieldText.length() - 1) {
                        fieldPair = new String[2];
                        fieldPair[0] = fieldText.substring(0, associatorIndex);
                        fieldPair[1] = fieldText.substring(associatorIndex + 1);
                        Field field = clazz.getDeclaredField(fieldPair[0].substring(0, 1).toLowerCase() + fieldPair[0].substring(1));
                        // Method method = clazz.getDeclaredMethod("set" + fieldPair[0], field.getType());
                        try {
                            // method.invoke(bean, baseTypeConvertor.toObject(fieldPair[1], field.getType()));
                            field.setAccessible(true);
                            field.set(bean, baseTypeConvertor.toObject(fieldPair[1], field.getType()));
                        } catch (Exception e) {
                            throw new ReflectiveOperationException(e);
                        }
                    } else if (associatorIndex != fieldText.length() - 1) {
                        throw new RuntimeException("Text format incorrect：" + fieldText);
                    }
                }

            } else {
                if (fieldTexts.length != fields.length) {
                    for(int i=0;i<fieldTexts.length;i++) {
                        System.out.println("---------------------"+fieldTexts[i]);
                    }
                    throw new RuntimeException("Text part number " + fieldTexts.length + " is not match the field number " + fields.length);
                }
                for (int i = 0; i < fieldTexts.length; i++) {
                    try {
                        fields[i].setAccessible(true);
                        fields[i].set(bean, baseTypeConvertor.toObject(fieldTexts[i], fields[i].getType()));
                    } catch (Exception e) {
                        throw new ReflectiveOperationException(e);
                    }
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
            // check whether there are methods that are nether set nor get
            if (!name.startsWith("set") && !name.startsWith("get") && !name.startsWith("is")) {
                flag = false;
            }
        }
        return flag;
    }

}
