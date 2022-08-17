package com.yxx.integral_exchange.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yxx.integral_exchange.dto.FuluDto;
import com.yxx.integral_exchange.entity.FuluOrder;
import com.yxx.integral_exchange.service.FuluService;
import com.yxx.integral_exchange.util.HttpUtils;
import com.yxx.integral_exchange.vo.FuluVo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * (SystemUser)表服务实现类
 *
 * @author makejava
 * @since 2022-08-17 11:00:52
 */
@Service
public class FuluServiceImpl implements FuluService {


    @Override
    public FuluOrder fuluOrderDirectAdd(FuluDto dto) {
        String url = "http://openapi.fulu.com/api/getway";
        Map map = new HashMap<String, String>();
        Map<String, Object> mapContent = new HashMap<String, Object>();
        map.put("app_key", "2D5RPKC8DJNQ0Js3h7vmWmdGY3uGT1iYeBXbPTBecEYavZ6QZjgAEtCH9wXv46bj");
        map.put("method", "fulu.order.direct.add");
        map.put("timestamp", DateUtil.now());
        map.put("version", "2.0");
        map.put("format", "json");
        map.put("charset", "utf-8");
        map.put("sign_type", "md5");
        map.put("app_auth_token", "");
        mapContent.put("product_id",15395696L);
        mapContent.put("customer_order_no","202206281030191013526");
        mapContent.put("charge_account","16732362536");
        mapContent.put("buy_num",1);
        /*mapContent.put("charge_phone", "16732362536");
        mapContent.put("charge_value", "1");
        mapContent.put("customer_order_no", "15395696");
        mapContent.put("customer_price", "1");*/
        map.put("biz_content", JSON.toJSONString(mapContent));
        String sysSecret = "02412b06abea4c2aa5ea64c87273391f";
        System.out.println(DateUtil.now());
        String sign = Sign(sysSecret, map);
        map.put("sign", sign);
        System.out.println(sign);
        String result = HttpUtils.sendJsonHttpPost(url, JSON.toJSONString(map));
        System.out.println(result);
        FuluOrder fuluOrder = JSON.parseObject(result, FuluOrder.class);

        return fuluOrder;
    }

    public static String Sign(String sysSecret, Map<String, String> map) {

        JSONObject resultJson = JSONObject.fromObject(map);
        char[] s = JSON.toJSONString(resultJson, SerializerFeature.WriteMapNullValue).toCharArray();
        Arrays.sort(s);
        String outputSignOriginalStr = new String(s) + sysSecret;
        String sign = MD5(outputSignOriginalStr);
        return sign;
    }

    public static String MD5(String pwd) {
        try {
            // 创建加密对象
            MessageDigest digest = MessageDigest.getInstance("md5");

            // 调用加密对象的方法，加密的动作已经完成
            byte[] bs = digest.digest(pwd.getBytes());
            // 第一步，将数据全部转换成正数：
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                // 第二步，将所有的数据转换成16进制的形式
                // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                // 因此，需要对temp进行判断
                if (temp < 16 && temp >= 0) {
                    // 手动补上一个“0”
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
