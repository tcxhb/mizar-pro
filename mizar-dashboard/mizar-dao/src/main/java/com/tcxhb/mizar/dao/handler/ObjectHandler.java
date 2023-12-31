package com.tcxhb.mizar.dao.handler;

import com.tcxhb.mizar.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/9/30
 */
public abstract class ObjectHandler<T> extends BaseTypeHandler<T> {

    abstract public Class<T> getclass();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T object, JdbcType jdbcType) throws SQLException {
        String str = toJson(object);
        ps.setString(i, str);
    }

    @Override
    public T getNullableResult(ResultSet rs, String s) throws SQLException {
        return toBeans(rs.getString(s));
    }

    @Override
    public T getNullableResult(ResultSet rs, int i) throws SQLException {
        return toBeans(rs.getString(i));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int i) throws SQLException {
        return toBeans(cs.getString(i));
    }

    public T toBeans(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return JsonUtils.toBean(str, getclass());
    }

    public String toJson(T object) {
        return JsonUtils.toJson(object);
    }
}
