package org.fintx.http;

import org.fintx.lang.Codeable;

public enum MediaType implements Codeable<String> {
    APP_XML("application/xml"), APP_JSON("application/json"),APP_FORM("application/x-www-form-urlencoded"), TEXT_PLAIN("text/plain"), TEXT_HTML("text/html"), TEXT_XML("text/xml");
    private String value;

    private MediaType(String value) {
        this.value = value;
    }

    public String getCode() {
        return value;
    }
}
