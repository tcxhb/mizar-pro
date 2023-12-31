package com.tcxhb.mizar.common.utils;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/7
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.tcxhb.mizar.common.model.MiscResult;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/10/11
 */
public class JsonUtils {

    public static JSONObject toJson(String str) {
        return JSON.parseObject(str);
    }
    public static String toJson(Object object) {
        if(object == null){
            return null;
        }
        if(object instanceof String){
            return (String) object;
        }
        return JSON.toJSONString(object);
    }

    public static JSONObject beanToJson(Object object) {
        String str = toJson(object);
        return toJson(str);
    }

    public static <T> T toBean(String str, Class<T> c) {
        if(str == null){
            return null;
        }
        return JSON.parseObject(str, c);
    }

    public static <T> List<T> toBeans(String str, Class<T> c) {
        if(str == null){
            return null;
        }
        return JSONArray.parseArray(str,c);
    }

    /**
     * 复合对象转化
     * @param str
     * @param c
     * @param <T>
     * @return
     */
    public static <T> MiscResult<T> toMiscBean(String str, Class<T> c){
        Type type = buildType(MiscResult.class, c);
        MiscResult<T> response = JSONObject.parseObject(str,  type);
        return response;
    }

    public static <M,T>MiscResult<T> toBean(String str,Class<M> m,Class<T> c){
        if(StringUtils.isBlank(str)){
            return null;
        }
        Type type = buildType(m, c);
        return JSONObject.parseObject(str,  type);
    }

    public static <M,T>MiscResult<T> toBean(String str,Type type){
        if(StringUtils.isBlank(str)){
            return null;
        }
        return JSONObject.parseObject(str,  type);
    }

    public static Type buildType(Type... types) {
        if (types == null && types.length == 0) {
            return null;
        }

        ParameterizedTypeImpl beforeType = null;
        if (types.length == 1) {
            beforeType = new ParameterizedTypeImpl(null, null, types[0]);
            return beforeType;
        }

        for (int i = types.length - 1; i > 0; i--) {
            beforeType = new ParameterizedTypeImpl(
                    new Type[]{beforeType == null ? types[i] : beforeType}, null, types[i - 1]);
        }
        return beforeType;
    }
}
