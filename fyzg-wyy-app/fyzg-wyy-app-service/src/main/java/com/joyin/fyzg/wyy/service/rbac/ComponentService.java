package com.joyin.fyzg.wyy.service.rbac;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.rbac.ComponentDO;

import java.util.List;

public interface ComponentService {

    MethodResponse insertComponent(ComponentDO componentDO);

    MethodResponse<Integer> updateComponentById(ComponentDO componentDO);

    MethodResponse deleteComponentById(String rId);

	ComponentDO getComponentById(String rId);

	MethodResponse saveComponent(ComponentDO componentDO);

	MethodResponse clearComponent();

	List<ComponentDO> listAll();

	MethodResponse deleteComponentByCodeAndPattern(String code, String pattern);
}

