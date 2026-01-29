package com.joyin.fyzg.wyy.mapper.common;

import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemBO;
import com.joyin.fyzg.wyy.entity.common.ModelEnumItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * DEFINE_CONSTANT_ENUM_ITEM表的DAO层类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
@Mapper
public interface ModelEnumItemMapper extends SuperMapper<ModelEnumItemDO> {

	List<ModelEnumItemBO> listAllEnumItem();
}

