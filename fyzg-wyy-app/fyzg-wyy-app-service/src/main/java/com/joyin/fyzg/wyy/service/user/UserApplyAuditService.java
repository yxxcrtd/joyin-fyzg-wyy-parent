package com.joyin.fyzg.wyy.service.user;

import com.joyin.fyzg.wyy.entity.rbac.UserApplyAuditDO;

import java.util.List;

public interface UserApplyAuditService {
    /**
     * 发起审批流程（为申请创建多级审批任务）
     */
    //boolean startAuditProcess(String applyId, List<String> auditors);

    /**
     * 审批操作（通过/驳回）
     */
    boolean audit(String applyId, String auditor, String auditRemark);

    /**
     * 查询我的待办审批
     */
    List<UserApplyAuditDO> listPendingAudits(String auditor);

    /**
     * 查询某申请的所有审批记录
     */
    List<UserApplyAuditDO> listAuditHistory(String applyId);
}
