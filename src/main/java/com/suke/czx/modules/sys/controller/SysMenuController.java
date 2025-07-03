package com.suke.czx.modules.sys.controller;

import com.suke.czx.common.base.AbstractController;
import com.suke.czx.modules.sys.service.SysMenuNewService;
import com.suke.zhjg.common.autofull.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
@Api(value = "SysMenuController", tags = "菜单管理")
public class SysMenuController extends AbstractController {

    private final SysMenuNewService sysMenuNewService;

    @GetMapping(value = "/nav")
    public R nav() {
        return R.ok().setData(sysMenuNewService.getUserMenuList(getUserId()));
    }
}