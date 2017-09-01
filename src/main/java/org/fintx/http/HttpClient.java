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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public final class HttpClient {
    private HttpClient() {
        throw new AssertionError("No HttpClient instances for you!");
    }

    static private HttpClientBase httpClientBase = new HttpClientBase(null, null, null);

    public static HttpClientBase custom(final KeyStore trustStore, final KeyStore keyStore, final String keyPass) {
        return new HttpClientBase(trustStore, keyStore, keyPass);
    }

    public static void print(boolean printable) {
        httpClientBase.print(printable);
    }

    public static String get(URL url) throws IOException, IllegalStateException {
        return httpClientBase.get(url);
    }

    public static InputStream getFile(URL url) throws IOException, IllegalStateException {
        return httpClientBase.getFile(url);
    }

    public static String put(URL url, MediaType type, String body) throws IOException, IllegalStateException {
        return httpClientBase.put(url, type, body);
    }

    public static String delete(URL url, MediaType type, @Nullable String body) throws IOException, IllegalStateException {
        return httpClientBase.delete(url, type, body);
    }

    public static String post(URL url, MediaType type, String body) throws IOException, IllegalStateException {
        return httpClientBase.post(url, type, body);
    }

    /*
     * post file using form
     */
    public static String post(URL url, MediaType type, File file) throws IOException, IllegalStateException {
        return httpClientBase.post(url, type, file);
    }

    public static String post(URL url, Map<String, String> formParams) throws IOException, IllegalStateException {
        return httpClientBase.post(url, formParams);
    }

    @SuppressWarnings("null")
    public static String post(URL url, Map<String, String> formParams, Map<String, File> formFiles) throws IOException, IllegalStateException {
        return httpClientBase.post(url, formParams, formFiles);
    }

}
