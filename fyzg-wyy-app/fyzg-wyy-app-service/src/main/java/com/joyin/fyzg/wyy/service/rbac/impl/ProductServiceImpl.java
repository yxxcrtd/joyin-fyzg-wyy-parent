package com.joyin.fyzg.wyy.service.rbac.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.utils.DynamicSqlExecutorUtils;
import com.joyin.fyzg.wyy.entity.rbac.ProductDO;
import com.joyin.fyzg.wyy.mapper.rbac.ProductMapper;
import com.joyin.fyzg.wyy.mapper.rbac.RoleRelateMapper;
import com.joyin.fyzg.wyy.service.rbac.ProductService;
import com.joyin.fyzg.vo.enums.FlagType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {


	final static String POST_CODE = "WFW-POST";

	@Autowired
	ProductMapper productMapper;
	@Autowired
	RoleRelateMapper roleRelateMapper;
	@Autowired
	private DynamicSqlExecutorUtils sqlExecutorUtils;

	@Override
	public ProductDO getProductById(Long rId) {
		return productMapper.selectById(rId);
	}

	@Override
	public ProductDO getProductByCode(String code) {
		QueryWrapper<ProductDO> query = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(code)) {
			query.lambda().eq(ProductDO::getCode, code);
			ProductDO productDO = productMapper.selectOne(query);
			return productDO;
		}else {
			return null;
		}
	}

	@Override
	public MethodResponse getDesktopComponentDataById(Long rId) {
		ProductDO productDO = this.getProductById(rId);
		List<Map> listData = null;
		if (productDO != null && StringUtils.isNotBlank(productDO.getDesktopComponentSql())){
			listData = sqlExecutorUtils.findListData(productDO.getDesktopComponentSql(), null);
		}
		return MethodResponse.success(listData);
	}

	@Override
	public MethodResponse getAnnexSuffixByCode(String code) {
		ProductDO productDO = this.getProductByCode(code);
		if (productDO != null){
			return MethodResponse.success(productDO.getAnnexSuffix());
		} else {
			return MethodResponse.success("");
		}
	}
	@Override
	public MethodResponse getMdAnnexSuffixByCode(String code) {
		ProductDO productDO = this.getProductByCode(code);
		if (productDO != null){
			return MethodResponse.success(productDO.getMdAnnexSuffix());
		} else {
			return MethodResponse.success("");
		}
	}
	@Override
	public MethodResponse getDefaultDesktopCfgUserByCode(String code) {
		ProductDO productDO = this.getProductByCode(code);
		if (productDO != null){
			return MethodResponse.success(productDO.getDefaultDesktopCfgUser());
		} else {
			return MethodResponse.success("");
		}
	}
	@Override
	public MethodResponse listComponentWithFilter(String loginUserCode, String type) {
		QueryWrapper<ProductDO> query = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(type)) {
			query.lambda().eq(ProductDO::getCode, type);
		}
		List<ProductDO> productDOS = productMapper.selectList(query);
		final List<Map> listData = new ArrayList<>();
		if (productDOS != null && productDOS.size() > 0){
			productDOS.stream().forEach(item ->{
				listData.addAll(sqlExecutorUtils.findListData(item.getDesktopComponentSql(), null));
			});
		}
		List<String> components = roleRelateMapper.listComponentByUserCodeAndType(loginUserCode, type);
		//拿到父元素
		List<Map> pListData = listData.stream().filter(item -> item.get("PCODE") == null || "".equals(item.get("PCODE"))).collect(Collectors.toList());
		//拿到该用户拥有的元素
		List<Map> resListData = listData.stream().filter(item -> item.get("PCODE") != null && !"".equals(item.get("PCODE")))
//				.filter(item -> components.contains(item.get("CODE")))
				.filter(item-> Arrays.asList("base","kanban").contains(item.get("PCODE")))
				.collect(Collectors.toList());
		resListData.addAll(pListData);
		return MethodResponse.success(resListData);
	}

	@Override
	public List<Map> listAllComponent() {
		ProductDO productDO = productMapper.selectOne(new QueryWrapper<ProductDO>()
				.lambda()
				.eq(ProductDO::getCode, POST_CODE));
		List<Map> listData = Lists.newArrayList();
		if (productDO != null && StringUtils.isNotBlank(productDO.getDesktopComponentSql())){
			listData = sqlExecutorUtils.findListData(productDO.getDesktopComponentSql(), null);
		}
		return listData;
	}

	@Override
	public List<ProductDO> listAll() {
		return productMapper.selectList();
	}

	@Override
	public List<ProductDO> listAllEnabled() {
		QueryWrapper<ProductDO> query = new QueryWrapper<ProductDO>();
		query.lambda().eq(ProductDO::getEnabled, FlagType.YES.getValue());
		return productMapper.selectList(query);
	}
}
