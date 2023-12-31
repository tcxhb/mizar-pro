package com.tcxhb.mizar.dao.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/9/30
 */
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> stringListMap, JdbcType jdbcType) throws SQLException {
        String str = toJson(stringListMap);
        ps.setString(i, str);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String s) throws SQLException {
        return toBeans(rs.getString(s));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int i) throws SQLException {
        return toBeans(rs.getString(i));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int i) throws SQLException {
        return toBeans(cs.getString(i));
    }

    public static List<String> toBeans(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String[] sp = str.split(",");
        return Arrays.asList(sp);
    }

    public static String toJson(List<String> stringListMap) {
        return StringUtils.join(stringListMap, ",");
    }
}
