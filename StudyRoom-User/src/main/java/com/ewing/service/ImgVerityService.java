package com.ewing.service;

import Result.ResultPage;
import com.ewing.domain.dto.resp.ImgVerityCodeRespDto;


import java.io.IOException;

/**
 * @Author: Ewing
 * @Date: 2024-06-23-14:05
 * @Description:
 */
public interface ImgVerityService {

    public ResultPage<ImgVerityCodeRespDto> getImgVerityCode() throws IOException;
}
