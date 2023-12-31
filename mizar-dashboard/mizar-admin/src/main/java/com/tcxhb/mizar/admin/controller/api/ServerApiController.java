package com.tcxhb.mizar.admin.controller.api;

import com.tcxhb.mizar.common.model.MiscResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/10/31
 * 对外提供的API
 */
@RestController
@RequestMapping("/api/server")
@Slf4j
public class ServerApiController {

    @GetMapping(value = "/server/beat")
    public MiscResult<Boolean> beat() {
        return MiscResult.suc(true);
    }
}

