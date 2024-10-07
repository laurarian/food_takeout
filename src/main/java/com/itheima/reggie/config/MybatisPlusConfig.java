package com.itheima.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置mybatis plus的分页插件
 * 分页是数据库操作中常见的功能，它允许你每次只从数据库中获取固定数量的记录
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 使用 Spring 的 @Bean 注解声明一个 Spring 管理的 bean。
     * 这个方法配置了 MyBatis Plus 的拦截器，特别是加入了分页拦截器。
     * 分页拦截器用于拦截查询请求，自动实现分页逻辑。
     *
     * @return 配置好的 MyBatis Plus 拦截器。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor(); // 创建 MyBatis Plus 拦截器实例
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor()); // 添加分页拦截器到 MyBatis Plus
        return mybatisPlusInterceptor;
    }
}
