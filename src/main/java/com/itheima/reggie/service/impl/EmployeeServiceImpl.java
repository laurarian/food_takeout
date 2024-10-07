package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * Employee实体的服务实现类。
 * 继承自MyBatis Plus的ServiceImpl，该类提供了IService接口的默认实现。
 * 该类指定了EmployeeMapper作为数据访问层，用于实现具体的数据库操作。
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
