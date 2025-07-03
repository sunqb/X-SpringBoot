package com.suke.czx.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtil {

    public String getUserId() {
        return StpUtil.getLoginIdAsString();
    }

}
