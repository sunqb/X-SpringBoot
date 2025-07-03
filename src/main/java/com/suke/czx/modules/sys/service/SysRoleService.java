package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysRole;

public interface SysRoleService extends IService<SysRole> {
    void deleteBatch(Long[] roleIds);
}