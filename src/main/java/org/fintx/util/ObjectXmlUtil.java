package org.fintx.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.*;

/**
 * Utility for java and XML banding
 * 
 * @author admin
 *
 */

public final class ObjectXmlUtil {

    private ObjectXmlUtil() {
        throw new AssertionError("No ObjectXmlUtil instances for you!");
    }

    /**
     * JAXBContext is thread safe, Marshaller(Unmarshaller) and Validator are not safe for threads. So, we cached the
     * JAXBContext for reusing.
     */
    static private final Map<Class<?>, JAXBContext> cachedContexts = new HashMap<Class<?>, JAXBContext>();

    // TODO Set up the Marshaller and Unmarshaller pool for performance improve.

    /**
     * Get the JAXBContext in cache, create first and put into cache when there is no context for the class in cache.
     */
    static private JAXBContext getCachedContext(Class<?> clazz) throws JAXBException {
        if (cachedContexts.get(clazz) == null) {
            synchronized (cachedContexts) {
                if (cachedContexts.get(clazz) == null) {
                    cachedContexts.put(clazz, JAXBContext.newInstance(clazz));
                }
            }
        }
        return cachedContexts.get(clazz);
    }

    /**
     * Convert from object to XML string
     *
     */
    public static String toXml(final Object obj, final Encoding encoding, final boolean formatted, final boolean fragment) throws JAXBException {
        JAXBContext cachedContext = getCachedContext(obj.getClass());
        Marshaller marshaller = cachedContext.createMarshaller();
        try {
            // 编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding.getValue());
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            // 是否省略xml头声明信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
        } catch (PropertyException e) {
            throw new JAXBException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JAXBException(e.getMessage());
        }
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();

    }
    
    /**
     * Convert from object to XML string
     *
     */
    public static String toXml(final Object obj, final Encoding encoding, final boolean formatted, final String headers) throws JAXBException {
        JAXBContext cachedContext = getCachedContext(obj.getClass());
        Marshaller marshaller = cachedContext.createMarshaller();
        try {
            // 编码格式
            //marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding.getValue());
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            // 是否省略xml头声明信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
           
        } catch (PropertyException e) {
            throw new JAXBException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JAXBException(e.getMessage());
        }
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return headers+"\r\n"+writer.toString();

    }

    /**
     * Convert from object to XML string Using UTF8 encoding, not formatted, ignore XML header annotation by default.
     *
     */
    public static String toXml(Object obj) throws JAXBException {
        return toXml(obj, Encoding.UTF_8, true, true);
    }

    /**
     * Convert from XML string to object
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObj(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext cachedContext = getCachedContext(clazz);
        Unmarshaller unmarshaller = cachedContext.createUnmarshaller();
        //unmarshaller.setAdapter(new NullableEmptyStringAdapter());
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

    /**
     * character encoding using in XML
     */
    public enum Encoding {
        UTF_8("UTF-8"),GBK("GBK"),GB18030("GB18030");
        private String value;

        private Encoding(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }
}