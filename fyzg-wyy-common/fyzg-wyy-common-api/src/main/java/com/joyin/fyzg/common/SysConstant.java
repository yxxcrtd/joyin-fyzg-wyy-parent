package com.joyin.fyzg.common;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 系统常量定义
 * <br/>
 *
 * @author pengzhen
 * @date 2019/11/7 0007 下午 6:28
 */
public class SysConstant {

	public interface EnumField {

		/**
		 * @Fields enumLabel : 枚举显示值
		 */
		String enumLabel = "ENUM_LABEL";

		/**
		 * @Fields enumValue : 枚举存储值
		 */
		String enumValue = "ENUM_VALUE";

	}

	public interface CoreDefine {
		/**
		 * @Fields enumLabel : 枚举显示值
		 */
		String coreDefineRId = "CORE_DEFINERID";

	}

	public interface TableField {
		/**
		 * @Fields sysType : 对象代码
		 */
		String sysType = "SYS_TYPE";

		/**
		 * @Fields oCode : 对象代码
		 */
		String oCode = "O_CODE";

		/**
		 * @Fields oName : 对象名称 （主表的时候才会用这个作为唯一约束字段）
		 */
		String oName = "O_NAME";

		/**
		 * @Fields pOCode : 父对象代码
		 */
		String pOCode = "P_O_CODE";

		/**
		 * @Fields pRId : D2表特有字段
		 */
		String pRId = "P_R_ID";

		/**
		 * @Fields oName : 对象名称拼音码
		 */
		String pyName = "PY_NAME";

		/**
		 * @Fields showName : 在选择好对象后，显示在对象型指标输入框中的名称。SHOW_NAME的用途就是显示用户选择的对象
		 */
		String showName = "SHOW_NAME";

		/**
		 * @Fields hintName : 在查询对象时，根据用户输入的查询条件，系统会返回复核条件的所有对象列表，列表中显示的信息就是HINT_NAME 。HINT_NAME的用途是告诉用户哪些对象符合筛选要求，并让用户选择一个具体对象
		 */
		String hintName = "HINT_NAME";

		/**
		 * @Fields ftCode : 在查询对象时，用户会输入查询的条件，这个条件就是FIT_CODE。系统会根据输入的信息与FIT_CODE进行匹配， 匹配上的对象，会填充到下拉列表中供用户筛选
		 */
		String ftCode = "FT_CODE";

		/**
		 * @Fields jyInsertTime : 创建时间
		 */
		String jyInsertTime = "JY_INSERT_TIME";

		/**
		 * @Fields jyUpdateTime : 修改时间
		 */
		String jyUpdateTime = "JY_UPDATE_TIME";

		/**
		 * @Fields jyDeleteTime : 删除时间
		 */
		String jyDeleteTime = "JY_DELETE_TIME";

		/**
		 * @Fields jyInserUser : 创建用户
		 */
		String jyInsertUser = "JY_INSERT_USER";

		/**
		 * @Fields jyUpdateUser : 修改用户
		 */
		String jyUpdateUser = "JY_UPDATE_USER";

		/**
		 * @Fields jyDeleteUser : 删除用户
		 */
		String jyDeleteUser = "JY_DELETE_USER";

		/**
		 * @Fields batchCode : 批次号
		 */
		String batchCode = "BATCH_CODE";

		/**
		 * @Fields pipeId : 管道号
		 */
		String pipeId = "PIPE_ID";

		/**
		 * @Fields instanceId : 流程实例ID
		 */
		String instanceId = "INSTANCE_ID";

		/**
		 * @Fields instanceName : 流程实例NAME
		 */
		String instanceName = "INSTANCE_NAME";

		/**
		 * @Fields srcInstanceId : 来源流程实例ID
		 */
		String srcInstanceId = "SRC_INSTANCE_ID";

		/**
		 * @Fields dataType : 数据类型
		 */
		String dataType = "DATA_TYPE";

		/**
		 * @Fields oType : 对象类型
		 */
		String oType = "O_TYPE";

		/**
		 * @Fields rType : K表的侧度数据
		 */
		String rType = "R_TYPE";

		/**
		 * @Fields dFlag : 数据标记
		 */
		String dFlag = "D_FLAG";

		/**
		 * @Fields RID : 数据自增的ID
		 */
		String rId = "R_ID";

		/**
		 * @Fields RID : 源数据编号
		 */
		String srcId = "SRC_ID";

		List<String> COMMON_COL_LIST = Lists.newArrayList(rId, dFlag, instanceId);
	}

}
