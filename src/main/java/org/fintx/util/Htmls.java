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

import org.fintx.http.HttpClient;
import org.fintx.http.MediaType;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class Htmls {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            HttpClient.post(new URL("http://124.74.239.32/payment/main"), MediaType.APP_FORM,
                    "Plain=TranAbbr%3DXYQY%7CMerc_id%3D2269620161%7CMercDtTm%3D20170623033818%7CCheckFlag%3D1%7CIdType%3D1%7CIdNo%3D31010219850101234X%7CAccount%3D6217922450500740%7CPayCardName%3D%25B2%25E2%25CA%25D4%25B4%25F3%25C9%25B3%25B7%25A2%25B5%25C4%25D4%25B1%7CMercCode%3D983708160000301%7CMercUrl%3Dhttp%3A%2F%2Fzhangwu.hrbbwx.com%2Freceiver%2FSpdb%2F983708160000301%2FXYQY%2F000000000532&Signature=123037734cd933183acd4588736c885e5ad5720a5aadc1b5a972b7865d3f4369100d2ae49fe9682f89dc9c0f23916a0151b38036ef722555a48b1bc1adf655648f90ab5983f0fd1ddadf22f5de52eefd24f06da7041e3bfca2aec467f7d97326fbc8a3c990d22813eca74ce3922bded61696d8f9d83eebab713566e7d9a73c52");
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        // TODO Auto-generated method stub
        @SuppressWarnings("resource")
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
        webClient.getOptions().setJavaScriptEnabled(true); // 启动JS
        webClient.getOptions().setActiveXNative(true);
        webClient.getOptions().setUseInsecureSSL(true);// 忽略ssl认证
        webClient.getOptions().setCssEnabled(false);// 禁用Css，可避免自动二次请求CSS进行渲染
        webClient.getOptions().setThrowExceptionOnScriptError(false);// 运行错误时，不抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.waitForBackgroundJavaScript(60 * 1000);
        webClient.setJavaScriptTimeout(-1L);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 设置Ajax异步
        // WebResponseData respData=new WebResponseData();
        // WebResponse webResponse=

        WebRequest req = null;
        try {
            req = new WebRequest(new URL("http://124.74.239.32/payment/main"), HttpMethod.POST);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        req.setRequestBody(
                "Plain=TranAbbr%3DXYQY%7CMerc_id%3D2269620161%7CMercDtTm%3D20170623033818%7CCheckFlag%3D1%7CIdType%3D1%7CIdNo%3D31010219850101234X%7CAccount%3D6217922450500740%7CPayCardName%3D%25B2%25E2%25CA%25D4%25B4%25F3%25C9%25B3%25B7%25A2%25B5%25C4%25D4%25B1%7CMercCode%3D983708160000301%7CMercUrl%3Dhttp%3A%2F%2Fzhangwu.hrbbwx.com%2Freceiver%2FSpdb%2F983708160000301%2FXYQY%2F000000000532&transName=XYQY&Signature=123037734cd933183acd4588736c885e5ad5720a5aadc1b5a972b7865d3f4369100d2ae49fe9682f89dc9c0f23916a0151b38036ef722555a48b1bc1adf655648f90ab5983f0fd1ddadf22f5de52eefd24f06da7041e3bfca2aec467f7d97326fbc8a3c990d22813eca74ce3922bded61696d8f9d83eebab713566e7d9a73c52");
        HtmlPage page = null;
        try {
            page = webClient.getPage(req);
            webClient.waitForBackgroundJavaScript(60 * 1000);
            webClient.setJavaScriptTimeout(-1L);
            page.initialize();
        } catch (FailingHttpStatusCodeException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // HtmlForm form=(HtmlForm) page.getElementByName("form2");
        ScriptResult s = page.executeJavaScript(
                "var CookieCsiiNetBank = new Cookie(document, \"promoteFlag\", 24*365*10);CookieCsiiNetBank.load();document.form2.submit();");
        webClient.waitForBackgroundJavaScript(60 * 1000);
        webClient.setJavaScriptTimeout(-1L);
        page = (HtmlPage) s.getNewPage();

        System.err.println(page.getUrl().toString());
        System.err.println(page.getTitleText());
        System.err.println(page.getElementByName("form1"));
        System.err.println(page.getElementByName("form1").asXml());
        System.err.println(page.getElementByName("_savedMap"));
        // System.err.println(page.getHtmlElementById("Password"));
        System.err.println(page.getElementById("pwdShowMsg"));
        System.err.println(page.getElementById("pwdShowMsg").asXml());
        ScriptResult s1 = page.executeJavaScript(
                "var pwdShowMsg=document.getElementById(\"pwdShowMsg\"); var newInput = document.createElement(\"input\");newInput.type=\"hidden\";  newInput.name=\"Password\"; newInput.id=\"Password\";newInput.value=\"888888\";pwdShowMsg.appendChild(newInput); ");
        webClient.waitForBackgroundJavaScript(60 * 1000);
        webClient.setJavaScriptTimeout(-1L);
        page = (HtmlPage) s1.getNewPage();
        System.err.println(page.getHtmlElementById("Password"));
        System.err.println(page.getHtmlElementById("Password").asXml());
        System.err.println(page.getElementByName("LoginButton"));
        try {
            page = page.getElementByName("LoginButton").click();
        } catch (ElementNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.err.println(page.getUrl().toString());
        System.err.println(page.getTitleText());
    }

}
