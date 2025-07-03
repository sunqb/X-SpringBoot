package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.entity.SysRole;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.mapper.SysUserMapper;
import com.suke.czx.modules.sys.service.SysUserRoleService;
import com.suke.czx.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService sysUserRoleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser queryByUserName(String username) {
        return baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
    }

    @Override
    public Set<SysRole> findUserRoles(String userId) {
        return new HashSet<>(baseMapper.queryAllRoles(Long.parseLong(userId)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        baseMapper.insert(user);
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.updateById(user);
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
        sysUserRoleService.removeByUserIds(userIds);
    }

    @Override
    public int updatePassword(String userId, String password, String newPassword) {
        SysUser user = new SysUser();
        user.setPassword(newPassword);
        return baseMapper.update(user,
                Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userId).eq(SysUser::getPassword, password));
    }
}
