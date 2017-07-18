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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.*;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.internal.bind.v2.WellKnownNamespace;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */

public final class ObjectXmlUtil {

    private ObjectXmlUtil() {
        throw new AssertionError("No ObjectXmlUtil instances for you!");
    }
    
    private static final Map<String,String> namespacePrefixMap=new HashMap<String,String>();
    static{
        //init namespace and prefix mapping
        namespacePrefixMap.put("http://schemas.xmlsoap.org/soap/envelope/", "soapenv");
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
            //reslove the namespace prefix problem
            NamespacePrefixMapper mapper=new DefaultNamespacePrefixMapper(namespacePrefixMap);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
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
    public static String toXml(final Object obj, final Encoding encoding, final boolean formatted, final String xmlHeaders) throws JAXBException {
        JAXBContext cachedContext = getCachedContext(obj.getClass());
        Marshaller marshaller = cachedContext.createMarshaller();
        try {
            // 编码格式
            //marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding.getValue());
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            // 是否省略xml头声明信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            //set the new xml headers 添加xml头声明信息
            marshaller.setProperty("com.sun.xml.bind.xmlHeaders",xmlHeaders);
            //reslove the namespace prefix problem
            NamespacePrefixMapper mapper=new DefaultNamespacePrefixMapper(namespacePrefixMap);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
           
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
    
    private static final class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {
        private static final String[] EMPTY_STRING = new String[0];

        private final Map<String, String> nspref;
        private String[] nsctxt = EMPTY_STRING;

        public DefaultNamespacePrefixMapper(Map<String, String> nspref) {
            this.nspref = nspref;
        }

        public String getPreferredPrefix(String namespaceUri, 
                                         String suggestion, 
                                         boolean requirePrefix) {
            String prefix = nspref.get(namespaceUri);
            if (prefix != null) {
                return prefix;
            }
            if( namespaceUri.equals(WellKnownNamespace.XML_SCHEMA_INSTANCE) )
                return "xsi";
            if( namespaceUri.equals(WellKnownNamespace.XML_SCHEMA) )
                return "xs";
            if( namespaceUri.equals(WellKnownNamespace.XML_MIME_URI) )
                return "xmime";
            return suggestion;
        }

        public void setContextualNamespace(String[] contextualNamespaceDecls) {
            this.nsctxt = contextualNamespaceDecls;
        }

        public String[] getContextualNamespaceDecls() {
            return nsctxt;
        }
        
        @Override
        public String[] getPreDeclaredNamespaceUris() {
            return new String[] { 
                XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI
            };
        }
        
        
    }
}