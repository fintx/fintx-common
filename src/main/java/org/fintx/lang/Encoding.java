package org.fintx.lang;

public enum Encoding implements Codeable<String> {
    UTF_8("UTF-8"), GBK("GBK"), GB18030("GB18030");
    private String code;

    private Encoding(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        // TODO Auto-generated method stub
        return code;
    }
}
