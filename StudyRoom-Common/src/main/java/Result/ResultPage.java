package Result;

import constant.ErrorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: Ewing
 * @Date: 2024-09-29-22:57
 * @Description:
 */
@Getter
@Schema(name = "ResultPage", description="数据返回实体类")
public class ResultPage<T> {

    @Schema(name = "message", type = "String", description = "返回消息")
    private String message;

    @Schema(name = "code", type = "String", description = "状态码")
    private String code;

    @Schema(name = "data", type = "String", description = "返回数据")
    private T data;

    private ResultPage() {
        this.code = ErrorEnum.SUCCESS.getCode();
        this.message = ErrorEnum.SUCCESS.getMessage();
    }

    private ResultPage(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

    private ResultPage(T data) {
        this();
        this.data = data;
    }

    public static ResultPage<Void> SUCCESS() {
        return new ResultPage<>();
    }

    public static <T> ResultPage<T> SUCCESS(T data) {
        return new ResultPage<>(data);
    }
    public static <T> ResultPage<T> SUCCESS(ErrorEnum errorEnum) {
        return new ResultPage<>(errorEnum);
    }

    public static <T> ResultPage<T> ERROR() {
        return new ResultPage<>(ErrorEnum.SYSTEM_ERROR);
    }

    public static <T> ResultPage<T> FAIL(ErrorEnum errorEnum) {
        return new ResultPage<>(errorEnum);
    }
    public static <T> ResultPage<T> FAIL(T data) {
        return new ResultPage<>(data);
    }

}
