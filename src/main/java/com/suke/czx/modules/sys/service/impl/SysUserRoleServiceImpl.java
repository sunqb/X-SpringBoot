package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.entity.SysUserRole;
import com.suke.czx.modules.sys.mapper.SysUserRoleMapper;
import com.suke.czx.modules.sys.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public void saveOrUpdate(String userId, List<Long> roleIdList) {
        this.remove(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userId));
        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }
        List<SysUserRole> list = roleIdList.stream().map(roleId -> {
            SysUserRole sysUserRoleEntity = new SysUserRole();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);
            return sysUserRoleEntity;
        }).collect(Collectors.toList());
        this.saveBatch(list);
    }

    @Override
    public void removeByUserIds(String[] userIds) {
        this.remove(new QueryWrapper<SysUserRole>().lambda().in(SysUserRole::getUserId, Arrays.asList(userIds)));
    }
}