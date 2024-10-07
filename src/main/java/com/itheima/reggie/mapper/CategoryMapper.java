package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
// 这里继承了 MyBatis Plus 的 BaseMapper 类，BaseMapper 提供了很多通用的方法，
// 比如插入、删除、修改、查询等，这些方法都可以直接使用，无需自定义。
// Category 指定了这个 Mapper 接口操作的实体类是 Category，这意味着 BaseMapper 中的方法
// 都将针对 Category实体类的数据进行操作。
}