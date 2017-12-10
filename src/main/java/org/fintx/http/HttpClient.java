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

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public final class HttpClient {
    private HttpClient() {
        throw new AssertionError("No HttpClient instances for you!");
    }
    private static final ThreadLocal<OkHttpClient> local = new ThreadLocal<OkHttpClient>();

    private static final Interceptor networkInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String arg0) {
            System.out.println(arg0);

        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);;

    public static void custom(final KeyStore trustStore, final KeyStore keyStore, final String keyPass) {
        local.set(getClient(trustStore, keyStore, keyPass));
    }

    @SuppressWarnings("deprecation")
    private static OkHttpClient getClient(final KeyStore trustStore, final KeyStore keyStore, final String keyPass) {
        if (null != keyStore && null == keyPass) {
            throw new IllegalArgumentException("keyPass for keyStore could not be null!");
        }
        // this.keyStore = keyStore;
        // this.keyPass = keyPass;
        // this.trustStore = trustStore;
        TrustManager[] trustManagers = null;
        if (null != trustStore) {
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(trustStore);
                trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }
                // trustManager = (X509TrustManager) trustManagers[0];
            } catch (Exception e) {
                throw new RuntimeException("Can not get trustManager!", e);
            }
        } else {
            trustManagers = new X509TrustManager[] { new X509TrustManager() {
                private final Set<X509Certificate> acceptedIssuers_ = new HashSet<>();

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    // Everyone is trusted!
                    acceptedIssuers_.addAll(Arrays.asList(chain));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    // Everyone is trusted!
                    acceptedIssuers_.addAll(Arrays.asList(chain));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    // it seems to be OK for Java <= 6 to return an empty array but not for Java 7 (at least 1.7.0_04-b20):
                    // requesting an URL with a valid certificate (working without WebClient.setUseInsecureSSL(true)) throws a
                    // javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
                    // when the array returned here is empty
                    if (acceptedIssuers_.isEmpty()) {
                        return new X509Certificate[0];
                    }
                    return acceptedIssuers_.toArray(new X509Certificate[acceptedIssuers_.size()]);
                }
            } };
        }
        KeyManager[] keyManagers = null;
        if (null != keyStore) {
            try {
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, keyPass.toCharArray());
                keyManagers = keyManagerFactory.getKeyManagers();
                if (keyManagers.length != 1 || !(keyManagers[0] instanceof X509KeyManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(keyManagers));
                }
                // keyManager = (X509KeyManager) keyManagers[0];
            } catch (Exception e) {
                throw new RuntimeException("Can not get keyManager!", e);
            }
        }

        SSLSocketFactory sslSocketFactory = null;
        if (null != keyManagers || null != trustManagers) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagers, trustManagers, null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (Exception e) {
                throw new RuntimeException("There is no keyManager!", e);
            }
        }

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (null != sslSocketFactory) {
            builder.sslSocketFactory(sslSocketFactory);
        }

        return builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).retryOnConnectionFailure(true).connectTimeout(5, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).build();

    }

    // private KeyStore trustStore = null;
    // private KeyStore keyStore = null;
    // private String keyPass = null;
    private static final OkHttpClient defaultClient = getClient(null, null, null);

    public static void print(boolean printable) {
        OkHttpClient client = local.get();
        //Do not support globe log intercepter
        if (null != client) {
            if (printable) {
                if (-1 == client.networkInterceptors().indexOf(networkInterceptor)) {
                    client = client.newBuilder().addNetworkInterceptor(networkInterceptor).build();
                }
            } else {
                // not supported operation
                // client.networkInterceptors().remove(networkInterceptor);
            }
        }

    }

    // public HttpClientBase trustStore(KeyStore trustStore) {
    // this.trustStore = trustStore;
    // return new HttpClientBase(trustStore, keyStore, keyPass);
    // }

    // public HttpClientBase keyStore(KeyStore keyStore) {
    // this.keyStore = keyStore;
    // return new HttpClientBase(trustStore, keyStore, keyPass);
    // }

    // public HttpClientBase keyPass(String keyPass) {
    // this.keyPass = keyPass;
    // return new HttpClientBase(trustStore, keyStore, keyPass);
    // }

    // public Pair<Map<String, String>, String> getHeadersAndBody(URL url) throws IOException, IllegalStateException {
    // Request request = new Request.Builder().url(url).build();
    //
    // Response response = client.newCall(request).execute();
    // if (!response.isSuccessful())
    // throw new IllegalStateException("Unexpected code " + response);
    //
    // // System.out.println("Server: " + response.header("Server"));
    // // System.out.println("Date: " + response.header("Date"));
    // // System.out.println("Vary: " + response.headers("Vary"));
    // Map<String, String> headers = new HashMap<String, String>();
    // Set<String> names = response.headers().names();
    // for (String name : names) {
    // headers.put(name, response.headers().get(name));
    // }
    // String body = response.body().toString();
    // response.close();
    // return new Pair<Map<String, String>, String>(headers, body);
    // }

    public static String get(URL url) throws IOException, IllegalStateException {

        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        Request request = new Request.Builder().url(url).get().build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        // Headers responseHeaders = response.headers();
        // for (int i = 0; i < responseHeaders.size(); i++) {
        // System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        // }

        String resp = response.body().string();
        response.close();
        return resp;
    }

    public static InputStream getFile(URL url) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        Request request = new Request.Builder().url(url).get().build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        // Headers responseHeaders = response.headers();
        // for (int i = 0; i < responseHeaders.size(); i++) {
        // System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        // }

        return response.body().byteStream();
    }

    public static String put(URL url, MediaType type, String body) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        if (null == body) {
            throw new NullPointerException("body could not be null!");
        }
        okhttp3.MediaType mediaType = null;
        if (null != type) {
            okhttp3.MediaType.parse(type.getCode());
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        Request request = new Request.Builder().url(url).put(RequestBody.create(mediaType, body)).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        String resp = response.body().string();
        response.close();
        return resp;
    }

    public static String delete(URL url, MediaType type, @Nullable String body) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        okhttp3.MediaType mediaType = null;
        if (null != type) {
            okhttp3.MediaType.parse(type.getCode());
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        RequestBody requestBody = null;
        if (null != body) {
            RequestBody.create(mediaType, body);
        }

        Request request = new Request.Builder().url(url).delete(requestBody).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        String resp = response.body().string();
        response.close();
        return resp;
    }

    public static String post(URL url, MediaType type, String body) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        if (null == body) {
            throw new NullPointerException("body could not be null!");
        }
        okhttp3.MediaType mediaType = null;
        if (null != type) {
            okhttp3.MediaType.parse(type.getCode());
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        Request request = new Request.Builder().url(url).post(RequestBody.create(mediaType, body)).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        String resp = response.body().string();
        response.close();
        return resp;
    }

    /*
     * post file using form
     */
    public static String post(URL url, MediaType type, File file) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        if (null == file || 0 == file.length()) {
            throw new IllegalArgumentException("file could not be null or 0 length!");
        }

        okhttp3.MediaType mediaType = null;
        if (null != type) {
            okhttp3.MediaType.parse(type.getCode());
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        Request request = new Request.Builder().url(url).post(RequestBody.create(mediaType, file)).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        String resp = response.body().string();
        response.close();
        return resp;
    }

    public static String post(URL url, Map<String, String> formParams) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        if (null == formParams || 0 == formParams.size()) {
            throw new IllegalArgumentException("formParams could not be null or empty!");
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);

        String resp = response.body().string();
        response.close();
        return resp;
    }

    public static String post(URL url, Map<String, String> formParams, Map<String, File> formFiles) throws IOException, IllegalStateException {
        if (null == url) {
            throw new NullPointerException("url could not be null!");
        }

        if ((null == formParams || 0 == formParams.size()) && (null == formFiles || 0 == formFiles.size())) {
            throw new IllegalArgumentException("Either formParams or formFiles could not be null or empty!");
        }

        OkHttpClient client = local.get();
        if (null == client) {
            client = defaultClient;
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE);

        if (null != formParams && 0 != formParams.size()) {
            for (Map.Entry<String, String> entry : formParams.entrySet()) {
                builder = builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        if (null != formFiles && 0 != formFiles.size()) {
            for (Map.Entry<String, File> entry : formFiles.entrySet()) {
                RequestBody fileBody = RequestBody.create(okhttp3.MediaType.parse(MediaType.APP_OCTETSTREAM.getCode()), entry.getValue());
                if (entry.getKey() == null) {
                    throw new NullPointerException("Key of form files should not be null!");
                }
                builder.addFormDataPart(entry.getKey(), entry.getValue().getName(), fileBody);
            }

        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IllegalStateException("Unexpected code " + response);
        String resp = response.body().string();
        response.close();
        return resp;
    }

    // TODO https://github.com/square/okhttp/wiki/Recipes
    // public class HttpLogger implements HttpLoggingInterceptor.Logger {
    // @Override
    // public void log(String message) {
    // System.err.println("HttpLogInfo:\n"+ message);
    // }
    // }

    // public class Builder {
    // private KeyStore trustStore = null;
    // private KeyStore keyStore = null;
    // private String keyPass = null;
    //
    // public Builder trustStore(KeyStore trustStore) {
    // this.trustStore = trustStore;
    // return this;
    // }
    //
    // public Builder keyStore(KeyStore keyStore) {
    // this.keyStore = keyStore;
    // return this;
    // }
    //
    // public Builder keyPass(String keyPass) {
    // this.keyPass = keyPass;
    // return this;
    // }
    //
    // public HttpClientBase build() {
    //
    // return new HttpClientBase(trustStore, keyStore, keyPass);
    // }
    // }
}
