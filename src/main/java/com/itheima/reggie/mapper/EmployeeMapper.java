package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

// 使用 @Mapper 注解，让 Spring Boot 能够扫描到这个接口，并将其作为 Mapper 接口处理
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    // 这里继承了 MyBatis Plus 的 BaseMapper 类，BaseMapper 提供了很多通用的方法，
    // 比如插入、删除、修改、查询等，这些方法都可以直接使用，无需自定义。
    // Employee 指定了这个 Mapper 接口操作的实体类是 Employee，这意味着 BaseMapper 中的方法
    // 都将针对 Employee 实体类的数据进行操作。
}
