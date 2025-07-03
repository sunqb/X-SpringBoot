package com.suke.czx.config;

import cn.hutool.core.util.ReUtil;
import com.suke.czx.common.annotation.AuthIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author czx
 * @title: AuthIgnoreConfig
 * @projectName x-springboot
 * @description: 搜集所有 @AuthIgnore 注解的接口并统一放行
 * @date 2019/12/24 15:56
 */
@Slf4j
@Configuration
public class AuthIgnoreConfig implements InitializingBean {

    private final WebApplicationContext applicationContext;

    public AuthIgnoreConfig(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String ASTERISK = "*";

    @Getter
    @Setter
    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            AuthIgnore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AuthIgnore.class);
            if (method != null) {
                extractUrlPatterns(info);
            }

            // 获取类上边的注解, 替代path variable 为 *
            AuthIgnore controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), AuthIgnore.class);
            if (controller != null) {
                extractUrlPatterns(info);
            }
        });
    }

    /**
     * 提取URL模式，兼容不同版本的Spring Boot路径匹配机制
     * @param info RequestMappingInfo对象
     */
    private void extractUrlPatterns(RequestMappingInfo info) {
        try {
            // 尝试使用新版本的PathPatternsRequestCondition (Spring Boot 2.6+)
            if (info.getPathPatternsCondition() != null) {
                info.getPathPatternsCondition().getPatterns()
                        .forEach(url -> urls.add(ReUtil.replaceAll(url.getPatternString(), PATTERN, ASTERISK)));
            }
            // 回退到旧版本的PatternsRequestCondition (Spring Boot 2.5及以下)
            else if (info.getPatternsCondition() != null) {
                info.getPatternsCondition().getPatterns()
                        .forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK)));
            }
        } catch (Exception e) {
            log.warn("提取URL模式时发生异常: {}", e.getMessage());
        }
    }
}
