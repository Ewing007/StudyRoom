package com.ewing.service.impl;

import Result.ResultPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ewing.service.FileUploadService;
import com.ewing.service.UserTableService;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import Exception.BusinessException;

/**
 * @Author: Ewing
 * @Date: 2024-10-28-14:20
 * @Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    private final UserTableService userService;
    @Value("${StudyRoom.file.upload.path}")
    private String fileUploadPath;
    private final LocalDateTime now = LocalDateTime.now();
    private final String savePath = Paths.get(
            SystemConfigConstant.FILE_UPLOAD_DIRECTORY,
            now.format(DateTimeFormatter.ofPattern("yyyy")),
            now.format(DateTimeFormatter.ofPattern("MM")),
            now.format(DateTimeFormatter.ofPattern("dd"))
    ).toString();

    @SneakyThrows
    @Override
    public ResultPage<String> uploadAvatar(MultipartFile file) {
        ResultPage<String> result = uploadFile(file);

        String avatarUrl = result.getData();
        userService.updateUserAvatar(avatarUrl);

        return result;
    }

    @SneakyThrows
    @Override
    public ResultPage<String> uploadStudyRoomImage(MultipartFile file) {
        return uploadFile(file);
    }

    @SneakyThrows
    @Override
    public ResultPage<String> uploadLostAndFoundImage(MultipartFile file) {
        return uploadFile(file);
    }

//    @SneakyThrows
//    private ResultPage<String> uploadFile(MultipartFile file) {
//        String oriName = file.getOriginalFilename();
//        assert oriName != null;
//        log.info("oriName.substring(oriName.indexOf('.')):{}", oriName.substring(oriName.indexOf('.')));
//        log.info("fileUploadPath.substring(fileUploadPath.indexOf('/')):{}", fileUploadPath.substring(fileUploadPath.indexOf('/')));
//        String saveFileName = IdWorker.get32UUID() + oriName.substring(oriName.indexOf('.'));
//        log.info("saveFileName:{}", saveFileName);
//        File saveFile = new File(fileUploadPath + savePath, saveFileName);
//        log.info("saveFile:{}", saveFile);
//        if (!saveFile.getParentFile().exists()) {
//            boolean isSuccess = saveFile.getParentFile().mkdirs();
//            if (!isSuccess) {
//                throw new BusinessException(ErrorEnum.USER_UPLOAD_FILE_ERROR);
//            }
//        }
//
//        file.transferTo(saveFile);
//        if (Objects.isNull(ImageIO.read(saveFile))) {
//            // 上传的文件不是图片
//            Files.delete(saveFile.toPath());
//            throw new BusinessException(ErrorEnum.USER_UPLOAD_FILE_TYPE_NOT_MATCH);
//        }
//        log.info("fileUploadPath + SystemConfigConstant.FILE_UPLOAD_DIRECTORY:{}", fileUploadPath + SystemConfigConstant.FILE_UPLOAD_DIRECTORY);
//        log.info("图片地址: {}", fileUploadPath + savePath + File.separator + saveFileName);
//        return ResultPage.SUCCESS(savePath + File.separator + saveFileName);
//    }
//@SneakyThrows
//private ResultPage<String> uploadFile(MultipartFile file) {
//    String oriName = file.getOriginalFilename();
//    assert oriName != null;
//
//    // 获取文件后缀名
//    String fileExtension = oriName.substring(oriName.indexOf('.'));
//    log.info("文件扩展名: {}", fileExtension);
//
//    // 使用 Paths.get 构建路径，确保分隔符一致
//    String saveFileName = IdWorker.get32UUID() + fileExtension;
//    Path saveFilePath = Paths.get(fileUploadPath, savePath, saveFileName);
//    log.info("保存文件路径: {}", saveFilePath);
//
//    // 确保父级目录存在
//    File saveFile = saveFilePath.toFile();
//    if (!saveFile.getParentFile().exists()) {
//        boolean isSuccess = saveFile.getParentFile().mkdirs();
//        if (!isSuccess) {
//            throw new BusinessException(ErrorEnum.USER_UPLOAD_FILE_ERROR);
//        }
//    }
//
//    // 文件保存
//    file.transferTo(saveFile);
//
//    // 检查是否为图片类型
//    if (ImageIO.read(saveFile) == null) {
//        Files.delete(saveFilePath);  // 删除非图片文件
//        throw new BusinessException(ErrorEnum.USER_UPLOAD_FILE_TYPE_NOT_MATCH);
//    }
//
//    // 日志记录完整的文件路径
//    log.info("文件上传目录: {}", fileUploadPath);
//    log.info("文件上传相对路径: {}", savePath + "/" + saveFileName);
//
//    // 返回相对路径，供前端访问
//    return ResultPage.SUCCESS(savePath + "/" + saveFileName);
//}
@SneakyThrows
private ResultPage<String> uploadFile(MultipartFile file) {
    String oriName = file.getOriginalFilename();
    assert oriName != null;

    // 获取文件后缀名
    String fileExtension = oriName.substring(oriName.indexOf('.'));
    log.info("文件扩展名: {}", fileExtension);

    // 使用 Paths.get 构建路径，确保分隔符一致
    String saveFileName = IdWorker.get32UUID() + fileExtension;
    Path saveFilePath = Paths.get(fileUploadPath, savePath, saveFileName);
    log.info("保存文件路径: {}", saveFilePath);

    // 确保父级目录存在
    File saveFile = saveFilePath.toFile();
    if (!saveFile.getParentFile().exists()) {
        boolean isSuccess = saveFile.getParentFile().mkdirs();
        if (!isSuccess) {
            throw new BusinessException(ErrorEnum.USER_UPLOAD_FILE_ERROR);
        }
    }

    // 文件保存
    file.transferTo(saveFile);

    // 检查是否为图片类型
    if (ImageIO.read(saveFile) == null) {
        Files.delete(saveFilePath);  // 删除非图片文件
        throw new BusinessException(ErrorEnum.USER_UPLOAD_FILE_TYPE_NOT_MATCH);
    }

    // 日志记录完整的文件路径
    log.info("文件上传目录: {}", fileUploadPath);
    log.info("文件上传相对路径: {}", savePath.replace(File.separator, "/") + "/" + saveFileName);

    // 返回相对路径，供前端访问
    String baseUrl = "http://localhost:8082/DataSource/StudyRoom/UploadFile";
    return ResultPage.SUCCESS(  baseUrl + savePath.replace(File.separator, "/") + "/" + saveFileName);
}

}
