package com.joyin.fyzg.enums;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joyin.fyzg.common.ResponseCode;
import com.joyin.fyzg.common.WithCodeMsgEnum;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;
import com.joyin.fyzg.common.utils.SpringContextUtils;
import com.joyin.fyzg.config.mybatisplus.TableNameConfig;

import java.text.MessageFormat;
import java.util.Optional;

public enum BaseExceptionEnum implements WithCodeMsgFormatEnum {

	INSERT_FAIL(ResponseCode.ERROR.code, "【{0}】插入失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】插入失败！";
		}
	},
	UPDATE_FAIL(ResponseCode.ERROR.code, "【{0}】更新失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】更新失败！";
		}
	},
	DELETE_FAIL(ResponseCode.ERROR.code, "【{0}】删除失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】删除失败！";
		}
	},
	BATCH_INSERT_FAIL(ResponseCode.ERROR.code, "【{0}】批量插入失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】批量插入失败！";
		}
	},
	BATCH_UPDATE_FAIL(ResponseCode.ERROR.code, "【{0}】批量更新失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】批量更新失败！";
		}
	},
	BATCH_DELETE_FAIL(ResponseCode.ERROR.code, "【{0}】批量删除失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】批量删除失败！";
		}
	},
	BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "【{0}】批量保存失败！错误消息如下：【{1}】") {
		@Override
		public String getMsg() {
			return "【{0}】批量删除失败！";
		}
	},
	;

	public final String code;
	public final String format;
	public String errorMsg;

	@Override
	public String getFormat() {
		return this.format;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.errorMsg;
	}

	private BaseExceptionEnum(String code, String format) {
		this.code =  code;
		this.format = format;
	}

	/**
	 * @Description entityClazz 必须要有@TableName注解
	 * @Author zihongshuai
	 * @Date 2021/6/30 - 9:37
	 */
	public <T> WithCodeMsgEnum formatEntity(Class<T> entityClazz, Exception e) {
		String enName = entityClazz.getAnnotation(TableName.class).value();
		String allName = SpringContextUtils.getBean(TableNameConfig.class).getFullNameByEnName(enName);
		String cnName = SpringContextUtils.getBean(TableNameConfig.class).getCnNameByEnName(enName);
		BaseExceptionEnum that = this;
		return new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return that.getCode();
			}

			@Override
			public String getMsg() {
				return Optional.ofNullable(that.getMsg()).map(msg -> MessageFormat.format(msg, cnName)).orElse(this.getErrorMsg());
			}

			@Override
			public String getErrorMsg() {
				return MessageFormat.format(that.getFormat(), allName, e);
			}
		};
	}
}
