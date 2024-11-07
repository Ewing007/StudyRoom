package constant;

/**
 * @Author: Ewing
 * @Date: 2024-10-10-15:22
 * @Description:
 */
public class CacheConstant {

    /**
     * @description: Redis缓存前缀
     * @author ewing
     * @date 2024/6/18 20:04
     * @version 1.0
     */
    public static final String REDIS_CACHE_PREFIX = "Cache::StudyRoom::";

    /**
     * @description: token缓存前缀
     * @author ewing
     * @date 2024/6/18 20:04
     * @version 1.0
     */
    public static final String TOKEN_VERITY_CACHE_KEY = REDIS_CACHE_PREFIX+ "token::";

    /**
     * @description: users缓存前缀
     * @author ewing
     * @date 2024/6/18 20:04
     * @version 1.0
     */
    public static final String USERS_CACHE_KEY = REDIS_CACHE_PREFIX+ "users::";

    /**
     * @description: 图片验证码缓存
     * @author ewing
     * @date 2024/6/18 20:06
     * @version 1.0
     */
    public static final String IMG_VERITY_CODE_CACHE_KEY = REDIS_CACHE_PREFIX + "imgVerityCodeCache::";

    /**
     * @description: 短信验证码缓存
     * @author ewing
     * @date 2024/6/18 20:07
     * @version 1.0
     */
    public static final String SMS_VERITY_CODE_CACHE_KEY = REDIS_CACHE_PREFIX + "smsVerityCacheCode::";

    /**
     * @description: caffene缓存
     * @author ewing
     * @date 2024/6/30 23:31
     * @version 1.0
     */
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";

    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    public static final String USER_INFO_CACHE_NAME = "userInfoCache";

    public enum CacheEnum {
        USER_INFO_CACHE(2, USER_INFO_CACHE_NAME, 60 * 60 * 24, 10000);

        /**
         * @description: 缓存类型 0-本地 1-本地和远程 2-远程
         * @author ewing
         * @date 2024/7/2 11:41
         * @version 1.0
         */
        private int type;

        /**
         * @description: 缓存的名字
         * @author ewing
         * @date 2024/7/2 11:42
         * @version 1.0
         */
        private String name;

        /**
         * @description: 失效时间（秒） 0-永不失效
         * @author ewing
         * @date 2024/7/2 11:42
         * @version 1.0
         */
        private int ttl;

        /**
         * @description: 最大容量
         * @author ewing
         * @date 2024/7/2 11:42
         * @version 1.0
         */
        private int maxSize;
        CacheEnum(int type, String name, int ttl, int maxSize) {
            this.type = type;
            this.name = name;
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public boolean isLocal() { return type <= 1; }

        public boolean isRemote() { return type >= 1; }

    }
}
