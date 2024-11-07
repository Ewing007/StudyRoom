package com.ewing.domain.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-21:24
 * @Description:
 */
@Data
public class LostFoundAdminReqDto {

    private Long id;

    /**
     * 信息状态，1-表示有效，0-表示已处理
     */
    private String status;


    /**
     * 是否违规（0-否，1-是）
     */
    private String illegal;

    /**
     * 是否删除（0-否，1-是）
     */
    private String delIllegal;


}
