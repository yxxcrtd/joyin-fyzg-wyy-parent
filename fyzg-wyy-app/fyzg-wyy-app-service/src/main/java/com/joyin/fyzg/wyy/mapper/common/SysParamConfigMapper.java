package com.joyin.fyzg.wyy.mapper.common;

import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.common.ModelEnumDO;
import com.joyin.fyzg.wyy.entity.common.SysParamConfigDO;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysParamConfigMapper extends SuperMapper<SysParamConfigDO> {
    List<SysParamConfigDO> findAllEnabledByType(@Param("type") String type);

    SysParamConfigDO findByKey(@Param("paramKey") String paramKey);

}
