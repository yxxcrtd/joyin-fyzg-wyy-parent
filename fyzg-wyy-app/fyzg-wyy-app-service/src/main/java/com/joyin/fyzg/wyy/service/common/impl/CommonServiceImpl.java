package com.joyin.fyzg.wyy.service.common.impl;

import com.google.common.base.Strings;
import com.joyin.fyzg.wyy.mapper.common.CommonMapper;
import com.joyin.fyzg.wyy.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

	@Autowired
	CommonMapper commonMapper;

	@Override
	public String getOCodeNextVal() {
		return "N" + Strings.padStart(commonMapper.getOCodeNextVal(), 7, '0');
	}
}
