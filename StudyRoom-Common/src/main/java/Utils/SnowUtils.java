package Utils;

import cn.hutool.core.util.IdUtil;
import lombok.experimental.UtilityClass;

/**
 * @Author: Ewing
 * @Date: 2024-06-18-21:56
 * @Description: 雪花算法工具类
 */
@UtilityClass
public class SnowUtils {

    /**
     * @description: 雪花算法生成long类型id
     * @param :  :
     * @return java.lang.Long
     * @author: ewing
     * @date: 2024/6/19 16:25
     */
    public static Long getSnowflakeNextId(){
        return IdUtil.getSnowflake(dataCenterId,workId).nextId();
    }

    /**
     * @description: 雪花算法生成string类型id
     * @param :  :
     * @return java.lang.String
     * @author: ewing
     * @date: 2024/6/19 16:29
     */
    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(dataCenterId,workId).nextIdStr();
    }
    private static final long dataCenterId = 1;

    private static final long workId = 1;
}
