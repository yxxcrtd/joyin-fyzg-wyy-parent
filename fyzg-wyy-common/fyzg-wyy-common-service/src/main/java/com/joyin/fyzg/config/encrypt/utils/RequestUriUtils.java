package com.joyin.fyzg.config.encrypt.utils;

import com.google.common.collect.Lists;
import com.joyin.fyzg.config.encrypt.constant.Constants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

public class RequestUriUtils {

    private static String separator = "/";

    public static List<String> getApiUri(Class<?> clz, Method method, String contextPath) {
        List<String> list= Lists.newArrayList();
        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
        if (reqMapping != null && reqMapping.value() != null && reqMapping.value().length > 0) {
            for (String prefix : reqMapping.value()) {
                StringBuilder uri = new StringBuilder();
                uri.append(formatUri(prefix));
                list.add(getMethodUri(method, contextPath, uri));
            }
        }else{
            StringBuilder uri = new StringBuilder();
            list.add(getMethodUri(method, contextPath, uri));
        }
        return list;
    }

    private static String getMethodUri(Method method, String contextPath, StringBuilder uri) {
        String methodType = "";
        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);

        if (getMapping != null && getMapping.value() != null && getMapping.value().length > 0) {
            methodType = Constants.GET;
            uri.append(formatUri(getMapping.value()[0]));

        } else if (postMapping != null && postMapping.value() != null && postMapping.value().length > 0) {
            methodType = Constants.POST;
            uri.append(formatUri(postMapping.value()[0]));

        } else if (putMapping != null && putMapping.value() != null && putMapping.value().length > 0) {
            methodType = Constants.PUT;
            uri.append(formatUri(putMapping.value()[0]));

        } else if (deleteMapping != null && deleteMapping.value() != null && deleteMapping.value().length > 0) {
            methodType = Constants.DELETE;
            uri.append(formatUri(deleteMapping.value()[0]));

        } else if (requestMapping != null && requestMapping.value() != null && requestMapping.value().length > 0) {
            RequestMethod requestMethod = RequestMethod.GET;
            if (requestMapping.method().length > 0) {
                requestMethod = requestMapping.method()[0];
            }

            methodType = requestMethod.name().toLowerCase() + ":";
            uri.append(formatUri(requestMapping.value()[0]));

        }

        if (StringUtils.hasText(contextPath) && !separator.equals(contextPath)) {
            if (contextPath.endsWith(separator)) {
                contextPath = contextPath.substring(0, contextPath.length() - 1);
            }
            return methodType + contextPath + uri.toString();
        }

        return methodType + uri.toString();
    }

//    public static String getApiUri1(Class<?> clz, Method method, String contextPath) {
//        String methodType = "";
//        StringBuilder uri = new StringBuilder();
//
//        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
//        if (reqMapping != null && reqMapping.value() != null && reqMapping.value().length > 0) {
//            uri.append(formatUri(reqMapping.value()[0]));
//        }
//
//
//        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
//        PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
//        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
//        PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
//        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
//
//        if (getMapping != null && getMapping.value() != null && getMapping.value().length > 0) {
//            methodType = Constants.GET;
//            uri.append(formatUri(getMapping.value()[0]));
//
//        } else if (postMapping != null && postMapping.value() != null && postMapping.value().length > 0) {
//            methodType = Constants.POST;
//            uri.append(formatUri(postMapping.value()[0]));
//
//        } else if (putMapping != null && putMapping.value() != null && putMapping.value().length > 0) {
//            methodType = Constants.PUT;
//            uri.append(formatUri(putMapping.value()[0]));
//
//        } else if (deleteMapping != null && deleteMapping.value() != null && deleteMapping.value().length > 0) {
//            methodType = Constants.DELETE;
//            uri.append(formatUri(deleteMapping.value()[0]));
//
//        } else if (requestMapping != null && requestMapping.value() != null && requestMapping.value().length > 0) {
//            RequestMethod requestMethod = RequestMethod.GET;
//            if (requestMapping.method().length > 0) {
//                requestMethod = requestMapping.method()[0];
//            }
//
//            methodType = requestMethod.name().toLowerCase() + ":";
//            uri.append(formatUri(requestMapping.value()[0]));
//
//        }
//
//        if (StringUtils.hasText(contextPath) && !separator.equals(contextPath)) {
//            if (contextPath.endsWith(separator)) {
//                contextPath = contextPath.substring(0, contextPath.length() - 1);
//            }
//            return methodType + contextPath + uri.toString();
//        }
//
//        return methodType + uri.toString();
//    }

    private static String formatUri(String uri) {
        if (uri.startsWith(separator)) {
            return uri;
        }
        return separator + uri;
    }
}
