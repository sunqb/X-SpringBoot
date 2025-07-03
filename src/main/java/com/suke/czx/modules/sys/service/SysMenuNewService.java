package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysMenuNew;
import com.suke.czx.modules.sys.vo.SysMenuNewVO;

import java.util.List;
import java.util.Set;

public interface SysMenuNewService extends IService<SysMenuNew> {
    List<SysMenuNewVO> getUserMenuList(String userId);
    Set<String> findUserPermissions(String userId);
}