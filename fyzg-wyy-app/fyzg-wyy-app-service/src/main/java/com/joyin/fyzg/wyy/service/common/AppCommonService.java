package com.joyin.fyzg.wyy.service.common;

import com.joyin.fyzg.common.QueryResultMap;

import java.util.List;

/**
 * AppSaveDataService(功能列表解析器应用)
 * <br/>
 *
 * @author pengzhen
 * @date 2019/10/29 0029 下午 3:54
 */
public interface AppCommonService {

	List<QueryResultMap> listAutocompleteUserData(String queryString);

}

