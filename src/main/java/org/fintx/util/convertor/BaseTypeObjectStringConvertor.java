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
package org.fintx.util.convertor;

import org.fintx.lang.Encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class BaseTypeObjectStringConvertor implements ObjectStringConvertor {
    public BaseTypeObjectStringConvertor(Encoding encoding) {
        this.encoding = encoding;
    }

    private Encoding encoding = Encoding.UTF_8;

    public <T> String toString(T obj) throws ReflectiveOperationException {
        if (null == obj) {
            return "";
        }
        String type = obj.getClass().getName();
        if (type.equals("java.lang.String")) {
            return obj.toString();
        } else if (type.equals("char[]")) {
            return new String((char[]) obj);
        } else if (type.equals("char") || type.equals("java.lang.Character")) {
            return obj.toString();
        } else if (type.equals("int") || type.equals("java.lang.Integer")) {
            return obj.toString();
        } else if (type.equals("byte[]")) {
            return new String((byte[]) obj);
        } else if (type.equals("float") || type.equals("java.lang.Float")) {
            return obj.toString();
        } else if (type.equals("double") || type.equals("java.lang.Double")) {
            return obj.toString();
        } else if (type.equals("long") || type.equals("Long")) {
            return obj.toString();
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
            return obj.toString();
        } else if (type.equals("java.math.BigDecimal")) {
            return obj.toString();
        } else if (type.equals("java.sql.Timestamp")) {
            return LocalDateTime.ofInstant(((java.sql.Timestamp) obj).toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else if (type.equals("java.sql.Time")) {
            return LocalDateTime.ofInstant(((java.sql.Time) obj).toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HHmmss"));
        } else if (type.equals("java.sql.Date")) {
            return LocalDateTime.ofInstant(((java.sql.Date) obj).toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.BASIC_ISO_DATE);
        } else if (type.equals("java.util.Date")) {
            return LocalDateTime.ofInstant(((java.util.Date) obj).toInstant(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else if (type.equals("byte") || type.equals("java.lang.Byte")) {
            // TODO ??
            return obj.toString();
        } else if (type.equals("short") || type.equals("java.lang.Short")) {
            return obj.toString();
        } else if (type.equals("java.math.BigInteger")) {
            return obj.toString();
        } else if (type.equals("java.util.Calendar")) {
            return LocalDateTime.ofInstant((((java.util.Calendar) obj).getTime()).toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else if (type.equals("java.sql.Clob")) {
            java.sql.Clob clob = (java.sql.Clob) obj;
            StringBuilder sb = new StringBuilder();
            BufferedReader br;
            try {
                br = new BufferedReader(clob.getCharacterStream());
                String line = null;
                while (null != (line = br.readLine())) {
                    sb.append(line);
                }
            } catch (SQLException | IOException e) {
                throw new ReflectiveOperationException(e.getMessage());
            }
            return sb.toString();
        } else if (type.equals("java.sql.Blob")) {
            java.sql.Blob blob = (java.sql.Blob) obj;
            StringBuilder sb = new StringBuilder();
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(blob.getBinaryStream()));
                String line = null;
                while (null != (line = br.readLine())) {
                    sb.append(line);
                }
            } catch (SQLException | IOException e) {
                throw new ReflectiveOperationException(e.getMessage());
            }
            return sb.toString();
        } else {
            throw new ReflectiveOperationException("Unsupport type class name:" + obj.getClass().getName() + " class canonical name:"
                    + obj.getClass().getCanonicalName() + " value:" + String.valueOf(obj));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T toObject(String text, Class<T> clazz) throws ReflectiveOperationException {
        if (null == text) {
            return null;
        } else {
            text = text.trim();
        }

        String type = clazz.getName();
        if (type.equals("java.lang.String")) {
            return (T) text;
        } else if (type.equals("char[]")) {
            return (T) text.toCharArray();
        } else if (type.equals("char")) {
            if (text.length() == 1) {
                return (T) new Character(text.charAt(0));
            } else {
                throw new ReflectiveOperationException("Could not convert " + text + " to a char type!");
            }
        } else if (type.equals("int") || type.equals("java.lang.Integer")) {// int
            return (T) new Integer(text);
        } else if (type.equals("byte[]")) {
            try {
                return (T) text.getBytes(encoding.getCode());
            } catch (UnsupportedEncodingException e) {
                throw new ReflectiveOperationException(e.getMessage());
            }
        } else if (type.equals("float") || type.equals("java.lang.Float")) {
            return (T) new Float(text);
        } else if (type.equals("double") || type.equals("java.lang.Double")) {
            return (T) new Double(text);
        } else if (type.equals("long") || type.equals("Long")) {
            return (T) new Long(text);
        } else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
            return (T) new Boolean(text);
        } else if (type.equals("java.math.BigDecimal")) {
            return (T) new BigDecimal(text);
        } else if (type.equals("java.sql.Timestamp")) {
            return (T) java.sql.Timestamp
                    .from(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).atZone(ZoneId.systemDefault()).toInstant());
        } else if (type.equals("java.sql.Time")) {
            return (T) java.sql.Timestamp.from(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("HHmmss")).atZone(ZoneId.systemDefault()).toInstant());
        } else if (type.equals("java.sql.Date")) {
            return (T) java.sql.Date.from(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).atZone(ZoneId.systemDefault()).toInstant());
        } else if (type.equals("java.util.Date")) {
            return (T) java.util.Date.from(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).atZone(ZoneId.systemDefault()).toInstant());
        } else if (type.equals("byte") || type.equals("java.lang.Byte")) {
            return (T) new Byte(text);
        } else if (type.equals("short") || type.equals("java.lang.Short")) {
            return (T) new Short(text);
        } else if (type.equals("java.math.BigInteger")) {
            return (T) new java.math.BigInteger(text);
        } else if (type.equals("java.util.Calendar")) {
            java.util.Calendar ca = java.util.Calendar.getInstance();
            ca.setTime(
                    java.util.Date.from(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).atZone(ZoneId.systemDefault()).toInstant()));
            return (T) ca;
        } else if (java.sql.Clob.class.isAssignableFrom(clazz)) {
            java.sql.Clob clob = null;
            try {
                clob = ((java.sql.Clob) clazz.newInstance());
                clob.setString(0, text);
            } catch (SQLException e) {
                throw new ReflectiveOperationException(e.getMessage());
            }
            return (T) clob;
        } else if (type.equals("java.sql.Blob")) {
            java.sql.Blob blob = null;
            try {
                blob = ((java.sql.Blob) clazz.newInstance());
                blob.setBytes(0, text.getBytes(encoding.getCode()));
            } catch (SQLException | UnsupportedEncodingException e) {
                throw new ReflectiveOperationException(e.getMessage());
            }
            return (T) blob;
        } else {
            throw new ReflectiveOperationException(
                    "Unsupport type class name:" + clazz.getName() + " class canonical name:" + clazz.getCanonicalName() + " value:" + String.valueOf(text));
        }
    }
}
