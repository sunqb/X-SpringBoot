package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysMenuNew;

import java.util.List;

/**
 * 菜单管理
 *
 * @author czx
 * @email object_czx@163.com
 */
public interface SysMenuNewMapper extends BaseMapper<SysMenuNew> {

    List<SysMenuNew> queryUserAllMenu(Long userId);

    List<String> queryAllPerms(Long userId);
}
