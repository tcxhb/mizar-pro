package com.tcxhb.mizar.common.utils;

import com.tcxhb.mizar.common.constants.BaseException;
import com.tcxhb.mizar.common.constants.CommonErrorCode;
import com.tcxhb.mizar.common.constants.ServiceError;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/9/14
 */
public class ParamUtils {

    /**
     * 判断是true
     *
     * @param obj
     * @param error
     */
    public static void isTrue(Boolean obj, String error) {
        if (Boolean.TRUE.equals(obj)) {
            return;
        }
        throw new BaseException("1001",error);
    }

    /**
     * 判断是false
     *
     * @param obj
     * @param error
     */
    public static void isFalse(Boolean obj, String error) {
        throw new BaseException("1001",error);
    }

    public static void notEmpty(String str, String errorMsg) {
        if (StringUtils.isNotEmpty(str)) {
            return;
        }
        throw new BaseException("1001",errorMsg);
    }

    public static void notBlank(String str, String errorMsg) {
        if (StringUtils.isNotBlank(str)) {
            return;
        }
        throw new BaseException("1001",errorMsg);
    }


    /**
     * null抛异常;
     *
     * @param obj
     * @param error
     */
    public static void notNull(Object obj, ServiceError error) {
        if (obj != null) {
            return;
        }
        throw new BaseException(error);
    }

    public static void mustNull(Object obj, String error) {
        if (obj != null) {
            throw new BaseException(CommonErrorCode.PARAMS_ERROR,error);
        }
        return;
    }
    public static void notNull(Object obj, String error) {
        if (obj != null) {
            return;
        }
        throw new BaseException("4001",error);
    }

    /**
     * 不是true throw error
     *
     * @param result
     * @param error
     */
    public static void isTrue(Boolean result, ServiceError error) {
        if (Boolean.TRUE.equals(result)) {
            return;
        }
        throw new BaseException(error);
    }

    /**
     * 不是false throw error
     *
     * @param result
     * @param error
     */
    public static void isFalse(Boolean result, ServiceError error) {
        if (Boolean.FALSE.equals(result)) {
            return;
        }
        throw new BaseException(error);
    }

}

