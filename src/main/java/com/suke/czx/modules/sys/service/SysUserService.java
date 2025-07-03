package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysRole;
import com.suke.czx.modules.sys.entity.SysUser;

import java.util.Set;

public interface SysUserService extends IService<SysUser> {
    SysUser queryByUserName(String username);
    Set<SysRole> findUserRoles(String userId);
    void saveUser(SysUser user);
    void updateUser(SysUser user);
    void deleteBatch(String[] userIds);
    int updatePassword(String userId, String password, String newPassword);
}