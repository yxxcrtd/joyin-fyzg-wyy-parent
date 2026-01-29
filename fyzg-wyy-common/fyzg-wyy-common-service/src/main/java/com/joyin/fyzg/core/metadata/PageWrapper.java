package com.joyin.fyzg.core.metadata;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.common.exception.SqlExecutionException;
import com.joyin.fyzg.enums.SqlExecutionExceptionEnum;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <br/>
 *
 * @author jinyuan.lin
 * @date 2024/10/8 13:45
 */
@Data
public class PageWrapper {

	private Integer pageSize;
	private Integer currentPage;
	private Integer offset ;

	private Integer startNum ;
	private Integer endNUm ;

	/**
	 * 从HTTP请求中获取分页参数并封装到PageWrapper对象中
	 *
	 * @param paramMap HTTP请求对象，用于获取分页参数
	 * @return PageWrapper 分页参数封装对象
	 * @throws SqlExecutionException 如果分页参数不正确，抛出此异常
	 */
	public static PageWrapper builderPageWrapper(Map<String, Object> paramMap) {
	    // 创建PageWrapper对象用于封装分页信息
	    PageWrapper page = null;
	    // 检查是否存在currentPage和pageSize参数
		if(paramMap.containsKey("currentPage") && paramMap.containsKey("pageSize")){
			// 获取并转换currentPage和pageSize参数值
			Integer currentPage = Integer.valueOf(paramMap.get("currentPage").toString());
			Integer pageSize = Integer.valueOf(paramMap.get("pageSize").toString());

			// 设置PageWrapper的分页信息
			page = builderPageWrapper(currentPage, pageSize);
		}else if(paramMap.containsKey("current") && paramMap.containsKey("size")){
			// 获取并转换currentPage和pageSize参数值
			Integer currentPage = Integer.valueOf(paramMap.get("current").toString());
			Integer pageSize = Integer.valueOf(paramMap.get("size").toString());
			// 设置PageWrapper的分页信息
			page = builderPageWrapper(currentPage, pageSize);
		}else{
			// 设置一个默认的
			page = builderPageWrapper(1, 99999999);
//	        // 如果缺少分页参数，抛出异常  树列表不存在分页
//	        RuntimeException runtimeException = new RuntimeException("currentPage和pageSize参数不存在");
//	        throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_PAGE_FAIL.format(runtimeException), runtimeException);
	    }
	    // 返回封装好的PageWrapper对象
	    return page;
	}

	public static PageWrapper builderPageWrapper(HttpServletRequest httpServletRequest) {
		// 创建PageWrapper对象用于封装分页信息
		PageWrapper page = new PageWrapper();

		// 获取请求参数映射，键是参数名，值是参数的字符串数组
		Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

		// 检查是否存在currentPage和pageSize参数
		if(parameterMap.containsKey("currentPage") && parameterMap.containsKey("pageSize")){
			// 获取并转换currentPage和pageSize参数值
			Integer currentPage = Integer.valueOf(parameterMap.get("currentPage")[0]);
			Integer pageSize = Integer.valueOf(parameterMap.get("pageSize")[0]);

			// 设置PageWrapper的分页信息
			page = builderPageWrapper(currentPage, pageSize);
		}else if(parameterMap.containsKey("current") && parameterMap.containsKey("size")){
			// 获取并转换currentPage和pageSize参数值
			Integer currentPage = Integer.valueOf(parameterMap.get("current")[0]);
			Integer pageSize = Integer.valueOf(parameterMap.get("size")[0]);

			// 设置PageWrapper的分页信息
			page = builderPageWrapper(currentPage, pageSize);
		}else{
			// 如果缺少分页参数，抛出异常
			RuntimeException runtimeException = new RuntimeException("currentPage和pageSize参数不存在");
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_PAGE_FAIL.format(runtimeException), runtimeException);
		}
		// 返回封装好的PageWrapper对象
		return page;
	}


	public static PageWrapper builderPageWrapper(Integer currentPage, Integer pageSize){
		PageWrapper page = new PageWrapper();
		// 设置PageWrapper的分页信息
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		// 计算数据库查询的偏移量，用于分页查询
		page.setOffset(currentPage > 0 ? (currentPage - 1) * pageSize : 0);

		// 开始位置和结束位置
		page.setStartNum(pageSize * (currentPage - 1) + 1) ;
		page.setEndNUm(page.getStartNum() + pageSize - 1) ;
		return page ;
	}


	/**
	 * 获取MyBatis分页对象
	 *
	 * 本方法用于创建并返回一个分页对象(IPage)，该对象用于在查询数据时实现分页功能
	 * 通过当前页码和每页大小来初始化分页对象，这样可以灵活地控制分页行为
	 *
	 * @return IPage<Map> 分页对象，包含当前页码和每页大小的配置
	 */
	public IPage<Map> getIPage() {
	    return new Page<>(this.currentPage, this.pageSize);
	}


}
