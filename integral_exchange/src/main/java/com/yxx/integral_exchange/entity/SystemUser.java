package com.yxx.integral_exchange.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (SystemUser)表实体类
 *
 * @author makejava
 * @since 2022-08-17 11:00:45
 */
@Data
public class SystemUser extends Model<SystemUser> {

    private Integer id;

    private String userName;

    private String password;


}
