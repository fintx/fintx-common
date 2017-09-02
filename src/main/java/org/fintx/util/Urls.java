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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class Urls {
    /**
     * Parse key value pair from data string
     * 
     * @param map key value pair map
     * @param data to be parse
     */
    public static void parseParameters(Map<String, String> map, String data) {
        try {
            parseParameters(map, data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse key value pair from data string
     * 
     * @param map key value pair map
     * @param data to be parse
     * @param encoding the data string encoding
     * @throws UnsupportedEncodingException when encoding is unsupported
     */
    public static void parseParameters(Map<String, String> map, String data, String encoding) throws UnsupportedEncodingException {
        if ((data == null) || (data.length() <= 0)) {
            return;
        }

        byte[] bytes = null;
        try {
            if (encoding == null)
                bytes = data.getBytes();
            else
                bytes = data.getBytes(encoding);
        } catch (UnsupportedEncodingException uee) {
        }
        parseParameters(map, bytes, encoding);
    }

    /**
     * Parse key value pair from data string
     * 
     * @param map key value pair map
     * @param data to be parse
     * @param encoding the data string encoding
     */
    private static void parseParameters(Map<String, String> map, byte[] data, String encoding) throws UnsupportedEncodingException {
        if ((data != null) && (data.length > 0)) {
            int ix = 0;
            int ox = 0;
            String key = null;
            String value = null;
            while (ix < data.length) {
                byte c = data[(ix++)];
                switch ((char) c) {
                    case '&':
                        value = new String(data, 0, ox, encoding);
                        if (key != null) {
                            putMapEntry(map, key, value);
                            key = null;
                        }
                        ox = 0;
                        break;
                    case '=':
                        if (key == null) {
                            key = new String(data, 0, ox, encoding);
                            ox = 0;
                        } else {
                            data[(ox++)] = c;
                        }
                        break;
                    case '+':
                        data[(ox++)] = 32;
                        break;
                    case '%':
                        data[(ox++)] = (byte) ((convertHexDigit(data[(ix++)]) << 4) + convertHexDigit(data[(ix++)]));

                        break;
                    default:
                        data[(ox++)] = c;
                }
            }

            if (key != null) {
                value = new String(data, 0, ox, encoding);
                putMapEntry(map, key, value);
            }
        }
    }

    /**
     * Put name value pair to map
     * 
     * @param map key value pair map
     * @param data name the key of pair
     * @param value the value of the pair
     */
    private static void putMapEntry(Map<String, String> map, String name, String value) {
        // String[] newValues = null;
        // String[] oldValues = (String[]) (String[]) map.get(name);
        // if (oldValues == null) {
        // newValues = new String[1];
        // newValues[0] = value;
        // } else {
        // newValues = new String[oldValues.length + 1];
        // System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
        // newValues[oldValues.length] = value;
        // }
        map.put(name, value);
    }

    private static byte convertHexDigit(byte b) {
        if ((b >= 48) && (b <= 57))
            return (byte) (b - 48);
        if ((b >= 97) && (b <= 102))
            return (byte) (b - 97 + 10);
        if ((b >= 65) && (b <= 70))
            return (byte) (b - 65 + 10);
        return 0;
    }

    /**
     * Build query string in url from map
     * 
     * @param map key value pair map
     * @return the query string in url
     */
    @SuppressWarnings("deprecation")
    public static String buildQueryData(Map<String, String> map) {
        StringBuilder sb = new StringBuilder(512);
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                sb.append("&");
            } else {
                first = false;
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue()));
        }
        return sb.toString();

    }


}
