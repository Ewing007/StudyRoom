package Exception;

import Result.ResultPage;
import constant.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Ewing
 * @Date: 2024-09-29-22:17
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends Exception {

    /**
     * 处理数据校验异常
     */
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResultPage<Void> handlerBindException(BindException e) {
        log.error(e.getMessage(), e);
        return ResultPage.FAIL(ErrorEnum.USER_REQUEST_PARAM_ERROR);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultPage<Void> handlerBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return ResultPage.FAIL(e.getErrorEnum());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResultPage<Void> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultPage.ERROR();
    }
}
