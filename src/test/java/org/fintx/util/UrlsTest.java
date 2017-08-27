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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class UrlsTest {

    @Test
    public void test() throws Exception {
        Map map = new HashMap();
        Urls.parseParameters(map, null);
        String text1 =
                "transName=XYQY&Plain=TranAbbr%3DXYQY%7CMerc_id%3D2269620161%7CMercDtTm%3D20170623033818%7CCheckFlag%3D1%7CIdType%3D1%7CIdNo%3D31010219850101234X%7CAccount%3D6217922450500740%7CPayCardName%3D%25B2%25E2%25CA%25D4%25B4%25F3%25C9%25B3%25B7%25A2%25B5%25C4%25D4%25B1%7CMercCode%3D983708160000301%7CMercUrl%3Dhttp%3A%2F%2Fzhangwu.hrbbwx.com%2Freceiver%2FSpdb%2F983708160000301%2FXYQY%2F000000000532&Signature=123037734cd933183acd4588736c885e5ad5720a5aadc1b5a972b7865d3f4369100d2ae49fe9682f89dc9c0f23916a0151b38036ef722555a48b1bc1adf655648f90ab5983f0fd1ddadf22f5de52eefd24f06da7041e3bfca2aec467f7d97326fbc8a3c990d22813eca74ce3922bded61696d8f9d83eebab713566e7d9a73c52";

        Map<String, String> m = new HashMap<String, String>();
        Urls.parseParameters(m, text1);
        System.out.println(m.get("Plain").toString());
        System.out.println(m.get("Signature").toString());
        System.out.println(Urls.buildQueryData(m));
        Urls.parseParameters(m, text1, null);
    }

}
