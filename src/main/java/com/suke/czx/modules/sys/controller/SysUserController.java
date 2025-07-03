package com.suke.czx.modules.sys.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.HttpContextUtils;
import com.suke.czx.common.utils.IPUtils;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.service.SysMenuNewService;
import com.suke.czx.modules.sys.service.SysUserService;
import com.suke.czx.modules.sys.vo.RouterInfo;
import com.suke.czx.modules.sys.vo.SysMenuNewVO;
import com.suke.czx.modules.sys.vo.UserInfoVO;
import com.suke.zhjg.common.autofull.annotation.AutoFullData;
import com.suke.zhjg.common.autofull.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
@Api(value = "SysUserController", tags = "系统用户")
public class SysUserController extends AbstractController {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;
    private final SysMenuNewService sysMenuNewService;

    @AutoFullData
    @GetMapping(value = "/list")
    public R list(@RequestParam Map<String, Object> params) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (!getUserId().equals(Constant.SUPER_ADMIN)) {
            queryWrapper.lambda().eq(SysUser::getCreateUserId, getUserId());
        }

        final String keyword = mpPageConvert.getKeyword(params);
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.lambda().and(func -> func.like(SysUser::getUsername, keyword).or().like(SysUser::getMobile, keyword));
        }
        IPage<SysUser> listPage = sysUserService.page(mpPageConvert.pageParamConvert(params), queryWrapper);
        return R.ok().setData(listPage);
    }

    @GetMapping(value = "/sysInfo")
    public R sysInfo() {
        final String userId = getUserId();
        final List<SysMenuNewVO> userMenu = sysMenuNewService.getUserMenuList(userId);

        RouterInfo routerInfo = new RouterInfo();
        routerInfo.setMenus(userMenu);

        final SysUser sysUser = sysUserService.getById(userId);
        final Set<String> userPermissions = sysMenuNewService.findUserPermissions(userId);

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setUserId(sysUser.getUserId());
        userInfo.setUserName(sysUser.getUsername());
        userInfo.setName(sysUser.getName());
        userInfo.setLoginIp(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        final String photo = sysUser.getPhoto();
        userInfo.setPhoto(photo == null ? "https://img0.baidu.com/it/u=1833472230,3849481738&fm=253&fmt=auto?w=200&h=200" : photo);
        userInfo.setRoles(new String[]{sysUser.getUserId().equals(Constant.SUPER_ADMIN) ? "admin" : "common"});
        userInfo.setTime(DateUtil.now());
        userInfo.setAuthBtnList(userPermissions.toArray(new String[0]));
        routerInfo.setUserInfo(userInfo);
        return R.ok().setData(routerInfo);
    }

    @SysLog("修改密码")
    @PostMapping(value = "/password")
    public R password(@RequestBody Map<String, String> params) {
        String password = params.get("password");
        String newPassword = params.get("newPassword");

        if (StrUtil.isEmpty(newPassword)) {
            return R.error("新密码不为能空");
        }

        SysUser user = sysUserService.getById(getUserId());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return R.error("原密码不正确");
        }

        newPassword = passwordEncoder.encode(newPassword);
        sysUserService.updatePassword(getUserId(), user.getPassword(), newPassword);
        return R.ok();
    }

    @SysLog("保存用户")
    @PostMapping(value = "/save")
    public R save(@RequestBody @Validated SysUser user) {
        user.setCreateUserId(getUserId());
        sysUserService.saveUser(user);
        return R.ok();
    }

    @SysLog("修改用户")
    @PostMapping(value = "/update")
    public R update(@RequestBody @Validated SysUser user) {
        sysUserService.updateUser(user);
        return R.ok();
    }

    @SysLog("删除用户")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody String[] userIds) {
        for (String userId : userIds) {
            if (userId.equals(Constant.SUPER_ADMIN)) {
                return R.error("系统管理员不能删除");
            }
            if (userId.equals(getUserId())) {
                return R.error("当前用户不能删除");
            }
        }
        sysUserService.deleteBatch(userIds);
        return R.ok();
    }
}