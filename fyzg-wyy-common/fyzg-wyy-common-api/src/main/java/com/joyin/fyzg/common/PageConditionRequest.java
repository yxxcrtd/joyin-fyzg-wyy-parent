package com.joyin.fyzg.common;

import lombok.Data;

@Data
public class PageConditionRequest<T> {

	private Long page;

	private Long pageSize;

	private T condition;

}
