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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.fintx.lang.Message;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
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
