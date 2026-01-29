package com.joyin.fyzg.wyy.service.user;

import com.joyin.fyzg.wyy.common.dto.UserApplyDTO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAuditDO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyDO;
import com.joyin.fyzg.wyy.vo.rbac.UserApplyVO;

import java.util.List;

public interface UserApplyService {
    /**
     * 创建草稿申请
     */
    //String createDraft(UserApplyDO apply);

    /**
     * 提交申请（转为待审批）
     */
    String submitApply(UserApplyDTO userApplyDTO);

    /**
     * 查询我的申请列表
     */
    List<UserApplyDO> listMyApplies(String applicant, String status);

    /**
     * 根据申请ID查询申请详情（含审批记录）
     */
    UserApplyVO getApplyDetail(String applyId);

    /**
     * 撤回申请（仅限已提交未审批完）
     */
    boolean withdrawApply(String applyId, String applicant);

    /**
     * 删除草稿
     */
    boolean deleteDraft(String applyId, String applicant);

    /**
     * 复核
     */
    boolean approveUserApply(String applyId, String auditRemark, String loginUserCode, String account, UserApplyAuditDO userApplyAuditDO);
}
