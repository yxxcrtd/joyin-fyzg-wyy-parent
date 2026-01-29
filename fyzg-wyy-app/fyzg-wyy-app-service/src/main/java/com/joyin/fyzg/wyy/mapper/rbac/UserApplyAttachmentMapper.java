package com.joyin.fyzg.wyy.mapper.rbac;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.SysLoginLogDO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAttachmentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserApplyAttachmentMapper extends SuperMapper<UserApplyAttachmentDO> {

    /**
     * 根据申请单 ID 查询所有附件
     */
    List<UserApplyAttachmentDO> selectByApplyId(@Param("applyId") String applyId);

    UserApplyAttachmentDO selectByAttachmentId(@Param("attachmentId") String attachmentId);

    void updateApplyIdByAttachmentIds(
            @Param("attachmentIds") List<String> attachmentIds,
            @Param("applyId") String applyId
    );

    /**
     * 根据 APPLY_ID 删除所有附件记录
     *
     * @param applyId 申请单ID
     */
    void deleteByApplyId(@Param("applyId") String applyId);

    /**
     * 根据 APPLY_ID 批量删除多个申请单的附件（可选扩展）
     */
    void deleteByApplyIds(@Param("applyIds") List<String> applyIds);

    /**
     * 分页查询（可选）
     */
    IPage<UserApplyAttachmentDO> selectPageByApplyId(Page<UserApplyAttachmentDO> page, @Param("applyId") String applyId);
}
