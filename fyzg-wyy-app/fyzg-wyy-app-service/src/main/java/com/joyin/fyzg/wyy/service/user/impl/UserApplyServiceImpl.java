package com.joyin.fyzg.wyy.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.common.dto.UserApplyDTO;
import com.joyin.fyzg.wyy.common.utils.UuidGenerator;
import com.joyin.fyzg.wyy.entity.rbac.*;
import com.joyin.fyzg.wyy.mapper.rbac.UserApplyAttachmentMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserApplyAuditMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserApplyMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserMapper;
import com.joyin.fyzg.wyy.service.common.CommonService;
import com.joyin.fyzg.wyy.service.rbac.AccountService;
import com.joyin.fyzg.wyy.service.user.UserApplyAttachmentService;
import com.joyin.fyzg.wyy.service.user.UserApplyAuditService;
import com.joyin.fyzg.wyy.service.user.UserApplyService;
import com.joyin.fyzg.wyy.vo.rbac.UserApplyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserApplyServiceImpl implements UserApplyService {

    private final UserApplyMapper userApplyMapper;
    private final UserApplyAuditMapper userApplyAuditMapper;
    private final UserApplyAttachmentService attachmentService;
    private final UuidGenerator idGenerator;
    private UserApplyAttachmentMapper attachmentMapper;

    @Autowired
    CommonService commonService;
    private static final String APPLY_PENDING  = "PENDING";  //待审批
    private static final String APPLY_REJECTED  = "REJECTED";  //已驳回
    private static final String APPLY_APPROVED  = "APPROVED";  //已审批

    @Autowired
    private UserApplyMapper applyMapper;

    @Autowired
    private UserApplyAuditService auditService;

    @Autowired
    private UserApplyAttachmentService applyAttachmentService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountService accountService;


    @Value("${user.password}")
    public String userDefaultPassword;


    /* @Override
    @Transactional
   public String createDraft(UserApplyDO apply) {
        String applyId = java.util.UUID.randomUUID().toString().replace("-", "");
        apply.setApplyId(applyId);
        apply.setStatus("DRAFT");
        apply.setCreateTime(now());
        apply.setUpdateTime(now());

        int result = applyMapper.insert(apply);
        if (result == 0) {
            throw new RuntimeException("保存草稿失败");
        }
        return applyId;
    }*/

    @Override
    @Transactional
    public String submitApply(UserApplyDTO userApplyDTO) {
        if ("2".equals(userApplyDTO.getUserType()) && StringUtils.isBlank(userApplyDTO.getAttachmentId())) {
            throw new RuntimeException("划款用户申请未提交附件");
        }
        if (StringUtils.equals(userApplyDTO.getUserType(),"0") && !StringUtils.equals(userApplyDTO.getModiAdmin(),"1")) {
            //同一个管理人只有一个管理员
            QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
            query.lambda().eq(UserDO::getFinancier, userApplyDTO.getFinancier());
            query.lambda().eq(UserDO::getUserType, "0");
            List<UserDO> userDOList = userMapper.selectList(query);
            if (CollectionUtils.isNotEmpty(userDOList)) {
                throw new IllegalArgumentException("用户类型选择错误，当前管理人已经存在管理员！");
            }
        }
        UserApplyDO userApplyDO = new UserApplyDO();
        if (StringUtils.equals(userApplyDTO.getModiAdmin(),"1")) {
            QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
            query.lambda().eq(UserDO::getUserOName, userApplyDTO.getUserOName());
            query.lambda().eq(UserDO::getUserOCode, userApplyDTO.getUserOCode());
            List<UserDO> userDOList = userMapper.selectList(query);
            if (CollectionUtils.isNotEmpty(userDOList)) {
                log.info("调整管理员用户为：{}",userDOList.get(0).getUserOName());
                BeanUtils.copyProperties(userDOList.get(0), userApplyDTO);
            }
        }
        String applyId = java.util.UUID.randomUUID().toString().replace("-", "");
        BeanUtils.copyProperties(userApplyDTO,userApplyDO);
        userApplyDO.setApplyId(applyId);
        userApplyDO.setStatus("PENDING");
        userApplyDO.setApplyTime(DateUtil8.getNowTime_EN());
        userApplyDO.setAccount(userApplyDTO.getMobile());
        if(StringUtils.isBlank(userApplyDTO.getUserOName())){
            userApplyDO.setUserOName(userApplyDTO.getMobile());
        }
        if (!StringUtils.equals(userApplyDTO.getModiAdmin(),"1")) {
            userApplyDO.setUserOCode(commonService.getOCodeNextVal());
        }
        userApplyDO.setModiAdminFlag(StringUtils.defaultIfBlank(userApplyDTO.getModiAdmin(),"0"));
        int result = applyMapper.insert(userApplyDO);
        if (result == 0) {
            throw new RuntimeException("提交申请失败");
        }
        //绑定附件信息
        if (StringUtils.isNotBlank(userApplyDTO.getAttachmentId())) {
            // 绑定附件
            boolean associate = applyAttachmentService.associateToApply(userApplyDTO.getAttachmentId(), applyId);
            if (!associate) {
                throw new RuntimeException("附件绑定流程失败");
            }
        }
        return applyId;
    }

    @Override
    public List<UserApplyDO> listMyApplies(String applicant, String status) {
        QueryWrapper<UserApplyDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyDO::getApplicant, applicant)
                .eq(status != null && !status.isEmpty(), UserApplyDO::getStatus, status)
                .ne(UserApplyDO::getStatus, "DELETED")
                .orderByDesc(UserApplyDO::getApplyTime);

        return applyMapper.selectList(query);
    }

    @Override
    public UserApplyVO getApplyDetail(String applyId) {
        QueryWrapper<UserApplyDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserApplyDO::getApplyId,applyId);
        UserApplyDO apply = applyMapper.selectOne(queryWrapper);
        UserApplyVO userApplyVO = new UserApplyVO();

        List<UserApplyAttachmentDO> userApplyAttachmentDOS = applyAttachmentService.listByApplyId(applyId);
        if(CollectionUtils.isEmpty(userApplyAttachmentDOS)){
            throw new RuntimeException("获取附件失败");
        }

        BeanUtils.copyProperties(apply,userApplyVO);
        userApplyVO.setAttachments(userApplyAttachmentDOS);

       /* if (apply != null) {
            List<UserApplyAuditDO> audits = auditService.listAuditHistory(applyId);
            // 可通过 VO 设置，此处省略
        }*/
        return userApplyVO;
    }

    @Override
    @Transactional
    public boolean withdrawApply(String applyId, String applicant) {
        QueryWrapper<UserApplyDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyDO::getApplyId, applyId)
                .eq(UserApplyDO::getApplicant, applicant)
                .eq(UserApplyDO::getStatus, "SUBMITTED");

        UserApplyDO apply = applyMapper.selectOne(query);
        if (apply == null) {
            return false;
        }

        apply.setStatus("WITHDRAWN");
        return applyMapper.updateById(apply) > 0;
    }

    @Override
    public boolean deleteDraft(String applyId, String applicant) {
        QueryWrapper<UserApplyDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyDO::getApplyId, applyId)
                .eq(UserApplyDO::getApplicant, applicant)
                .eq(UserApplyDO::getStatus, "DRAFT");

        return applyMapper.delete(query) > 0;
    }

    @Override
    @Transactional
    public boolean approveUserApply(String applyId,String auditRemark,String loginUserCode,String account,UserApplyAuditDO userApplyAuditDO) {
        boolean result = false;
        //1、查询流程状态是否已被审批过
        QueryWrapper<UserApplyDO> query = new QueryWrapper<>();
        query.lambda()
                .eq(UserApplyDO::getApplyId, applyId);

        UserApplyDO apply = applyMapper.selectOne(query);
        if (StringUtils.equals(apply.getStatus(),"APPLY_APPROVED")) {
            throw new RuntimeException("流程已被审核");
        }

        //2、修改流程状态
        UserApplyDO userApplyDO = new UserApplyDO();
        if (StringUtils.equals(userApplyAuditDO.getApproveFlag(),"1")) {
            userApplyDO.setStatus(APPLY_APPROVED);
        }else{
            userApplyDO.setStatus(APPLY_REJECTED);
        }
        //userApplyDO.setApplicant();
        LambdaUpdateWrapper<UserApplyDO> updateWrapper = new UpdateWrapper<UserApplyDO>()
                .lambda()
                .eq(UserApplyDO::getApplyId, applyId);

        result =  applyMapper.update(userApplyDO,updateWrapper) > 0;
        if(!result){
            throw new RuntimeException("更新流程状态失败");
        }
        //3、写入审核记录表
        result = auditService.audit(applyId, loginUserCode, auditRemark);
        if(!result){
            throw new RuntimeException("写入审核记录表失败");
        }
        if (StringUtils.equals(userApplyAuditDO.getApproveFlag(),"1")) {
            if (StringUtils.equals(apply.getModiAdminFlag(),"1")) {
                //调整管理员的场景
                QueryWrapper<UserDO> queryAdminOld = new QueryWrapper<UserDO>();
                queryAdminOld.lambda().eq(UserDO::getFinancier, userApplyAuditDO.getFinancier());
                queryAdminOld.lambda().eq(UserDO::getUserType, "0");
                queryAdminOld.lambda().eq(UserDO::getEnabled,"1");
                List<UserDO> adminList = userMapper.selectList(queryAdminOld);
                if (CollectionUtils.isNotEmpty(adminList)) {
                    UserDO userAdmin = adminList.get(0);
                    log.info("原有管理员用户为：{}",userAdmin.getUserOName());
                    userAdmin.setUserType("1");
                    userAdmin.setJyUpdateTime(DateUtil8.getNowTime_EN());
                    userMapper.updateById(userAdmin);
                }
                QueryWrapper<UserDO> queryAdminNew = new QueryWrapper<UserDO>();
                queryAdminNew.lambda().eq(UserDO::getUserOName, userApplyAuditDO.getUserOName());
                queryAdminNew.lambda().eq(UserDO::getUserOCode, userApplyAuditDO.getUserOCode());
                queryAdminNew.lambda().eq(UserDO::getUserType, "1");
                //queryAdminNew.lambda().eq(UserDO::getEnabled,"1");
                List<UserDO> userDOList = userMapper.selectList(queryAdminNew);
                if (CollectionUtils.isNotEmpty(userDOList)) {
                    UserDO userDO = userDOList.get(0);
                    log.info("新管理员用户为：{}",userDO.getUserOName());
                    userDO.setJyUpdateTime(DateUtil8.getNowTime_EN());
                    userDO.setUserType("0");
                    userMapper.updateById(userDO);
                }
            }else {
                UserDO userDO = new UserDO();
                BeanUtils.copyProperties(userApplyAuditDO,userDO);
                userDO.setEnabled("1");
                userDO.setUserOCode(commonService.getOCodeNextVal());
                userDO.setJyInsertTime(DateUtil8.getNowTime_EN());
                if(StringUtils.isBlank(userDO.getUserOName())){
                    userDO.setUserOName(userDO.getAccount());
                }
                userMapper.insert(userDO);
                AccountDO accountByAccount = accountService.getAccountByAccount(userApplyAuditDO.getMobile());
                if (accountByAccount == null) {
                    BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
                    //TODO 临时密码设置为111111
                    AccountDO accountDO = AccountDO.builder()
                            .password(bcp.encode(StringUtils.defaultIfBlank(userDefaultPassword,"111111")))
                            .build();
                    accountDO.setAccount(userApplyAuditDO.getMobile());
                    accountDO.setEnabled("1");
                    accountService.insertAccount(accountDO);
                }
            }
        }

        return result;
    }

}
