package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * 定义Category实体的服务接口。
 * 继承了MyBatis Plus的IService接口，提供了常用的CRUD操作和其他服务方法。
 */
public interface CategoryService extends IService<Category>{

    public void remove(Long id);
}
