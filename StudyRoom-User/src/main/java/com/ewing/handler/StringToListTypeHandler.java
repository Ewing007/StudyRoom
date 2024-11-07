package com.ewing.handler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: Ewing
 * @Date: 2024-10-14-15:52
 * @Description:
 */
public class StringToListTypeHandler extends BaseTypeHandler<List<String>> {

    private static final String SEPARATOR = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(SEPARATOR, parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String roles = rs.getString(columnName);
        if (roles != null && !roles.isEmpty()) {
            return Arrays.asList(roles.split(SEPARATOR));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String roles = rs.getString(columnIndex);
        if (roles != null && !roles.isEmpty()) {
            return Arrays.asList(roles.split(SEPARATOR));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String roles = cs.getString(columnIndex);
        if (roles != null && !roles.isEmpty()) {
            return Arrays.asList(roles.split(SEPARATOR));
        }
        return null;
    }
}
