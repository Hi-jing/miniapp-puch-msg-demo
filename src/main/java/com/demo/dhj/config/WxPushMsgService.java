package com.demo.dhj.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cn.binarywang.wx.miniapp.api.WxMaService;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 消息推送服务
 * <p>
 *
 * @author denghaijing
 * @since 0.2.0
 */
@Data
public class WxPushMsgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxPushMsgService.class);

    @Autowired
    private WxMaService wxMaService;

    /**
     * 用来请求微信的get和post
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 模版id
     */
    private String templateId;

    /**
     * 跳到小程序的页面
     */
    private String templatePage;

    public String pushMsg(String openid, String formId, List<String> list, String params) throws WxErrorException {
        //获取access_token
        String accessToken = this.wxMaService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;

        Map<String, Object> wxMssVo = new HashMap<>(5);
        wxMssVo.put("touser", openid);
        wxMssVo.put("template_id", this.templateId);
        wxMssVo.put("page", this.templatePage + params);
        wxMssVo.put("form_id", formId);
        wxMssVo.put("data", generateTemplateData(list));

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
    }


    /**
     * 生成模板数据
     *
     * @param list
     * @return
     */
    public Map<String, MyTemplateData> generateTemplateData(List<String> list) {
        Map<String, MyTemplateData> result = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            MyTemplateData template = new MyTemplateData();
            template.setValue(list.get(i));
            result.put("keyword" + (i + 1), template);
        }
        return result;
    }


    /**
     * 模板数据类
     */
    @Data
    private class MyTemplateData {
        private String value;
    }

}
