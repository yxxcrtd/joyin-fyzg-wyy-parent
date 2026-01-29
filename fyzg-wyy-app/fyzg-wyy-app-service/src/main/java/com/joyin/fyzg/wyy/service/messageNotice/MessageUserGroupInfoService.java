package com.joyin.fyzg.wyy.service.messageNotice;


import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageFinancierGroupInfoDO;
import com.joyin.fyzg.wyy.vo.MessageNotice.MessageFinancierGroupInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【SYS_MESSAGE_USER_GROUP_INFO(用户分组信息表)】的数据库操作Service
 * @createDate 2025-10-13 17:17:13
 */
public interface MessageUserGroupInfoService {
    MethodResponse queryMessageUserGroupInfo(String groupName);

    PageResponse<MessageFinancierGroupInfoVO> queryMessageUserGroupInfoByPage(String groupName, PageWrapper pageWrapper);

    MethodResponse queryUserCodeByGroupIds(List<Long> groupIds);

    MethodResponse insertMessageUserGroupInfo(MessageFinancierGroupInfoDO messageUserGroupInfo);

    MethodResponse updateMessageUserGroupInfo(MessageFinancierGroupInfoDO messageUserGroupInfo);

    MethodResponse batchDeleteMessageUserGroupInfo(List<Long> idList);

    List<String> uploadTemp(MultipartFile file) throws IOException ;

}
