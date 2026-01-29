package com.joyin.fyzg.wyy.service.microApplication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.wyy.entity.microApplication.MicroApplicationDO;
import com.joyin.fyzg.wyy.mapper.microApplication.MicroApplicationMapper;
import com.joyin.fyzg.wyy.service.microApplication.MicroApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

@Service
@Slf4j
public class MicroApplicationServiceImpl implements MicroApplicationService {

	@Autowired
	MicroApplicationMapper microApplicationMapper;

	@Override
	public MethodResponse insertMicroApplication(MicroApplicationDO microApplicationDO) {
		try {
			microApplicationMapper.insert(microApplicationDO);
			return MethodResponse.success(microApplicationDO.getRId());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(INSERT_FAIL.formatEntity(MicroApplicationDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MicroApplicationDO.class, e));
		}
	}

	@Override
	public MethodResponse updateMicroApplicationById(MicroApplicationDO microApplicationDO) {
		try {
			microApplicationMapper.updateById(microApplicationDO);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(UPDATE_FAIL.formatEntity(MicroApplicationDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(UPDATE_FAIL.formatEntity(MicroApplicationDO.class, e));
		}
	}

	@Override
	public MethodResponse deleteMicroApplicationById(String rId) {
		try {
			microApplicationMapper.deleteById(rId);
			return MethodResponse.success();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error(DELETE_FAIL.formatEntity(MicroApplicationDO.class, e).getErrorMsg(), e);
			return MethodResponse.transWithCodeMsgEnum(DELETE_FAIL.formatEntity(MicroApplicationDO.class, e));
		}
	}

	@Override
	public MicroApplicationDO getMicroApplicationById(String rId) {
		return microApplicationMapper.selectById(rId);
	}


	@Override
	public List<MicroApplicationDO> listAll() {
		return microApplicationMapper.selectList();
	}

	@Override
	public List<MicroApplicationDO> listMicroApplication(RequestParamMap params) {

		QueryWrapper<MicroApplicationDO> queryWrapper = new QueryWrapper<>();
		Optional.ofNullable(params.get("name")).ifPresent(name ->{
			queryWrapper.lambda().eq(MicroApplicationDO::getName, name);
		});
		return microApplicationMapper.selectList(queryWrapper);
	}

}
