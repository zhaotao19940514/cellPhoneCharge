package com.yxx.integral_exchange.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxx.integral_exchange.entity.SystemUser;
import com.yxx.integral_exchange.service.SystemUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SystemUser)表控制层
 *
 * @author makejava
 * @since 2022-08-17 11:00:53
 */
@RestController
@RequestMapping("systemUser")
public class SystemUserController {
    /**
     * 服务对象
     */
    @Resource
    private SystemUserService systemUserService;

}
