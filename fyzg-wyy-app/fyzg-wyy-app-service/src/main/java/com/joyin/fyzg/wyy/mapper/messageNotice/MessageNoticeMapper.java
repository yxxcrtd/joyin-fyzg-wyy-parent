package com.joyin.fyzg.wyy.mapper.messageNotice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joyin.fyzg.SuperMapper;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageNoticeDO;
import com.joyin.fyzg.wyy.vo.MessageNotice.MessageNoticeVO;
import org.apache.ibatis.annotations.Param;

/**
 * SYS_DATASOURCE表的DAO层类
 * @author 工具生成
 * @version 1.0
 * @since 
 */
public interface MessageNoticeMapper extends SuperMapper<MessageNoticeDO> {
    IPage<MessageNoticeVO> selectPageByParam(Page<MessageNoticeVO> page, @Param("title") String title, @Param("userOName") String userOName, @Param("type") String type);
}

