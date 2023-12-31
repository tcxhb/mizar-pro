package com.tcxhb.mizar.common.constants;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/12/9
 */
public class BlockException extends RuntimeException {
    public static final String BLOCK_EXCEPTION_FLAG = "MizarBlockException";

    /**
     *
     */
    public BlockException(String resource, Long currentQps) {
        super(BLOCK_EXCEPTION_FLAG + ",resource:" + resource + ",qps:" + currentQps);
    }
}
