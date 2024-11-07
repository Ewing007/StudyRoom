package Utils;

import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Ewing
 * @Date: 2024-06-18-21:56
 * @Description: JWT工具类
 */
@Slf4j
@UtilityClass
public class JwtUtils {

    /**
     * @description: JWT加密秘钥
     * @author ewing
     * @date 2024/6/19 16:32
     * @version 1.0
     */
    private static final String SECRET = "F66559580A1ADE48CDD928516062E12F";

    /**
     * @description: 系统标识头常量
     * @author ewing
     * @date 2024/6/19 16:35
     * @version 1.0
     */
    private static final String HEADER_SYSTEM_KEY = "systemKeyHeader";

    /**
     * @description: 根据用户id生产JWT
     * @param userId:
     * @param systemKey:  :
     * @return java.lang.String
     * @author: ewing
     * @date: 2024/6/19 16:41
     */
    public String generatorToken(String userId, String systemKey){
        return Jwts.builder()
                .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
                .setSubject(userId.toString())
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * @param token:
     * @param systemKey: :
     * @return java.lang.Long
     * @description: 解析JWT返回用户id
     * @author: ewing
     * @date: 2024/6/19 16:54
     */
    public String parseToken(String token, String systemKey){
        Jws<Claims> claimsJwt;
        try {
            claimsJwt = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);

            //判断jwt是否属于指定系统
            if (ObjectUtil.equal(claimsJwt.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return String.valueOf(Long.parseLong(claimsJwt.getBody().getSubject()));
            }
        } catch (JwtException e) {
            log.warn("JWT解析失败,{}",token);
        }
        return null;
    }
}
