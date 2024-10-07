package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController // 标记这个类为RESTful控制器，处理HTTP请求并返回JSON格式的数据
@RequestMapping("/employee") // 设定了该控制器所有方法的基础URL路径
public class EmployeeController {

    @Autowired // 自动注入EmployeeService类，用于业务操作
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request  request 提供请求信息的HttpServletRequest对象，可以用来访问例如请求头和请求参数等数据
     * @param employee employee 通过HTTP请求体以JSON格式传入，被Spring自动解析填充的员工对象
     * @return 返回一个泛型为Employee的R对象，通常R是一个用于封装响应数据和状态的通用类
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        // 1. 将页面提交的密码进行md5加密处理
        // .var 根据右侧表达式自动推断类型补全左侧
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>(); // 包装查询对象
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper); // sql表设计了username唯一因此可以调用getone

        // 3. 如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }

        // 4. 密码比对，如果不一致则返回密码错误结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        // 5. 查看员工状态，如果为禁用状态，则返回员工已禁用状态
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        // 6. 登录成功，将员工信息放入Session并返回登录成功状态
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理session中保存的当前登录员工的员工id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        // 设置初始密码123456，需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 没有的属性进行初始化,status不用初始化了，表给了默认值1正常
        // employee.setCreateTime(LocalDateTime.now()); // 初始化为当前系统时间
        // employee.setUpdateTime(LocalDateTime.now()); // 这条记录的更新时间

        // 获得当前登录用户的id,创建人也就是当前登录用户的id
        // Long empId = (Long)request.getSession().getAttribute("employee");
        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);

        employeeService.save(employee); // 这里的save方法是mybatis plus的save方法

        return R.success("新增员工成功");
    }

    /**
     * 员工信息的分页查询
     * 注意这里的泛型是Page(Mybatis Plus),因为前端要获取data中的records和total
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        // 添加过滤条件,name不为空才会添加过滤条件,like方法第一个参数boolean执行了判断
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件(根据更新时间进行排序)
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }


    /**
     * 根据id修改员工信息 @RequestBody 注解会启动 Spring 的消息转换器,把 JSON 格式的请求体自动转换为 Java 对象
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为:{}", id);

        // Long empID = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateTime(LocalDateTime.now());
        // employee.setUpdateUser(empID);
        employeeService.updateById(employee);

        return R.success("员工信息更新成功");
    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getByID(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }

}
