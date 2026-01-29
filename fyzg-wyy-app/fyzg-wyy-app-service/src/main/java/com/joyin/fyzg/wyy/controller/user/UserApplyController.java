package com.joyin.fyzg.wyy.controller.user;

import com.joyin.fyzg.common.BaseController;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.common.dto.UserApplyDTO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAttachmentDO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAuditDO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.service.user.UserApplyAttachmentService;
import com.joyin.fyzg.wyy.service.user.UserApplyService;
import com.joyin.fyzg.wyy.vo.rbac.UserApplyVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userApply")
public class UserApplyController extends BaseController {

    @Autowired
    private UserApplyService userApplyService;

    @Autowired
    private UserApplyAttachmentService attachmentService;

    /**
     *  流程上传文件
     * */
    @PostMapping("/uploadAttachment")
    public RestResponse<UserApplyAttachmentDO> upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "type",required = false) String type) {
        UserApplyAttachmentDO userApplyAttachmentDO;
        try {
            userApplyAttachmentDO = attachmentService.uploadTemp(file,type);
        } catch (IOException e) {
            return RestResponse.error("文件上传失败: " + e.getMessage());
        }
        return RestResponse.success(userApplyAttachmentDO);
    }

    /**
     *  流程多文件上传
     * */
    @PostMapping("/uploadAttachmentBatch")
    public RestResponse<List<UserApplyAttachmentDO>> uploadBatch(@RequestParam("file") MultipartFile[] files, @RequestParam(value = "type",required = false) String type) {
        List<UserApplyAttachmentDO> userApplyAttachmentDOList = new ArrayList<>();
        try {
            for(int i= 0;i<files.length;i++) {
                userApplyAttachmentDOList.add(attachmentService.uploadTemp(files[i],type));
            }

        } catch (IOException e) {
            return RestResponse.error("文件上传失败: " + e.getMessage());
        }
        return RestResponse.success(userApplyAttachmentDOList);
    }

    /**
     *  流程附件下载
     * */
    @PostMapping("/downloadAttachment")
    public void download(@RequestBody UserApplyAttachmentDO userApplyAttachmentDO,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws IOException {

        // 调用服务层获取资源
        Resource resource = attachmentService.download(userApplyAttachmentDO.getAttachmentId());
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

    /**
     * 提交用户新增申请
     */
    @PostMapping("/submit")
    public RestResponse<Map<String, Object>> submitUserApply(
            @RequestBody UserApplyDTO userInfo) {
        try {
            String applyId = userApplyService.submitApply(userInfo);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "申请提交成功");
            response.put("applyId", applyId);
            return RestResponse.success(response);
        } catch (Exception e) {
            return RestResponse.error("申请提交失败: " + e.getMessage());
        }
    }

    /**
     * 获取待审批列表
     */
    /*@GetMapping("/pending")
    public RestResponse<Map<String, Object>> getPendingApprovals(
            @RequestParam String applyId) {

        try {
            List<UserApplyDO> pendingList = userApplyService.getPendingApprovalList();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", pendingList);

            return RestResponse.success(response);
        } catch (Exception e) {
            return RestResponse.error("获取待审批列表失败: " + e.getMessage());
        }
    }*/

    /**
     * 审批用户申请
     */
    @PostMapping("/approve")
    public RestResponse<Map<String, Object>> approveUserApply(@RequestBody UserApplyAuditDO userApplyAuditDO //@RequestParam("applyId") String applyId, @RequestParam("auditRemark") String auditRemark
            ) {

        try {
            boolean result = userApplyService.approveUserApply(userApplyAuditDO.getApplyId(),userApplyAuditDO.getAuditRemark(),super.getLoginUserCode(),userApplyAuditDO.getAccount(),userApplyAuditDO);
            Map<String, Object> response = new HashMap<>();
            if (StringUtils.equals(userApplyAuditDO.getApproveFlag(),"1")) {
                response.put("success", true);
                response.put("message", "审批成功");
            } else if (StringUtils.equals(userApplyAuditDO.getApproveFlag(),"0")) {
                response.put("success", true);
                response.put("message", "退回成功");
            } else {
                response.put("success", false);
                response.put("message", "审批失败");
            }
            return RestResponse.success(response);
        } catch (Exception e) {
            return RestResponse.error("审批失败: " + e.getMessage());
        }
    }

    /**
     * 获取申请详情
     */
    @GetMapping("/getDetailByAppId")
    public RestResponse<UserApplyVO> getApplyDetail(@RequestParam String applyId) {
        try {
            UserApplyVO userApplyVO= userApplyService.getApplyDetail(applyId);
            return RestResponse.success(userApplyVO);
        } catch (Exception e) {
            return RestResponse.error("获取审批详情失败: " + e.getMessage());

        }
    }

    /**
     * 获取用户申请历史
     */
    /*@GetMapping("/history")
    public RestResponse<Map<String, Object>> getUserApplyHistory(
            @RequestHeader("X-User-Id") String userId) {
        try {
            List<FlowMain> history = userApplyFlowService.getUserApplyHistory(Long.valueOf(userId));

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", history);

            return RestResponse.success(response);
        } catch (Exception e) {
            return RestResponse.error("获取历史失败: " + e.getMessage());
        }
    }*/


}
