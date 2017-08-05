package org.fintx.util;


import org.fintx.lang.Encoding;
import org.fintx.util.convertor.ObjectStringConvertor;
import org.fintx.util.convertor.ObjectTextConvertor;
import org.fintx.util.convertor.ObjectXmlConvertor;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.apache.commons.lang3.CharSet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Objects {
    private Objects() {
    }

    // /**
    // *
    // * @Title: deepClone
    // * @Description: (深度克隆方法)
    // * @param src 待克隆对象
    // * @return
    // */
    // // TODO BAO性能不佳 要么用池，要么不用
    // public static Object deepClone(Object src) {
    // Object o = null;
    // try {
    // if (src != null) {
    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // ObjectOutputStream oos = new ObjectOutputStream(baos);
    // oos.writeObject(src);
    // oos.flush();
    // oos.close();
    // ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    // ObjectInputStream ois = new ObjectInputStream(bais);
    // o = ois.readObject();
    // ois.close();
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // } catch (ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // return o;
    // }

    private static ConcurrentMap<Class<?>, BeanCopier> beanCopiers = new ConcurrentHashMap<Class<?>, BeanCopier>();

    public static XmlConvertorBuilder xml() {
        return new XmlConvertorBuilder();
    }
    
    public static TextConvertorBuilder text() {
        return new TextConvertorBuilder();
    }
    
    
    public static <T> T clone(T from) {
        try {
            T clone = (T) from.getClass().newInstance();

            BeanCopier copier = getCopier(from.getClass());

            copier.copy(from, clone, new Converter() {
                @Override
                public Object convert(Object pojo, Class fieldType, Object fieldName) {
                    return _clone(pojo);
                }
            });

            return clone;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object _clone(Object pojo) {
        if (pojo == null) {
            return null;
        } else {
            if (pojo.getClass().isArray() && !pojo.getClass().getComponentType().equals(byte.class)) {
                int length = Array.getLength(pojo);
                Object clone = Array.newInstance(pojo.getClass().getComponentType(), length);
                for (int i = 0; i < length; i++) {
                    Array.set(clone, i, _clone(Array.get(pojo, i)));
                }
                return clone;
            } else {
                return pojo;
            }
        }
    }

    private static BeanCopier getCopier(Class<?> clz) {
        if (beanCopiers.containsKey(clz))
            return beanCopiers.get(clz);
        beanCopiers.putIfAbsent(clz, BeanCopier.create(clz, clz, true));
        return beanCopiers.get(clz);

    }

    public static class XmlConvertorBuilder {
        
        private boolean formatted=true;
        private Encoding encoding=Encoding.UTF_8;
        private boolean fragment=false;
        private String headers=null;
        private Map<String,String> namespacePrefixMapper=null;
        public XmlConvertorBuilder formatted(boolean formatted) {
            this.formatted=formatted;
            return this;
        }
        
        public XmlConvertorBuilder encoding(Encoding encoding) {
            this.encoding=encoding;
            return this;
        }
        public XmlConvertorBuilder fragment(boolean fragment) {
            this.fragment=fragment;
            return this;
        }
        public XmlConvertorBuilder headers(String headers) {
            this.headers=headers;
            return this;
        }
        
        public ObjectStringConvertor build() {
            ObjectStringConvertor convertor=new ObjectXmlConvertor(namespacePrefixMapper,formatted, encoding, fragment, headers);
            return convertor;
        }

    }

    public static class TextConvertorBuilder {
        private  Encoding encoding=Encoding.UTF_8;
        private  Character separator='|';
        private Character associator='=';
        public TextConvertorBuilder encoding(Encoding encoding) {
            this.encoding=encoding;
            return this;
        }
        
        public TextConvertorBuilder separator(Character separator) {
            this.separator=separator;
            return this;
        }
        
        public TextConvertorBuilder associator(Character associator) {
            this.associator=associator;
            return this;
        }
        public ObjectStringConvertor build() {
            ObjectStringConvertor convertor=new ObjectTextConvertor(encoding, separator, associator);
            return convertor;
        }
    }
}
