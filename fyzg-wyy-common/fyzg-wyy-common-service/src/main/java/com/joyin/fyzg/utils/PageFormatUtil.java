package com.joyin.fyzg.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cqh
 * @date 2022/4/18 16:22
 */
public class PageFormatUtil {

    /**
     * 适配老的分页参数
     *
     * @param startNum
     * @param endNum
     * @return
     */
    public static PageFormat getPageObj(int startNum, int endNum) {
        int pageSize = endNum + 1 - startNum;
        int page = startNum / pageSize + 1;
        return new PageFormat(page, pageSize);
    }

    /**
     * 适配老的返回格式
     *
     * @param page
     * @return
     */
    public static JSONObject formatVo(IPage<?> page) {
        JSONObject result = new JSONObject();
        result.put("tileTableData", page.getRecords());
        result.put("totalCount", page.getTotal());
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageFormat {
        public int page;
        public int pageSize;
    }
}
