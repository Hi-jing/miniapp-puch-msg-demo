package com.demo.dhj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dhj.config.WxPushMsgService;
import com.demo.dhj.dto.PushMsgRequestDTO;
import com.demo.dhj.service.ITestService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;


/**
 * 小程序端首页service实现层
 * <p>
 *
 * @author denghaijing
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TestServiceImpl implements ITestService {

    @Autowired
    private WxPushMsgService wxPushMsgService;

    @Override
    public void pushMsg(PushMsgRequestDTO dto) throws WxErrorException {
        String result = wxPushMsgService.pushMsg(dto.getOpenid(), dto.getFormid(), Lists.newArrayList(dto.getData()),null);
        log.info("mini app push message result is:{}", result);
    }
}
