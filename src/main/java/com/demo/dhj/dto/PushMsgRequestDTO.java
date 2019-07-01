package com.demo.dhj.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 小程序推送请求DTO
 * <p>
 *
 * @author denghaijing
 */
@Data
public class PushMsgRequestDTO {

    @NotBlank(message = "用户openid不能为空")
    private String openid;

    @NotBlank(message = "formid不能为空")
    private String formid;

    @NotNull(message = "推送文字不能为空")
    private String[] data;

}
