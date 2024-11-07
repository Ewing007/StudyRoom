//package com.ewing.aspect;
//import Result.ResultPage;
//import Utils.IpAddressUtil;
//import cn.hutool.core.util.ObjectUtil;
//import com.alibaba.fastjson.JSON;
//import com.ewing.annotation.MyLog;
//import com.ewing.domain.entity.LogTable;
//import com.ewing.mapper.LogTableMapper;
//import constant.ErrorEnum;
//import context.UserContext;
//import context.UserInfoContextHandler;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import Exception.BusinessException;
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: Ewing
// * @Date: 2024-10-11-21:02
// * @Description: 切面处理类，记录操作日志到数据库
// */
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class OperLogAspect {
//
//
//    private final LogTableMapper logTableMapper;
//
//    //为了记录方法的执行时间
//    ThreadLocal<Long> startTime = new ThreadLocal<>();
//
//    /**
//     * 设置操作日志切入点，这里介绍两种方式：
//     * 1、基于注解切入（也就是打了自定义注解的方法才会切入）
//     *    @Pointcut("@annotation(com.ewing.annotation.MyLog)")
//     * 2、基于包扫描切入
//     *    @Pointcut("execution(public * com.ewing.controller..*.*(..))")
//     */
//    @Pointcut("@annotation(com.ewing.annotation.MyLog)")//在注解的位置切入代码
//    //@Pointcut("execution(public * com.ewing.controller..*.*(..))")//从controller切入
//    public void operLogPoinCut() {
//    }
//
//    @Before("operLogPoinCut()")
//    public void beforMethod(JoinPoint point){
//        startTime.set(System.currentTimeMillis());
//    }
//
//    /**
//     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
//     */
//    @Pointcut("execution(* com.ewing.controller..*.*(..))")
//    public void operExceptionLogPoinCut() {
//    }
//
//    /**
//     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
//     *
//     * @param joinPoint 切入点
//     * @param result      返回结果
//     */
//    @AfterReturning(value = "operLogPoinCut()", returning = "result")
//    public void saveOperLog(JoinPoint joinPoint, Object result) throws BusinessException {
//        // 获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        // 从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//
//        try {
//            // 从切面织入点处通过反射机制获取织入点处的方法
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            // 获取切入点所在的方法
//            Method method = signature.getMethod();
//            // 获取操作
//            MyLog myLog = method.getAnnotation(MyLog.class);
//
//            LogTable operlog = new LogTable();
//            if (myLog != null) {
//                operlog.setTitle(myLog.title());//设置模块名称
//                operlog.setContent(myLog.content());//设置日志内容
//            }
//            // 将入参转换成json
//            String params = argsArrayToString(joinPoint.getArgs());
//            // 获取请求的类名
//            String className = joinPoint.getTarget().getClass().getName();
//            // 获取请求的方法名
//            String methodName = method.getName();
//            methodName = className + "." + methodName + "()";
//            operlog.setMethod(methodName); //设置请求方法
//            operlog.setRequestMethod(request.getMethod());//设置请求方式
//            operlog.setRequestParam(params); // 请求参数
//            operlog.setResponseResult(truncateString(JSON.toJSONString(result), 2000)); // 返回结果
//            operlog.setUserId(UserInfoContextHandler.getUserContext().getUserId());     // 获取用户ID
//            operlog.setOperationUser(UserInfoContextHandler.getUserContext().getUserName()); // 获取用户名
//            operlog.setIp(getIp(request)); // IP地址
//            operlog.setIpLocation(""); // IP归属地（真是环境中可以调用第三方API根据IP地址，查询归属地）
//            operlog.setRequestUrl(request.getRequestURI()); // 请求URI
//            operlog.setOperationTime(new Date()); // 时间
//            operlog.setStatus("0");//操作状态（0正常 1异常）
//            Long takeTime = System.currentTimeMillis() - startTime.get();//记录方法执行耗时时间（单位：毫秒）
//            operlog.setTakeTime(takeTime);
//            //插入数据库
//            logTableMapper.insert(operlog);
//        } catch (Exception e) {
//            ResultPage.FAIL(e.getMessage());
//        } finally {
//            //清理计时
//            startTime.remove();
//        }
//    }
//
//    /**
//     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
//     */
//    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
//    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
//        // 获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        // 从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//
//        LogTable operlog = new LogTable();
//        try {
//            // 从切面织入点处通过反射机制获取织入点处的方法
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            // 获取切入点所在的方法
//            Method method = signature.getMethod();
//            // 获取请求的类名
//            String className = joinPoint.getTarget().getClass().getName();
//            // 获取请求的方法名
//            String methodName = method.getName();
//            methodName = className + "." + methodName + "()";
//            // 获取操作
//            MyLog myLog = method.getAnnotation(MyLog.class);
//            if (myLog != null) {
//                operlog.setTitle(myLog.title());//设置模块名称
//                operlog.setContent(myLog.content());//设置日志内容
//            }
//            // 将入参转换成json
//            String params = argsArrayToString(joinPoint.getArgs());
//            operlog.setMethod(methodName); //设置请求方法
//            operlog.setRequestMethod(request.getMethod());//设置请求方式
//            operlog.setRequestParam(params); // 请求参数
////            operlog.setOperationUser("张三"); // 获取用户名（真实环境中，肯定有工具类获取当前登录者的账号或ID的，或者从token中解析而来）
//            operlog.setIp(getIp(request)); // IP地址
//            operlog.setUserId(UserInfoContextHandler.getUserContext().getUserId());     // 获取用户ID
//            operlog.setOperationUser(UserInfoContextHandler.getUserContext().getUserName()); // 获取用户名
//            operlog.setIpLocation(IpAddressUtil.getCityInfoByVectorIndex(getIp(request))); // IP归属地（真是环境中可以调用第三方API根据IP地址，查询归属地）
//            operlog.setRequestUrl(request.getRequestURI()); // 请求URI
//            operlog.setOperationTime(new Date()); // 时间
//            operlog.setStatus("1");//操作状态（0正常 1异常）
//            operlog.setErrorMessage(truncateString(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()), 2000));//记录异常信息
//            //插入数据库
//            logTableMapper.insert(operlog);
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }
//    }
//
//    /**
//     * 转换异常信息为字符串
//     */
//    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
//        StringBuffer strbuff = new StringBuffer();
//        for (StackTraceElement stet : elements) {
//            strbuff.append(stet + "\n");
//        }
//        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
//        message = substring(message,0 ,2000);
//        return message;
//    }
//
//
//    /**
//     *
//     * 限制插入的数据长度
//     */
//    private String truncateString(String str, int maxLength) {
//        if (str == null) {
//            return null;
//        }
//        return str.length() > maxLength ? str.substring(0, maxLength) : str;
//    }
//
//    /**
//     * 参数拼装
//     */
//    private String argsArrayToString(Object[] paramsArray)
//    {
//        String params = "";
//        if (paramsArray != null && paramsArray.length > 0)
//        {
//            for (Object o : paramsArray)
//            {
//                if (o != null)
//                {
//                    try
//                    {
//                        Object jsonObj = JSON.toJSON(o);
//                        params += jsonObj.toString() + " ";
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return params.trim();
//    }
//
//    //字符串截取
//    public static String substring(String str, int start, int end) {
//        if (str == null) {
//            return null;
//        } else {
//            if (end < 0) {
//                end += str.length();
//            }
//
//            if (start < 0) {
//                start += str.length();
//            }
//
//            if (end > str.length()) {
//                end = str.length();
//            }
//
//            if (start > end) {
//                return "";
//            } else {
//                if (start < 0) {
//                    start = 0;
//                }
//
//                if (end < 0) {
//                    end = 0;
//                }
//                return str.substring(start, end);
//            }
//        }
//    }
//
//    /**
//     * 转换request 请求参数
//     * @param paramMap request获取的参数数组
//     */
//    public Map<String, String> converMap(Map<String, String[]> paramMap) {
//        Map<String, String> returnMap = new HashMap<>();
//        for (String key : paramMap.keySet()) {
//            returnMap.put(key, paramMap.get(key)[0]);
//        }
//        return returnMap;
//    }
//
//    //根据HttpServletRequest获取访问者的IP地址
//    public static String getIp(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
//
//
//
//}







