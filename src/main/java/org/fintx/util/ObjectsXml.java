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

import org.fintx.lang.Encoding;
import org.fintx.util.convertor.ObjectStringConvertor;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.v2.WellKnownNamespace;
import lombok.AllArgsConstructor;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
@SuppressWarnings("restriction")
@AllArgsConstructor
public final class ObjectsXml implements ObjectStringConvertor {

    private Map<String, String> namespacePrefixMapper;
    private boolean formatted;
    private Encoding encoding;
    private boolean fragment;
    private String headers;

    /**
     * JAXBContext is thread safe, Marshaller(Unmarshaller) and Validator are not safe for threads. So, we cached the JAXBContext for reusing.
     */
    private final Map<Class<?>, JAXBContext> cachedContexts = new HashMap<Class<?>, JAXBContext>();

    // TODO Set up the Marshaller and Unmarshaller pool for performance improve.

    /**
     * Get the JAXBContext in cache, create first and put into cache when there is no context for the class in cache.
     */
    private JAXBContext getCachedContext(Class<?> clazz) throws JAXBException {
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
    public String toString(final Object obj) throws JAXBException {
        JAXBContext cachedContext = getCachedContext(obj.getClass());
        Marshaller marshaller = cachedContext.createMarshaller();
        StringWriter writer = new StringWriter();
        try {
            // 编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding.getCode());
            // 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            if (Strings.isEmpty(headers)) {
                // 是否省略xml头声明信息
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
            } else {
                // set the new xml headers 添加xml头声明信息
                //marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", headers);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
                writer.write(headers);
            }

            // reslove the namespace prefix problem
            if(null!=namespacePrefixMapper) {
                NamespacePrefixMapper mapper = new DefaultNamespacePrefixMapper(namespacePrefixMapper);
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
            }
           
        } catch (PropertyException e) {
            throw new JAXBException(e);
        } catch (IllegalArgumentException e) {
            throw new JAXBException(e);
        }
        
        marshaller.marshal(obj, writer);
        return writer.toString();

    }

    /**
     * Convert from XML string to object
     */
    @SuppressWarnings("unchecked")
    public <T> T toObject(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext cachedContext = getCachedContext(clazz);
        Unmarshaller unmarshaller = cachedContext.createUnmarshaller();
        // unmarshaller.setAdapter(new NullableEmptyStringAdapter());
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

    private final class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {
        private final String[] EMPTY_STRING = new String[0];

        private final Map<String, String> nspref = new HashMap<String, String>();
        private String[] nsctxt = EMPTY_STRING;

        public DefaultNamespacePrefixMapper(Map<String, String> nspref) {
            if (null != nspref) {
                this.nspref.putAll(nspref);
            }
        }

        @SuppressWarnings("deprecation")
        public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
            String prefix = nspref.get(namespaceUri);
            if (prefix != null) {
                return prefix;
            }
            if (namespaceUri.equals(WellKnownNamespace.XML_SCHEMA_INSTANCE))
                return "xsi";
            if (namespaceUri.equals(WellKnownNamespace.XML_SCHEMA))
                return "xs";
            if (namespaceUri.equals(WellKnownNamespace.XML_MIME_URI))
                return "xmime";
            return suggestion;
        }

        @SuppressWarnings("unused")
        public void setContextualNamespace(final String[] contextualNamespaceDecls) {
            this.nsctxt = contextualNamespaceDecls;
        }

        public String[] getContextualNamespaceDecls() {
            return nsctxt;
        }

        @Override
        public String[] getPreDeclaredNamespaceUris() {
            return new String[] { XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI };
        }

    }
}