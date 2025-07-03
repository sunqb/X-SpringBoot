package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.entity.SysRoleMenu;
import com.suke.czx.modules.sys.mapper.SysRoleMenuMapper;
import com.suke.czx.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public void deleteByRoleIds(Long[] roleIds) {
        baseMapper.delete(new QueryWrapper<SysRoleMenu>().lambda().in(SysRoleMenu::getRoleId, Arrays.asList(roleIds)));
    }
}