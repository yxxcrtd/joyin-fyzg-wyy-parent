package com.joyin.fyzg.wyy.controller.messageNotice;


import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageFinancierGroupInfoDO;
import com.joyin.fyzg.wyy.service.messageNotice.MessageUserGroupInfoService;
import com.joyin.fyzg.wyy.vo.MessageNotice.MessageFinancierGroupInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("MessageUserGroupInfo")
@Slf4j
public class MessageUserGroupInfoController {

    @Autowired
    private MessageUserGroupInfoService messageUserGroupInfoService;

    //查询用户分组信息
    @GetMapping("list")
    public RestResponse<List<MessageFinancierGroupInfoDO>> getAllMessageUserGroupInfo(@RequestParam("groupName") String groupName) {
        return RestResponse.transMethodResponse(messageUserGroupInfoService.queryMessageUserGroupInfo(groupName));
    }

    //分页查询用户分组信息
    @GetMapping("listPage")
    public RestResponse<PageResponse<MessageFinancierGroupInfoVO>> getAllMessageUserGroupInfoByPage(@RequestParam("groupName") String groupName, HttpServletRequest httpServletRequest) {
        PageWrapper pageWrapper = PageWrapper.builderPageWrapper(httpServletRequest);
        return RestResponse.success(messageUserGroupInfoService.queryMessageUserGroupInfoByPage(groupName, pageWrapper));
    }

    //用户分组信息id查询管理员下的所有用户
    @PostMapping("userInfoList")
    public RestResponse<List<MessageFinancierGroupInfoVO>> getAllMessageUserGroupInfo(@RequestBody List<Long> groupIds) {
        return RestResponse.transMethodResponse(messageUserGroupInfoService.queryUserCodeByGroupIds(groupIds));
    }

    //新增用户分组信息
    @PostMapping("add")
    public RestResponse<MessageFinancierGroupInfoDO> addMessageUserGroupInfo(@RequestBody MessageFinancierGroupInfoDO messageUserGroupInfoDO) {
        return RestResponse.transMethodResponse(messageUserGroupInfoService.insertMessageUserGroupInfo(messageUserGroupInfoDO));
    }

    //修改用户分组信息
    @PostMapping("update")
    public RestResponse<MessageFinancierGroupInfoDO> updateMessageUserGroupInfo(@RequestBody MessageFinancierGroupInfoDO messageUserGroupInfoDO) {
        return RestResponse.transMethodResponse(messageUserGroupInfoService.updateMessageUserGroupInfo(messageUserGroupInfoDO));
    }

    //删除用户分组信息
    @PostMapping("delete")
    public RestResponse<MessageFinancierGroupInfoDO> deleteMessageUserGroupInfo(@RequestBody List<Long> idList) {
        return RestResponse.transMethodResponse(messageUserGroupInfoService.batchDeleteMessageUserGroupInfo(idList));
    }

    //上传导入文件
    @PostMapping("/uploadExcel")
    public RestResponse upload(@RequestParam("file") MultipartFile file) {
        List<String> errorList;
        try {
            errorList = messageUserGroupInfoService.uploadTemp(file);
        } catch (IOException e) {
            return RestResponse.error("文件上传失败: " + e.getMessage());
        }
        return RestResponse.success(errorList);
    }
}
