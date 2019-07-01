package com.demo.dhj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 微信配置信息
 * <p>
 *
 * @author denghaijing
 */
@ConfigurationProperties(prefix = "wechat.miniapp")
@Data
public class WxMiniAppProperties {

    /**
     * 小程序appid
     */
    private String appId;

    /**
     * 小程序appSecret
     */
    private String appSecret;

    /**
     * 模板id
     */
    private String templateId;

}
