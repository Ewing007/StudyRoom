package com.ewing.service;

import Result.ResultPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Ewing
 * @Date: 2024-10-28-14:20
 * @Description:
 */

public interface FileUploadService {
    ResultPage<String> uploadAvatar(MultipartFile file);

    ResultPage<String> uploadStudyRoomImage(MultipartFile file);

    ResultPage<String> uploadLostAndFoundImage(MultipartFile file);
}
