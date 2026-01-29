package com.joyin.fyzg.wyy.service.wf.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.wyy.entity.wf.WfPhraseDO;
import com.joyin.fyzg.wyy.mapper.wf.WfPhraseMapper;
import com.joyin.fyzg.wyy.service.wf.WfPhraseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
@Transactional
public class WfPhraseServiceImpl implements WfPhraseService {

	@Autowired
    WfPhraseMapper wfWfPhraseMapper;

    @Override
    public MethodResponse insertPhrase(WfPhraseDO actCcDO) {
        try {
            wfWfPhraseMapper.insert(actCcDO);
            return MethodResponse.success(actCcDO.getRId());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(INSERT_FAIL.formatEntity(WfPhraseDO.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(INSERT_FAIL.formatEntity(WfPhraseDO.class, e), null);
        }
    }

    @Override
    public MethodResponse updatePhraseById(WfPhraseDO actCcDO) {
        try {
            wfWfPhraseMapper.updateById(actCcDO);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(UPDATE_FAIL.formatEntity(WfPhraseDO.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(UPDATE_FAIL.formatEntity(WfPhraseDO.class, e), null);
        }
    }

    @Override
    public MethodResponse deletePhraseById(String rId) {
        try {
            wfWfPhraseMapper.deleteById(rId);
            return MethodResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(DELETE_FAIL.formatEntity(WfPhraseDO.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(DELETE_FAIL.formatEntity(WfPhraseDO.class, e), null);
        }
    }

    @Override
    public WfPhraseDO getPhraseById(String rId) {
        return wfWfPhraseMapper.selectById(rId);
    }

    @Override
    public List<WfPhraseDO> listPhraseByUser(String loginUserCode) {
		QueryWrapper<WfPhraseDO> query = new QueryWrapper<WfPhraseDO>();
		if (StringUtils.isNotEmpty(loginUserCode)) {
			query.lambda().eq(WfPhraseDO::getOpUser, loginUserCode);
            query.lambda().orderByAsc(WfPhraseDO::getOpTime);
			return wfWfPhraseMapper.selectList(query);
		}
		else {
			return Lists.newArrayList();
		}
	}

    @Override
    public List<WfPhraseDO> listAll() {
        return wfWfPhraseMapper.selectList();
    }

}
