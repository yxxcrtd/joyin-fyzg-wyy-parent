package com.joyin.fyzg.wyy.mapper.common;

import com.joyin.fyzg.common.QueryResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {

	public String getOCodeNextVal();


	List<QueryResultMap> listAutocompleteUserData(
			@Param("queryString") String queryString
	);
}

