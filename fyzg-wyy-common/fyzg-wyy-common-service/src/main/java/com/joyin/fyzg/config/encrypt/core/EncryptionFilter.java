package com.joyin.fyzg.config.encrypt.core;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.config.encrypt.algorithm.AesEncryptAlgorithm;
import com.joyin.fyzg.config.encrypt.algorithm.EncryptAlgorithm;
import com.joyin.fyzg.config.encrypt.config.PublicEncryptionInfo;
import com.joyin.fyzg.config.encrypt.ignore.IgnoreCheck;
import com.joyin.fyzg.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据加解密过滤器
 *
 * @author yinjihuan
 */
@Slf4j
@Component
public class EncryptionFilter implements Filter {

    @Autowired
    private PublicEncryptionInfo publicEncryptionInfo;

    @Autowired
    private IgnoreCheck ignoreCheck;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private EncryptAlgorithm encryptAlgorithm = new AesEncryptAlgorithm();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 加解密开关，true为打开，会进行加解密，false反之
        if (true) {
//        if (false) {
//        if (!publicEncryptionInfo.isEncryptSwitch()) {
            chain.doFilter(req, resp);
            return;
        }
        // 忽略判断，一般用以判断网关转发的情况下忽略加解密，如果进行加解密就会出现双重加解密（对应服务也会加解密一次）
        if (ignoreCheck.ignore(req)) {
            chain.doFilter(req, resp);
            return;
        }

        String uri = req.getRequestURI();
        boolean decryptionStatus = this.contains(ApiEncryptDataInit.requestDecryptUriList, uri, req.getMethod(), req);
        boolean encryptionStatus = this.contains(ApiEncryptDataInit.responseEncryptUriList, uri, req.getMethod(), req);
        boolean decryptionIgnoreStatus = this.contains(ApiEncryptDataInit.requestDecryptUriIgnoreList, uri, req.getMethod(), req);
        boolean encryptionIgnoreStatus = this.contains(ApiEncryptDataInit.responseEncryptUriIgnoreList, uri, req.getMethod(), req);

        // 没有配置具体加解密的URI默认全部都开启加解密
        if (CollectionUtils.isEmpty(ApiEncryptDataInit.requestDecryptUriList) && CollectionUtils.isEmpty(ApiEncryptDataInit.responseEncryptUriList)) {
            decryptionStatus = true;
            encryptionStatus = true;
        }

        // 接口在忽略加密列表中
        if (encryptionIgnoreStatus) {
            encryptionStatus = false;
        }

        // 接口在忽略解密列表中
        if (decryptionIgnoreStatus) {
            decryptionStatus = false;
        }

        // 没有加解密操作
        if (!decryptionStatus && !encryptionStatus) {
            chain.doFilter(req, resp);
            return;
        }

        EncryptionResponseWrapper responseWrapper = null;
        EncryptionRequestWrapper requestWrapper = null;
        try {
            // 配置了需要解密才处理
            if (decryptionStatus) {
                requestWrapper = new EncryptionRequestWrapper(req);
                requestDecryption(requestWrapper, req);
            }

            if (encryptionStatus) {
                responseWrapper = new EncryptionResponseWrapper(resp);
            }

            boolean needEncrypt = publicEncryptionInfo.isFormEncrypt();
            //必须先get一下，不然在输出之前流会被关闭
            response.getOutputStream();
            // 同时需要加解密
            if (encryptionStatus && needEncrypt && decryptionStatus) {
                chain.doFilter(requestWrapper, responseWrapper);
            } else if (encryptionStatus && needEncrypt) {
                // 只需要响应加密
                chain.doFilter(req, responseWrapper);
            } else if (decryptionStatus) {
                // 只需要请求解密
                chain.doFilter(requestWrapper, resp);
            }
        }
        catch (Exception e){
            log.error("加解密过滤器发生错误", e);
            if(null == responseWrapper){
                responseWrapper = new EncryptionResponseWrapper(resp);
            }
            String errorMsg= JsonUtils.obj2json(RestResponse.error(e.getMessage()));
            byte[] bytes = errorMsg.getBytes();
            responseWrapper.getOutputStream().write(bytes);
        }
        finally {
            // 配置了需要加密才处理
            if (encryptionStatus && publicEncryptionInfo.isFormEncrypt()) {
                String responseData = responseWrapper.getResponseData();
                responseEncryption(responseData, response);
            }
        }

    }

    /**
     * 请求解密处理
     *
     * @param requestWrapper
     * @param req
     */
    private void requestDecryption(EncryptionRequestWrapper requestWrapper, HttpServletRequest req) {
        String requestData = requestWrapper.getRequestData();
        String uri = req.getRequestURI();
        log.debug("RequestData: {}", requestData);
        try {
            Map<String, String[]> paramMap = new HashMap<>();
            requestWrapper.setParamMap(paramMap);
            if (!StringUtils.endsWithIgnoreCase(req.getMethod(), RequestMethod.GET.name())) {
                String decryptRequestData = encryptAlgorithm.decrypt(requestData, publicEncryptionInfo.getKey());
                log.debug("DecryptRequestData: {}", decryptRequestData);
                requestWrapper.setRequestData(decryptRequestData);
            }
            // url参数解密
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] paramValueAry = req.getParameterValues(paramName);
                for (int i = 0; i < paramValueAry.length; i++) {
                    paramValueAry[i] = encryptAlgorithm.decrypt(paramValueAry[i], publicEncryptionInfo.getKey());
                }
                paramMap.put(paramName, paramValueAry);
            }
        } catch (Exception e) {
            log.error("请求数据解密失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 输出加密内容
     *
     * @param responseData
     * @param response
     * @throws IOException
     */
    private void responseEncryption(String responseData, ServletResponse response) throws IOException {
        log.debug("ResponseData: {}", responseData);
        ServletOutputStream out = null;
        try {
            responseData = encryptAlgorithm.encrypt(responseData, publicEncryptionInfo.getKey());
            log.debug("EncryptResponseData: {}", responseData);
            response.setContentLength(responseData.length());
            response.setCharacterEncoding(publicEncryptionInfo.getResponseCharset());
            out = response.getOutputStream();
            out.write(responseData.getBytes(publicEncryptionInfo.getResponseCharset()));
        } catch (Exception e) {
            log.error("响应数据加密失败", e);
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    private boolean contains(List<String> list, String uri, String methodType, HttpServletRequest request) {
        if (list.contains(uri)) {
            return true;
        }
        String prefixUri = methodType.toLowerCase() + ":" + uri;
        log.debug("contains uri: {}", prefixUri);
        if (list.contains(prefixUri)) {
            return true;
        }

        // 优先用AntPathMatcher，其实用这个也够了，底层是一样的，下面用的方式兜底
        for (String u : list) {
            boolean match = antPathMatcher.match(u, prefixUri);
            if (match) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
