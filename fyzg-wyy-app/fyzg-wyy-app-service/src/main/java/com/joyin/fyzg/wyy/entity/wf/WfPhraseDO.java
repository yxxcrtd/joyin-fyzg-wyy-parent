package com.joyin.fyzg.wyy.entity.wf;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表SYS_APP_WF_ACT_CC的实体类
 *
 * @author 工具生成
 * @version 1.0
 * @since
 */
@Data
//@KeySequence(value = "SYS_WF_PHRASE", clazz = Long.class)
@TableName("SYS_WF_PHRASE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WfPhraseDO {

	/**
	 * R_ID : 主键R_ID
	 */
	@TableId(value = "R_ID", type = IdType.UUID)
	private String rId;

	/**
	 * OP_USER : 所属用户
	 */
	private String opUser;

	/**
	 * OP_TIME : 操作时间
	 */
	private String opTime;

	/**
	 * CONTENT : 短语内容
	 */
	private String description ;

	/**
	 * TITLE : 短语标题
	 */
	private String shortName ;

}
