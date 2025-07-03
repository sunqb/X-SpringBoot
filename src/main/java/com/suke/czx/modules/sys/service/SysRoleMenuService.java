package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysRoleMenu;

public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void deleteByRoleIds(Long[] roleIds);
}