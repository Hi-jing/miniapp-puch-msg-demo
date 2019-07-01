package com.demo.dhj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 微信配置信息
 * <p>
 *
 * @author denghaijing
 * @since 0.1.0
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

    /**
     * 消息模板中的页面跳转路径
     */
    private String templatePage;

}
