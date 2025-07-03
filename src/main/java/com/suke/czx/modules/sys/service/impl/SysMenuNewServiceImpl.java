package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.TreeUtil;
import com.suke.czx.modules.sys.entity.SysMenuNew;
import com.suke.czx.modules.sys.mapper.SysMenuNewMapper;
import com.suke.czx.modules.sys.service.SysMenuNewService;
import com.suke.czx.modules.sys.vo.SysMenuNewVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysMenuNewServiceImpl extends ServiceImpl<SysMenuNewMapper, SysMenuNew> implements SysMenuNewService {

    @Override
    public List<SysMenuNewVO> getUserMenuList(String userId) {
        List<SysMenuNew> menuList;
        if(userId.equals(Constant.SUPER_ADMIN)){
            menuList = list();
        } else {
            menuList = baseMapper.queryUserAllMenu(Long.parseLong(userId));
        }
        List<SysMenuNewVO> voList = menuList.stream().map(menu -> {
            SysMenuNewVO vo = new SysMenuNewVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());
        return TreeUtil.build(voList, -1L);
    }

    @Override
    public Set<String> findUserPermissions(String userId) {
        if(userId.equals(Constant.SUPER_ADMIN)){
            return new HashSet<>(baseMapper.queryAllPerms(null));
        }
        return new HashSet<>(baseMapper.queryAllPerms(Long.parseLong(userId)));
    }
}
