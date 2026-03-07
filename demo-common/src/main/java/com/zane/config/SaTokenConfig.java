package com.zane.config;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                        if (handler instanceof HandlerMethod handlerMethod) {
                            boolean ignoreMethod = handlerMethod.hasMethodAnnotation(SaIgnore.class);
                            boolean ignoreClass = AnnotatedElementUtils.hasAnnotation(handlerMethod.getBeanType(), SaIgnore.class);
                            log.info("sa-token preHandle uri={}, handler={}.{}, ignoreMethod={}, ignoreClass={}",
                                    request.getRequestURI(),
                                    handlerMethod.getBeanType().getSimpleName(),
                                    handlerMethod.getMethod().getName(),
                                    ignoreMethod,
                                    ignoreClass);
                            if (ignoreMethod || ignoreClass) {
                                return true;
                            }
                        }

                        StpUtil.checkLogin();
                        return true;
                    }
                })
                .addPathPatterns("/**")
                .excludePathPatterns("/system/login")
                .excludePathPatterns("/system/register")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/error");
    }
}

