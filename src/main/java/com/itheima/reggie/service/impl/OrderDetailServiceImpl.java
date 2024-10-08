package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.*;
import com.itheima.reggie.mapper.OrderDetailMapper;
import com.itheima.reggie.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author regina
 * @version 1.0
 * @date
 */
@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService{
}