package com.joyin.fyzg.wyy.service.common.impl;

import com.joyin.fyzg.common.QueryResultMap;
import com.joyin.fyzg.wyy.mapper.common.CommonMapper;
import com.joyin.fyzg.wyy.service.common.AppCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TpAppServiceImpl(功能列表解析器应用)
 * <br/>
 *
 * @author pengzhen
 * @date 2019/10/29 0029 下午 3:55
 */
@Service
@Slf4j
@Transactional
public class AppCommonServiceImpl implements AppCommonService {

	@Autowired
	CommonMapper commonMapper;

	@Override
	public List<QueryResultMap> listAutocompleteUserData(String queryString) {
		return commonMapper.listAutocompleteUserData(queryString);
	}

}
