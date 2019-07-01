package com.demo.dhj.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.demo.dhj.dto.PushMsgRequestDTO;
import com.demo.dhj.service.ITestService;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * TODO 〈一句话功能简述〉
 * <p>
 * 〈功能详细描述〉 描述
 *
 * @author denghaijing
 * @date 2019/7/1
 * @since [产品/模块版本]
 */
@RestController
public class TestController {

    @Autowired
    private ITestService testService;

    @GetMapping("test")
    public String test(){
        return "hello world";
    }

    @PostMapping("/pushMsg")
    public ResponseEntity<String> pushMsg(@RequestBody @Valid PushMsgRequestDTO requestDTO) throws WxErrorException, WxErrorException {
        testService.pushMsg(requestDTO);
        return ResponseEntity.ok().build();

    }
}
