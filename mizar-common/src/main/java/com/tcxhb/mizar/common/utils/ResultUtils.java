package com.tcxhb.mizar.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.tcxhb.mizar.common.model.MiscResult;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/2/27
 */
public class ResultUtils {

    public static <IN, OUT> MiscResult<OUT> copy(MiscResult<IN> inputs, Function<IN, OUT> function) {
        MiscResult result = new MiscResult();
        result.setCode(inputs.getCode());
        result.setMsg(inputs.getMsg());
        result.setSuccess(inputs.isSuccess());
        //结果转换
        IN in = inputs.getData();
        if (in != null && function != null) {
            result.setData(function.apply(in));
        }
        return result;
    }
    /**
     *
     * 用于反序列化 Result的data 为List
     * @param str
     * @param actualArguments
     * @param <T>
     * @return
     */
    public static <T> T toListResult(String str, Type actualArguments) {
        ParameterizedTypeImpl inner = new ParameterizedTypeImpl(new Type[]{actualArguments}, null, List.class);
        ParameterizedTypeImpl outer = new ParameterizedTypeImpl(new Type[]{inner}, null, MiscResult.class);
        return JSONObject.parseObject(str, outer);
    }
}
