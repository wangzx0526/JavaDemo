//package com.zane.config;
//
//import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class SaTokenConfig implements WebMvcConfigurer {
//   // 注册拦截器
//   @Override
//   public void addInterceptors(InterceptorRegistry registry) {
//       // 注册 Sa-Token 拦截器，拦截所有路由
//       registry.addInterceptor(new SaInterceptor(handle -> {
//           // 检查是否登录，未登录则抛出异常
//           StpUtil.checkLogin();
//       }))
//       .addPathPatterns("/**")
//       .excludePathPatterns("/system/login")  // 排除登录接口
//       .excludePathPatterns("/system/register")  // 排除注册接口
//       .excludePathPatterns("/swagger-ui/**")  // 排除Swagger接口
//       .excludePathPatterns("/v3/api-docs/**")  // 排除API文档接口
//       .excludePathPatterns("/doc.html");  // 排除Knife4j接口
//   }
//}
