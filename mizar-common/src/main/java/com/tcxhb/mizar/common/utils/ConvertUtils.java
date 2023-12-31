package com.tcxhb.mizar.common.utils;


import com.tcxhb.mizar.common.model.MiscResult;
import com.tcxhb.mizar.common.model.PageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/9/20
 */
public class ConvertUtils {
    /**
     * 批量转换
     * @param inputs
     * @param function
     * @param <IN>
     * @param <OUT>
     * @return
     */
    public static <IN, OUT> List<OUT> list(List<IN> inputs, Function<IN, OUT> function) {
        if (inputs == null) {
            return null;
        }
        List list = new ArrayList();
        for (IN in : inputs) {
            OUT out = function.apply(in);
            list.add(out);
        }
        return list;
    }

    /**
     * 批量转换
     * @param inputs
     * @param function
     * @param <IN>
     * @param <OUT>
     * @return
     */
    public static <IN, OUT> PageResponse<OUT> page(PageResponse<IN> inputs, Function<IN, OUT> function) {
        if (inputs == null) {
            return null;
        }
        PageResponse result = new PageResponse<OUT>();
        result.setTotal(inputs.getTotal());
        result.setList(list(inputs.getList(),function));
        return result;
    }

    public static <IN, OUT> MiscResult<PageResponse<OUT>> page(MiscResult<PageResponse<IN>> inputs, Function<IN, OUT> function) {
        if (inputs == null) {
            return null;
        }
        //失败
        if(!inputs.isSuccess()){
            return ResultUtils.copy(inputs,null);
        }
        PageResponse<IN> page = inputs.getData();
        PageResponse result = new PageResponse<OUT>();
        result.setTotal(page.getTotal());
        result.setList(list(page.getList(),function));
        return MiscResult.suc(result);
    }

    public static <IN, OUT> MiscResult<OUT> data(MiscResult<IN> inputs, Function<IN, OUT> function) {
        if (inputs == null) {
            return null;
        }
        MiscResult result = new MiscResult();
        result.setCode(inputs.getCode());
        result.setMsg(inputs.getMsg());
        if(function != null) {
            OUT out = function.apply(inputs.getData());
            result.setData(out);
        }
        return result;
    }

}
