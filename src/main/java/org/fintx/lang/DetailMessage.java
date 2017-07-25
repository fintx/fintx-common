package org.fintx.lang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailMessage<T> implements Message<T> {
    private T code;
    private String desc;
    private String detail;
    public DetailMessage(Message<T> msg) {
        this.code=msg.getCode();
        this.desc=msg.getDesc();
    }

    @Override
    public T getCode() {
        // TODO Auto-generated method stub
        return code;
    }

    @Override
    public String getDesc() {
        // TODO Auto-generated method stub
        return desc;
    }

}
