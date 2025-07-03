package com.suke.czx.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.suke.czx.modules.sys.entity.SysRole;
import com.suke.czx.modules.sys.service.SysMenuNewService;
import com.suke.czx.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final SysUserService sysUserService;
    private final SysMenuNewService sysMenuNewService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>(sysMenuNewService.findUserPermissions(loginId.toString()));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Set<SysRole> roleSet = sysUserService.findUserRoles(loginId.toString());
        return roleSet.stream().map(SysRole::getRoleName).collect(Collectors.toList());
    }
}