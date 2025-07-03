package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysRole;
import com.suke.czx.modules.sys.entity.SysUser;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    List<String> queryAllPerms(Long userId);
    List<Long> queryAllMenuId(Long userId);
    List<SysRole> queryAllRoles(Long userId);
}

