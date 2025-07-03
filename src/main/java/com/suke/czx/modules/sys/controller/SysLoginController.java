package com.suke.czx.modules.sys.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import com.google.code.kaptcha.Producer;
import com.suke.czx.common.annotation.AuthIgnore;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.service.SysUserService;
import com.suke.zhjg.common.autofull.util.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "SysLoginController", tags = "登录相关")
public class SysLoginController extends AbstractController {

    private final Producer producer;
    private final RedisTemplate<String,String> redisTemplate;
    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;

    @AuthIgnore
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public R hello() {
        return R.ok("hello welcome to use x-springboot");
    }

    @AuthIgnore
    @SneakyThrows
    @RequestMapping(value = "/sys/code/{time}", method = RequestMethod.GET)
    public void captcha(@PathVariable("time") String time, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        String text = producer.createText();
        log.info("验证码:{}", text);
        BufferedImage image = producer.createImage(text);
        redisTemplate.opsForValue().set(Constant.NUMBER_CODE_KEY + time, text, 60, TimeUnit.SECONDS);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IoUtil.close(out);
    }

    @AuthIgnore
    @PostMapping(value = "/sys/login")
    public R login(@RequestParam String username, @RequestParam String password, @RequestParam String code, @RequestParam String time) {
        String redisCode = redisTemplate.opsForValue().get(Constant.NUMBER_CODE_KEY + time);
        if (StringUtils.isEmpty(redisCode) || !redisCode.equals(code)) {
            throw new RRException("验证码不正确");
        }

        SysUser user = sysUserService.queryByUserName(username);
        if (user == null) {
            throw new RRException("账号或密码不正确");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RRException("账号或密码不正确");
        }

        if (user.getStatus() == 0) {
            throw new RRException("账号已被锁定,请联系管理员");
        }

        StpUtil.login(user.getUserId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        Map<String, Object> map = new HashMap<>();
        map.put(tokenInfo.getTokenName(), tokenInfo.getTokenValue());
        return R.ok(map);
    }

    @PostMapping(value = "/sys/logout")
    public R logout() {
        StpUtil.logout();
        return R.ok("退出成功");
    }
}