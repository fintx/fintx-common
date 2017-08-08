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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse key value pair from data string
     * 
     * @param map key value pair map
     * @param data to be parse
     * @param encoding the data string encoding
     */
    private static void parseParameters(Map<String, String> map, String data, String encoding) throws UnsupportedEncodingException {
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

    public static void main(String[] args) {

        String text1 =
                "transName=XYQY&Plain=TranAbbr%3DXYQY%7CMerc_id%3D2269620161%7CMercDtTm%3D20170623033818%7CCheckFlag%3D1%7CIdType%3D1%7CIdNo%3D31010219850101234X%7CAccount%3D6217922450500740%7CPayCardName%3D%25B2%25E2%25CA%25D4%25B4%25F3%25C9%25B3%25B7%25A2%25B5%25C4%25D4%25B1%7CMercCode%3D983708160000301%7CMercUrl%3Dhttp%3A%2F%2Fzhangwu.hrbbwx.com%2Freceiver%2FSpdb%2F983708160000301%2FXYQY%2F000000000532&Signature=123037734cd933183acd4588736c885e5ad5720a5aadc1b5a972b7865d3f4369100d2ae49fe9682f89dc9c0f23916a0151b38036ef722555a48b1bc1adf655648f90ab5983f0fd1ddadf22f5de52eefd24f06da7041e3bfca2aec467f7d97326fbc8a3c990d22813eca74ce3922bded61696d8f9d83eebab713566e7d9a73c52";

        Map<String, String> m = new HashMap<String, String>();
        parseParameters(m, text1);
        System.out.println(m.get("Plain").toString());
        System.out.println(m.get("Signature").toString());
        System.out.println(buildQueryData(m));
    }

}