package com.ewing.aspect;

import com.alibaba.fastjson.JSON;
import com.ewing.annotation.MyLog;
import com.ewing.domain.entity.LogTable;
import com.ewing.mapper.LogTableMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import context.UserContext;
import context.UserInfoContextHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面处理类，记录操作日志到数据库
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OperLogAspect {

    private final LogTableMapper logTableMapper;

    /**
     * 设置操作日志切入点，基于自定义注解 @MyLog
     */
    @Pointcut("@annotation(com.ewing.annotation.MyLog)")
    public void operLogPoinCut() {
    }

    /**
     * 使用 @Around 切面，确保在方法执行前后都能访问到用户上下文
     */
    @Around("operLogPoinCut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 记录方法执行前的时间
        long startTime = System.currentTimeMillis();

        // 获取用户上下文
        UserContext userContext = UserInfoContextHandler.getUserContext();

        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        // 执行目标方法
        Object result = null;
        Throwable throwable = null;
        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable t) {
            throwable = t;
            throw t;
        } finally {
            try {
                // 获取切入点方法信息
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                Method method = signature.getMethod();
                MyLog myLog = method.getAnnotation(MyLog.class);

                LogTable operlog = new LogTable();
                if (myLog != null) {
                    operlog.setTitle(myLog.title()); // 设置模块名称
                    operlog.setContent(myLog.content()); // 设置日志内容
                }

                // 将入参转换成json
                String params = argsArrayToString(pjp.getArgs());

                // 获取请求的类名和方法名
                String className = pjp.getTarget().getClass().getName();
                String methodName = method.getName();
                methodName = className + "." + methodName + "()";
                operlog.setMethod(methodName); // 设置请求方法

                if (request != null) {
                    operlog.setRequestMethod(request.getMethod()); // 设置请求方式
                    operlog.setRequestParam(truncateString(params, 1000)); // 请求参数，限制长度
                    operlog.setIp(getIp(request)); // IP地址
                    operlog.setIpLocation(""); // IP归属地（真实环境中可以调用第三方API根据IP地址，查询归属地）
                    operlog.setRequestUrl(request.getRequestURI()); // 请求URI
                }

                // 返回结果或异常信息
                if (throwable == null) {
                    operlog.setResponseResult(truncateString(JSON.toJSONString(result), 2000)); // 返回结果，限制长度
                } else {
                    operlog.setErrorMessage(truncateString(stackTraceToString(throwable), 2000)); // 记录异常信息，限制长度
                }

                log.info("操作日志：{}", operlog);
//                log.info("用户id:{}",userContext.getUserId());
                if (userContext != null) {
                    operlog.setUserId(userContext.getUserId()); // 获取用户ID
                    operlog.setOperationUser(userContext.getUserName()); // 获取用户名
                } else {
                    // 创建 ObjectMapper 对象
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 将 JSON 字符串解析为 Map
                    Map<String, Object> jsonMap = objectMapper.readValue(params, Map.class);
                    // 提取 username
                    String username = (String) jsonMap.get("username");
                    operlog.setUserId("登录时未携带id，无法获取");
                    operlog.setOperationUser(username);
                }

                operlog.setOperationTime(new Date()); // 时间
                operlog.setStatus(throwable == null ? "0" : "1"); // 操作状态（0正常 1异常）
                Long takeTime = System.currentTimeMillis() - startTime; // 记录方法执行耗时时间（单位：毫秒）
                operlog.setTakeTime(takeTime);

                // 插入数据库
                logTableMapper.insert(operlog);
            } catch (Exception e) {
                log.error("记录操作日志失败", e);
            }
        }
    }




    /**
     * 转换异常信息为字符串
     */
    public String stackTraceToString(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        for (StackTraceElement stet : throwable.getStackTrace()) {
            sb.append(stet).append("\n");
        }
        String message = sb.toString();
        message = substring(message, 0, 2000); // 根据数据库字段长度调整
        return message;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (o != null) {
                    try {
                        if (o instanceof MultipartFile) {
                            MultipartFile file = (MultipartFile) o;
                            params.append("File {name: ").append(file.getOriginalFilename())
                                    .append(", size: ").append(file.getSize())
                                    .append("} ");
                        } else {
                            Object jsonObj = JSON.toJSON(o);
                            params.append(jsonObj.toString()).append(" ");
                        }
                    } catch (Exception e) {
                        log.error("参数转换失败", e);
                        params.append("[Unsupported type: ").append(o.getClass().getName()).append("] ");
                    }
                }
            }
        }
        return params.toString().trim();
    }


    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> returnMap = new HashMap<>();
        for (String key : paramMap.keySet()) {
            returnMap.put(key, paramMap.get(key)[0]);
        }
        return returnMap;
    }

        //字符串截取
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }
                return str.substring(start, end);
            }
        }
    }

    /**
     * 根据HttpServletRequest获取访问者的IP地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 截断字符串到指定长度
     */
    private String truncateString(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }
}

