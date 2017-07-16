package org.fintx.lang;


public interface Message<E> {
    public E getCode();
    public String getDesc();
}
