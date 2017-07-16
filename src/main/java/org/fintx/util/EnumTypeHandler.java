/**
 * www.beebank.com Inc.
 * Copyright (c) 2017 All Rights Reserved.
 */
package org.fintx.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.fintx.lang.Message;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author admin
 *
 */
public class EnumTypeHandler<E extends Enum<E>&Message<?>> extends BaseTypeHandler<E> {
    private Class<E> type;  
    public EnumTypeHandler(Class<E> type) {  
        if (type == null)  
            throw new IllegalArgumentException("Type argument cannot be null");  
        this.type = type;  
    }  
    private Enum<E> convert(String code) {  
        E[] objs = type.getEnumConstants(); 
        for (E e : objs) {  
            if (e.getCode().equals(code)) {  
                return e;  
            }  
        }  
        return null;  
    }  
    
    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#setNonNullParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.ResultSet, int)
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.BaseTypeHandler#getNullableResult(java.sql.CallableStatement, int)
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
