package com.suke.czx.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.modules.sys.entity.SysRole;
import com.suke.czx.modules.sys.service.SysRoleMenuService;
import com.suke.czx.modules.sys.service.SysRoleService;
import com.suke.zhjg.common.autofull.annotation.AutoFullData;
import com.suke.zhjg.common.autofull.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
@Api(value = "SysRoleController", tags = "角色管理")
public class SysRoleController extends AbstractController {

    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

    @AutoFullData
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (!getUserId().equals(Constant.SUPER_ADMIN)) {
            queryWrapper.lambda().eq(SysRole::getCreateUserId, Long.parseLong(getUserId()));
        }
        IPage<SysRole> listPage = sysRoleService.page(mpPageConvert.pageParamConvert(params), queryWrapper);
        return R.ok().setData(listPage);
    }

    @GetMapping("/select")
    public R select() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (!getUserId().equals(Constant.SUPER_ADMIN)) {
            queryWrapper.lambda().eq(SysRole::getCreateUserId, Long.parseLong(getUserId()));
        }
        List<SysRole> list = sysRoleService.list(queryWrapper);
        return R.ok().setData(list);
    }

    @SysLog("保存角色")
    @PostMapping("/save")
    public R save(@RequestBody SysRole role) {
        role.setCreateUserId(Long.parseLong(getUserId()));
        sysRoleService.save(role);
        return R.ok();
    }

    @SysLog("修改角色")
    @PostMapping("/update")
    public R update(@RequestBody SysRole role) {
        sysRoleService.updateById(role);
        return R.ok();
    }

    @SysLog("删除角色")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}