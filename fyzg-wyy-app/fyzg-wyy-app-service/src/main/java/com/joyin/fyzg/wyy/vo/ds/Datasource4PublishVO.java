package com.joyin.fyzg.wyy.vo.ds;

import com.joyin.fyzg.wyy.entity.ds.DatasourceDO;
import lombok.Data;

@Data
public class Datasource4PublishVO {
	/** 菜单定义信息 */
	private MenuPageVO menuPageVO;
	/** 数据源定义信息 */
	private DatasourceDO publishData;
}
