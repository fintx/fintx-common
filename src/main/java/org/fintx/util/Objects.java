package org.fintx.util;

import org.fintx.lang.Encoding;
import org.fintx.util.convertor.ObjectTextConvertor;
import org.fintx.util.convertor.ObjectXmlConvertor;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBException;

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

    public static <T> T clone(T from) {
        try {
            @SuppressWarnings("unchecked")
            T clone = (T) from.getClass().newInstance();

            BeanCopier copier = getCopier(from.getClass());

            copier.copy(from, clone, new Converter() {
                @Override
                public Object convert(Object pojo, @SuppressWarnings("rawtypes") Class fieldType, Object fieldName) {
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

    public static class Xml {
        private static ObjectXmlConvertor convertor = new ObjectXmlConvertor(null, true, Encoding.UTF_8, false, null);

        /**
         * 
         * @param bean
         * @return String xml of bean
         * @throws JAXBException
         */

        public static <T> String toString(final T bean) throws JAXBException {
            return convertor.toString(bean);
        }

        /**
         * 
         * @param xml xml to be convert to object
         * @param clazz target class type of object
         * @return target object
         * @throws JAXBException
         */

        public static <T> T toObject(final String xml, final Class<T> clazz) throws JAXBException {
            return convertor.toObject(xml, clazz);
        }

        public static ObjectXmlConvertor custom(Map<String, String> namespacePrefixMapper, boolean formatted, Encoding encoding, boolean fragment,
                String headers) {
            return new ObjectXmlConvertor(namespacePrefixMapper, formatted, encoding, fragment, headers);
        }

    }

    public static class Text {
        private static ObjectTextConvertor convertor = new ObjectTextConvertor(Encoding.UTF_8, '|', '=');

        /**
         * 
         * @param bean
         * @return String text of bean
         * @throws ReflectiveOperationException
         */

        public static <T> String toString(final T bean) throws ReflectiveOperationException {
            return convertor.toString(bean);
        }

        /**
         * 
         * @param text text to be convert to object
         * @param clazz target class type of object
         * @return target object
         * @throws ReflectiveOperationException
         */

        public static <T> T toObject(final String text, final Class<T> clazz) throws ReflectiveOperationException {
            return convertor.toObject(text, clazz);
        }

        public static ObjectTextConvertor custom(final Encoding encoding, final Character separator, final Character associator) {
            return new ObjectTextConvertor(encoding, separator, associator);
        }
    }
}
