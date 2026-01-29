package com.joyin.fyzg.wyy.common.utils;

import com.google.common.collect.Lists;
import com.joyin.fyzg.wyy.entity.rbac.*;
import com.joyin.fyzg.wyy.vo.rbac.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 菜单工具类
 * <br/>
 *
 * @author pengzhen
 * @date 2020/4/24 0024 下午 9:53
 */
@Component
@Slf4j
public class MenuUtils {
	@Autowired
	ModelMapper modelMapper;

	public List<MenuVO> buildVOTreeList(String busType, List<MenuModuleDO> menuModuleDOList, List<MenuPageDO> menuPageDOList, List<MenuPageComponentDO> menuPageComponentDOList, List<MenuPageParameterDO> menuPageParameterDOList, List<MenuPageActionDO> menuPageActionDOList, List<MenuPageRequestDO> menuPageRequestDOList) {
		List<MenuVO> list = Lists.newArrayList();
		for (MenuModuleDO item : menuModuleDOList) {
			if (busType.equals(item.getBusType()) && (StringUtils.isEmpty(item.getPMdlId()) || "0".equals(item.getPMdlId()))) {
				MenuVO rootMenuVO = modelMapper.map(item, MenuVO.class);
				rootMenuVO.setKey("MODULE" + item.getRId());
				rootMenuVO.setMenuName(item.getMdlName());
				rootMenuVO.setMenuType("MODULE");
				rootMenuVO.setChildren(this.buildVOTreeChildrenList(busType, item.getRId(), menuModuleDOList, menuPageDOList, menuPageComponentDOList, menuPageParameterDOList, menuPageActionDOList, menuPageRequestDOList));
				list.add(rootMenuVO);
			}
		}
		return list.stream().sorted(Comparator.comparing(MenuVO::getSort)).collect(Collectors.toList());
	}

	private List<MenuVO> buildVOTreeChildrenList(String busType, String mldId, List<MenuModuleDO> menuModuleDOList, List<MenuPageDO> menuPageDOList, List<MenuPageComponentDO> menuPageComponentDOList, List<MenuPageParameterDO> menuPageParameterDOList, List<MenuPageActionDO> menuPageActionDOList, List<MenuPageRequestDO> menuPageRequestDOList) {
		List<MenuVO> list = Lists.newArrayList();
		for (MenuModuleDO menuModuleDO : menuModuleDOList) {
			if (busType.equals(menuModuleDO.getBusType()) && mldId.equals(menuModuleDO.getPMdlId())) {
				MenuVO moduleMenuVO = modelMapper.map(menuModuleDO, MenuVO.class);
				moduleMenuVO.setKey("MODULE" + menuModuleDO.getRId());
				moduleMenuVO.setMenuName(menuModuleDO.getMdlName());
				moduleMenuVO.setMenuType("MODULE");
				moduleMenuVO.setChildren(this.buildVOTreeChildrenList(busType, menuModuleDO.getRId(), menuModuleDOList, menuPageDOList, menuPageComponentDOList, menuPageParameterDOList, menuPageActionDOList, menuPageRequestDOList));
				list.add(moduleMenuVO);
			}
		}
		for (MenuPageDO item : menuPageDOList) {
			if (busType.equals(item.getBusType()) && mldId.equals(item.getMdlId())) {
				MenuVO pageMenuVO = modelMapper.map(item, MenuVO.class);
				pageMenuVO.setKey("PAGE" + item.getRId());
				pageMenuVO.setMenuName(item.getPageName());
				pageMenuVO.setMenuType("PAGE");
				pageMenuVO.setComponentList(menuPageComponentDOList.stream().filter(item1 -> {
					return item1.getPageId().equals(item.getRId());
				}).map(item1 -> modelMapper.map(item1, MenuPageComponentVO.class)).collect(Collectors.toList()));
				pageMenuVO.setParameterList(menuPageParameterDOList.stream().filter(item1 -> {
					return item1.getPageId().equals(item.getRId());
				}).map(item1 -> modelMapper.map(item1, MenuPageParameterVO.class)).collect(Collectors.toList()));
				pageMenuVO.setActionList(menuPageActionDOList.stream().filter(item1 -> {
					return item1.getPageId().equals(item.getRId());
				}).map(item1 -> modelMapper.map(item1, MenuPageActionVO.class)).collect(Collectors.toList()));
				pageMenuVO.setRequestList(menuPageRequestDOList.stream().filter(item1 -> {
					return item1.getPageId().equals(item.getRId());
				}).map(item1 -> modelMapper.map(item1, MenuPageRequestVO.class)).collect(Collectors.toList()));
				list.add(pageMenuVO);
			}
		}
		return list.stream().sorted(Comparator.comparing(MenuVO::getSort)).collect(Collectors.toList());
	}
}
