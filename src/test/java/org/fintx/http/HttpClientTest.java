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
package org.fintx.http;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class HttpClientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void test() {
        try {
            HttpClient.print(true);
            System.out.println(HttpClient.get(new URL("http://www.baidu.com")));
            HttpClient.print(false);
            System.out.println(HttpClient.get(new URL("https://www.baidu.com")));
            //System.out.println(HttpClient.post(new URL("https://www.baidu.com"), MediaType.APP_FORM, "baidu"));
            HttpClientBase httpClient=HttpClient.custom(null, null, null);
            httpClient.get(new URL("https://www.baidu.com"));
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testException1() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.get(null);
    }
    @Test
    public void testException2() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.put(null,null,null);
    }
    @Test
    public void testException3() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.post(null,null,(String)null);
    }
    @Test
    public void testException4() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.post(null,null,(File)null);
    }
    @Test
    public void testException5() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.post(null,null,(Map)null);
    }
    
    @Test
    public void testException6() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.post(null,null);
    }
    @Test
    public void testException7() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.delete(null,null,null);
    }
    @Test
    public void testException8() throws IllegalStateException, IOException {
        thrown.expect(NullPointerException.class);
        HttpClient.getFile(null);
    }

}
