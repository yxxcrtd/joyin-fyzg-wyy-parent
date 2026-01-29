package com.joyin.fyzg.wyy.mapper.rbac;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAttachmentDO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAuditDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserApplyAuditMapper extends SuperMapper<UserApplyAuditDO> {
    /**
     * 根据申请ID查询所有审批记录（按审批时间升序）
     */
    List<UserApplyAuditDO> selectByApplyId(String applyId);

    /**
     * 查询当前待审批的记录（用于待办列表）
     */
    List<UserApplyAuditDO> selectPendingAudits(String auditor);

}
