package com.yxx.integral_exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxx.integral_exchange.dto.FuluDto;
import com.yxx.integral_exchange.entity.FuluOrder;
import com.yxx.integral_exchange.entity.SystemUser;
import com.yxx.integral_exchange.vo.FuluVo;

/**
 * (SystemUser)表服务接口
 *
 * @author makejava
 * @since 2022-08-17 11:00:51
 */
public interface FuluService {

    FuluOrder fuluOrderDirectAdd(FuluDto dto);
}
