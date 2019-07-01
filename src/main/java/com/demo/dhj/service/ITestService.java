package com.demo.dhj.service;


import com.demo.dhj.dto.PushMsgRequestDTO;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 小程序端发现service接口层
 * <p>
 *
 * @author denghaijing
 * @since 1.0
 */
public interface ITestService {

    void pushMsg(PushMsgRequestDTO requestDTO) throws WxErrorException;
}
