package com.joyin.fyzg.wyy.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAuditDO;
import com.joyin.fyzg.wyy.mapper.rbac.UserApplyAuditMapper;
import com.joyin.fyzg.wyy.service.user.UserApplyAuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class UserApplyAuditServiceImpl implements UserApplyAuditService {
    @Autowired
    private UserApplyAuditMapper auditMapper;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String now() {
        return LocalDateTime.now().format(DTF);
    }

   /* @Override
    @Transactional
    public boolean startAuditProcess(String applyId, List<String> auditors) {
        for (int i = 0; i < auditors.size(); i++) {
            UserApplyAuditDO audit = new UserApplyAuditDO();
            audit.setApplyId(applyId);
            audit.setAuditLevel(String.valueOf(i + 1));
            audit.setAuditor(auditors.get(i));
            audit.setCreateTime(now());
            auditMapper.insert(audit);
        }
        return true;
    }*/

    @Override
    public boolean audit(String applyId, String auditor, String auditRemark) {
        UserApplyAuditDO userApplyAuditDO = new UserApplyAuditDO();

        userApplyAuditDO.setApplyId(applyId);
        userApplyAuditDO.setAuditor(auditor);
        userApplyAuditDO.setAuditTime(DateUtil8.getNowTime_EN());
        userApplyAuditDO.setAuditRemark(auditRemark);

        return auditMapper.insert(userApplyAuditDO) > 0;
    }

    /**
     * 检查是否所有审批都通过，若通过则关闭申请
     */
    /*private void checkAndCloseApply(String applyId) {
        QueryWrapper<UserApplyAuditDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyAuditDO::getApplyId, applyId)
                .isNull(UserApplyAuditDO::getAuditResult);

        List<UserApplyAuditDO> pending = auditMapper.selectList(query);
        if (pending.isEmpty()) {
            // 所有审批完成，更新主表状态
            QueryWrapper<UserApplyDO> updateQuery = new QueryWrapper<>();
            updateQuery.lambda()
                    .eq(com.example.rbac.domain.UserApplyDO::getApplyId, applyId)
                    .eq(com.example.rbac.domain.UserApplyDO::getStatus, "SUBMITTED");

            com.example.rbac.domain.UserApplyDO apply = new com.example.rbac.domain.UserApplyDO();
            apply.setStatus("APPROVED");
            apply.setUpdateTime(now());
            // 实际项目中应注入 UserApplyMapper
        }
    }*/

    @Override
    public List<UserApplyAuditDO> listPendingAudits(String auditor) {
        return auditMapper.selectPendingAudits(auditor);
    }

    @Override
    public List<UserApplyAuditDO> listAuditHistory(String applyId) {
        return auditMapper.selectByApplyId(applyId);
    }
}
