package Exception;

import constant.ErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Ewing
 * @Date: 2024-09-29-22:16
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends Exception{

    private final ErrorEnum errorEnum;

    public BusinessException(ErrorEnum errorEnum) {
        // 不调用父类 Throwable的fillInStackTrace() 方法生成栈追踪信息，提高应用性能
        // 构造器之间的调用必须在第一行
        super(errorEnum.getMessage(), null, false, false);
        this.errorEnum = errorEnum;
    }
}
