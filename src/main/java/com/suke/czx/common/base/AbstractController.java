package com.suke.czx.common.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.common.utils.MPPageConvert;
import com.suke.czx.common.utils.UserUtil;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.service.SysUserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

	@Autowired
	protected MPPageConvert mpPageConvert;

	@Autowired
	public ObjectMapper objectMapper;

	@Autowired
	private SysUserService sysUserService;

	protected SysUser getUser() {
		return sysUserService.getById(getUserId());
	}

	@SneakyThrows
	protected String getUserId() {
		return UserUtil.getUserId();
	}
}
