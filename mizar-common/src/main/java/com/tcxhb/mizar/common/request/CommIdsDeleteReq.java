package com.tcxhb.mizar.common.request;

import com.tcxhb.mizar.common.utils.ParamUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/10/20
 */
@Data
public class CommIdsDeleteReq implements Serializable {
    private List<Long> ids;

    public void check() {
        ParamUtils.notNull(ids, "ids不可为空");
        ParamUtils.isTrue(ids.size() > 0, "ids不可为空");
    }

    public String split() {
        if (ids == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int len = ids.size();
        for (int i = 0; i < len; i++) {
            builder.append(ids.get(i));
            if (i != len - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
