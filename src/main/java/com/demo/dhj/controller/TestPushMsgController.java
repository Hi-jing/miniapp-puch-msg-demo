package com.demo.dhj.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.demo.dhj.config.WxPushMsgService;
import com.demo.dhj.dto.PushMsgRequestDTO;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 测试发送模板信息controller
 * <p>
 *
 * @author denghaijing
 */
@RestController
@Slf4j
public class TestPushMsgController {

    @Autowired
    private WxPushMsgService wxPushMsgService;

    @PostMapping("/pushMsg")
    public ResponseEntity<String> pushMsg(@RequestBody @Valid PushMsgRequestDTO dto) throws WxErrorException {
        String result = wxPushMsgService.pushMsg(dto.getOpenid(), dto.getFormid(), Lists.newArrayList(dto.getData()), "/pages/report/report?param=xxx");
        log.info("mini app push message result is:{}", result);
        return ResponseEntity.ok().build();

    }
}
