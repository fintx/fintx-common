package org.fintx.http;

import org.fintx.lang.Codeable;

public enum MediaType implements Codeable<String> {
    APP_XML("application/xml"), APP_JSON("application/json"), APP_FORM("application/x-www-form-urlencoded"), TEXT_PLAIN("text/plain"), TEXT_HTML("text/html"),
    TEXT_XML("text/xml"), APP_OCTETSTREAM("application/octet-stream");
    private String code;

    private MediaType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
