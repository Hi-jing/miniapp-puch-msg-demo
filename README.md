# 小程序消息推送demo


1、
```xml
        <dependency>
            <groupId>com.github.binarywang</groupId>
            <artifactId>weixin-java-miniapp</artifactId>
            <version>3.1.0</version>
        </dependency>
```

```java
@Data
public class PushMsgRequestDTO {


    @ApiModelProperty("用户openid")
    @NotEmpty(message = "用户openid不能为空")
    private String openid;

    @ApiModelProperty("formid")
    @NotEmpty(message = "formid不能为空")
    private String formid;

    @ApiModelProperty("推送文字")
    @NotNull(message = "推送文字不能为空")
    private String[] data;


}
```

```java
    @PostMapping("/pushMsg")
    public ResponseEntity<String> pushMsg(@RequestBody @Valid PushMsgRequestDTO requestDTO) throws WxErrorException {
        LOGGER.debug(" testUser pushMsg by request dto:{}", requestDTO);
        testUserService.pushMsg(requestDTO);
        return ResponseEntity.ok().build();

    }
```

```java
    public void pushMsg(PushMsgRequestDTO dto) throws WxErrorException {
        String result = wxPushMsgService.pushMsg(dto.getOpenid(), dto.getFormid(), Lists.newArrayList(dto.getData()),null);
        LOGGER.info("mini app push message result is:{}", result);
    }
```


WxPushMsgService

```java
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
```

2、
```java
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
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 微信支付通知回调地址
     */
    private String notifyUrl;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 消息模板中的页面跳转路径
     */
    private String templatePage;

}
```
2、
```java
/**
 * 微信小程序配置类
 * <p>
 *
 * @author denghaijing
 * @since 0.1.0
 */
@Configuration
@ConditionalOnClass(WxMaService.class)
@EnableConfigurationProperties(WxMiniAppProperties.class)
public class WxMaConfiguration {
    @Autowired
    private WxMiniAppProperties properties;


    @Bean
    @ConditionalOnMissingBean
    public WxMaConfig maConfig() {
        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(this.properties.getAppId());
        config.setSecret(this.properties.getAppSecret());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }


    /**
     * 消息推送服务bean
     */
    @Bean
    @ConditionalOnMissingBean
    public WxPushMsgService wxPushMsgService() {
        WxPushMsgService wxPushMsgService = new WxPushMsgService();
        wxPushMsgService.setTemplatePage(this.properties.getTemplatePage());
        wxPushMsgService.setTemplateId(this.properties.getTemplateId());
        return wxPushMsgService;
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

}
```

```yaml
wechat:
  miniapp:
    appId: wxe92819f5cdd4ba7a
    appSecret: 2f8475a39dcd8f0de4a0e9e48af8768f
    mchId: 1523165261
    mchKey: ppissumob0ce2eff4418f4d366ddc352
    templateId: RyPDvws09dmfmLUmPUq9XaYuu3tp_ph1MvX_8U4Yn8U
    templatePage: /pages/report/report
```

```java
        String testTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        List<String> data = Lists.newArrayList(
            testUserName, testResult, testTime, "好友已经对你做出了评价，点击查看>>");
        String result = null;
        try {
            String parentReportId = sumoReportRepository.findReportIdByChildrenReportId(reportId);
            String params = "?reportId=" + parentReportId + "&code=" + testCode + "&type=2&fromEvaluate=true&isFormPush=true" ;
            result = wxPushMsgService.pushMsg(openId, testUserFormId.getFormId(), data, params);
            //删除已使用的formid
            testUserFormIdRepository.delete(testUserFormId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            LOGGER.error("mini app push message error:{}", e.getMessage());
        }
        LOGGER.info("mini app push message result is:{}", result);
```

