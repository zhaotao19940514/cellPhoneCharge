package com.yxx.integral_exchange.controller;

import com.yxx.integral_exchange.dto.FuluDto;
import com.yxx.integral_exchange.entity.FuluOrder;
import com.yxx.integral_exchange.service.FuluService;
import com.yxx.integral_exchange.vo.FuluVo;
import com.yxx.integral_exchange.vo.Ret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zt
 * @ClassName FuluController
 * @description: TODO(这里用一句话描述类的作用)
 * @date 2022/8/17
 */

@RestController
@RequestMapping("/fulu")
public class FuluController {

    @Autowired
    private FuluService fuluService;


    @RequestMapping("/fuluOrderDirectAdd")
    public Ret<FuluOrder> fuluOrderDirectAdd(@RequestBody FuluDto dto) {
        FuluOrder order = fuluService.fuluOrderDirectAdd(dto);
        if(order.getCode()==0){
            return Ret.success(order);
        }else{
            return Ret.failed("充值失败！请稍后再试");
        }
    }
}
