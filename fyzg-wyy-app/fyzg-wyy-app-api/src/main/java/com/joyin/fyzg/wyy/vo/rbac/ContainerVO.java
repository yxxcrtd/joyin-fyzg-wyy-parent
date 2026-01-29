package com.joyin.fyzg.wyy.vo.rbac;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * ContainerVO
 * <br/>
 *
 * @author pengzhen
 * @date 2019/9/26 0026 上午 9:53
 */
@Data
@Builder
public class ContainerVO {
	private BusinessVO businessVO;
	private List<MenuVO> menuVOList;
}
