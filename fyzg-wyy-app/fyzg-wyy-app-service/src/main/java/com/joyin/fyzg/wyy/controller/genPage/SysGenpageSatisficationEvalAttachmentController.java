package com.joyin.fyzg.wyy.controller.genPage;


import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.service.genPage.impl.SysGenpageSatisficationEvalAttachmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("SysGenpageSatisficationEvalAttachment")
@Slf4j
public class SysGenpageSatisficationEvalAttachmentController {

    @Autowired
    private SysGenpageSatisficationEvalAttachmentServiceImpl sysGenpageSatisficationEvalAttachmentServiceImpl;


    @PostMapping("/uploadAttachment")
    public RestResponse<String> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "userId") String userId, @RequestParam(value = "pageId") String pageId) {
        String uuid;
        try {
            uuid = sysGenpageSatisficationEvalAttachmentServiceImpl.uploadTemp(file, userId, pageId);
        } catch (IOException e) {
            return RestResponse.error("文件上传失败: " + e.getMessage());
        }
        return RestResponse.success(uuid);
    }

    /**
     * 流程附件下载
     */
    @PostMapping("/downloadAttachment")
    public void download(@RequestBody String id,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        // 调用服务层获取资源
        Resource resource = sysGenpageSatisficationEvalAttachmentServiceImpl.download(id);
        if (resource == null || !resource.exists()) {
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("附件不存在或已被删除");
            return;
        }

        // 尝试获取文件名和内容类型
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException | UnsupportedOperationException ex) {
            // 忽略异常，使用默认类型
            contentType = "application/octet-stream";
        }
        // 如果无法从资源获取文件名，则使用默认名
        String originalFilename;
        try {
            originalFilename = resource.getFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                originalFilename = "download";
            }
        } catch (Exception e) {
            originalFilename = "download";
        }

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(originalFilename, "UTF-8"));

        // 3. 写出文件流
        try (InputStream in = new FileInputStream(resource.getFile());
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            while (in.read(buffer) != -1) {
                out.write(buffer);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("下载失败：" + e.getMessage());
        }
    }


    @GetMapping("/deleteTempAttachment")
    public RestResponse<Boolean> deleteTempAttachment(@RequestParam(value = "id") String id) {
        boolean result = sysGenpageSatisficationEvalAttachmentServiceImpl.deleteTempAttachment(id);
        return RestResponse.success(result);
    }


}
