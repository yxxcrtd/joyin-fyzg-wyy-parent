package com.joyin.fyzg.wyy.mapper.rbac;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyAuditDO;
import com.joyin.fyzg.wyy.entity.rbac.UserApplyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserApplyMapper extends SuperMapper<UserApplyDO> {
    /**
     * 根据申请ID查询申请详情
     */
    UserApplyDO selectByApplyId(String applyId);

    /**
     * 分页查询申请列表（可根据申请人、状态等过滤）
     */
    List<UserApplyDO> selectPageByCondition(UserApplyDO condition);
}
