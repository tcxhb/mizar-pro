package com.tcxhb.mizar.common.constants;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/9/9
 */
public enum CommonErrorCode implements ServiceError {


    BAD_REQUEST("1000", "系统内部错误"),
    PARAMS_ERROR("1001", "参数错误,可直接展示文案"),
    COMMON_ERROR("1002", "业务校验错误,可直接展示文案"),
    BLOCK_REQUEST("1005", "接口限流"),

    NEED_LOGIN("10000", "请重新登录"),
    FORBIDDEN("10001", "暂无权限,禁止访问"),
    HTTP_TO_AGENT_FAIL("HTTP_TO_AGENT_FAIL", "请求agent异常"),
    MACHINE_NOT_FOUND("MACHINE_NOT_FOUND", "机器信息不存在"),
    JOB_STATUS_ERROR("JOB_STATUS_ERROR", "状态异常"),
    DB_OPERATE_ERROR("DB_OPERATE_ERROR", "数据库操作异常"),
    SUCCESS("200", "成功");

    private String msg;
    private String code;

    CommonErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
