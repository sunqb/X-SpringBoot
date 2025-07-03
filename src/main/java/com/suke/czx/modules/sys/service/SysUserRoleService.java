package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {
	void saveOrUpdate(String userId, List<Long> roleIdList);
	void removeByUserIds(String[] userIds);
}