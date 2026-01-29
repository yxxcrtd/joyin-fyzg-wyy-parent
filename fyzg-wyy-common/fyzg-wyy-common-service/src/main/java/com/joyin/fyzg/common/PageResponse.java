package com.joyin.fyzg.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class PageResponse<T> {

	private List<T> records; // 当前页的数据
	private long current; // 当前页码
	private long size; // 每页大小
	private long totalPages; // 总页数
	private long total; // 总记录数

	public PageResponse(IPage<T> page) {
		this.records = page.getRecords();
		this.current = page.getCurrent();
		this.size = page.getSize();
		this.totalPages = page.getPages();
		this.total = page.getTotal();
	}



	// toString方法，方便打印调试
	@Override
	public String toString() {
		return "PageResponse{" +
				"records=" + records +
				", current=" + current +
				", size=" + size +
				", totalPages=" + totalPages +
				", total=" + total +
				'}';
	}
}