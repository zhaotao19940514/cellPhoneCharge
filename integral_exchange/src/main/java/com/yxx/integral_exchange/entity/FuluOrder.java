package com.yxx.integral_exchange.entity;

import lombok.Data;

/**
 * @author zt
 * @ClassName FuluOrder
 * @description: TODO(这里用一句话描述类的作用)
 * @date 2022/8/17
 */

@Data
public class FuluOrder {

    private Integer code;
    private String message;
    private String result;
    private String sign;
}
