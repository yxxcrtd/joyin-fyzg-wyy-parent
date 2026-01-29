package com.joyin.fyzg.wyy.service.messageNotice.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.PageResponse;
import com.joyin.fyzg.config.exception.ChainExceptionUtils;
import com.joyin.fyzg.core.metadata.PageWrapper;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.wyy.common.enums.RbacInnerUserFlag;
import com.joyin.fyzg.wyy.common.enums.RbacInnerUserType;
import com.joyin.fyzg.wyy.common.enums.RbacUserEnable;
import com.joyin.fyzg.wyy.entity.financier.FinancierDO;
import com.joyin.fyzg.wyy.entity.messageNotice.MessageFinancierGroupInfoDO;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import com.joyin.fyzg.wyy.mapper.financier.FinancierMapper;
import com.joyin.fyzg.wyy.mapper.messageNotice.MessageFinancierGroupInfoMapper;
import com.joyin.fyzg.wyy.mapper.rbac.UserMapper;
import com.joyin.fyzg.wyy.service.messageNotice.MessageUserGroupInfoService;
import com.joyin.fyzg.wyy.vo.MessageNotice.MessageFinancierGroupInfoImportVO;
import com.joyin.fyzg.wyy.vo.MessageNotice.MessageFinancierGroupInfoVO;
import com.joyin.fyzg.wyy.vo.MessageNotice.CustFinancierVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.joyin.fyzg.enums.BaseExceptionEnum.*;

/**
 * @author Administrator
 * @description 针对表【SYS_MESSAGE_USER_GROUP_INFO(用户分组信息表)】的数据库操作Service实现
 * @createDate 2025-10-13 17:17:13
 */
@Slf4j
@Service
public class MessageUserGroupInfoServiceImpl implements MessageUserGroupInfoService {
    @Autowired
    MessageFinancierGroupInfoMapper messageFinancierGroupInfoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    FinancierMapper financierMapper;

    @Override
    public MethodResponse queryMessageUserGroupInfo(String groupName) {
        try {
            List<MessageFinancierGroupInfoDO> messageFinancierGroupInfoList = Lists.newArrayList();
            QueryWrapper<MessageFinancierGroupInfoDO> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(groupName)) {
                queryWrapper.lambda().like(MessageFinancierGroupInfoDO::getGroupName, groupName);
            }
            messageFinancierGroupInfoList = messageFinancierGroupInfoMapper.selectList(queryWrapper);
            //设置用户名称
            List<MessageFinancierGroupInfoVO> financierGroupInfoVOs = this.setCustOName(messageFinancierGroupInfoList);
            return MethodResponse.success(financierGroupInfoVOs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【用户分组信息】查询出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MessageFinancierGroupInfoDO.class, e));
        }
    }


    @Override
    public PageResponse<MessageFinancierGroupInfoVO> queryMessageUserGroupInfoByPage(String groupName, PageWrapper pageWrapper) {
        Page<MessageFinancierGroupInfoDO> page = new Page<>(pageWrapper.getCurrentPage(), pageWrapper.getPageSize());
        QueryWrapper<MessageFinancierGroupInfoDO> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(groupName)) {
            queryWrapper.lambda().like(MessageFinancierGroupInfoDO::getGroupName, groupName);
        }
        IPage<MessageFinancierGroupInfoDO> financierGroupInfoList = messageFinancierGroupInfoMapper.selectPage(page, queryWrapper);
        PageResponse<MessageFinancierGroupInfoDO> pageResponse = new PageResponse<>(financierGroupInfoList);
        List<MessageFinancierGroupInfoVO> financierGroupInfoVOs = this.setCustOName(pageResponse.getRecords());
        PageResponse<MessageFinancierGroupInfoVO> pageResponseVo = new PageResponse();
        pageResponseVo.setRecords(financierGroupInfoVOs);
        pageResponseVo.setCurrent(financierGroupInfoList.getCurrent());
        pageResponseVo.setSize(financierGroupInfoList.getSize());
        pageResponseVo.setTotal(financierGroupInfoList.getTotal());
        return pageResponseVo;
    }

    private List<MessageFinancierGroupInfoVO> setCustOName(List<MessageFinancierGroupInfoDO> messageFinancierGroupInfoList) {
        List<MessageFinancierGroupInfoVO> messageFinancierGroupInfoVOList = Lists.newArrayList();
        for (MessageFinancierGroupInfoDO messageFinancierGroupInfoDO : messageFinancierGroupInfoList) {
            MessageFinancierGroupInfoVO messageFinancierGroupInfoVO = new MessageFinancierGroupInfoVO();
            messageFinancierGroupInfoVO.setRId(messageFinancierGroupInfoDO.getRId());
            messageFinancierGroupInfoVO.setGroupName(messageFinancierGroupInfoDO.getGroupName());
            String groupCode = messageFinancierGroupInfoDO.getGroupCode();
            if (!StringUtils.isEmpty(groupCode)) {
                List<String> groupCodeList = Arrays.asList(groupCode.split(","));
                QueryWrapper<FinancierDO> query = new QueryWrapper<FinancierDO>();
                query.lambda().in(FinancierDO::getCustOCode, groupCodeList);
                List<FinancierDO> userDOList = financierMapper.selectList(query);
                Map<String, String> financierMap = userDOList.stream().collect(Collectors.toMap(e -> e.getCustOCode(), e -> e.getCustOName(), (o1, o2) -> o2));
                List<CustFinancierVO> custFinancierVOs = Lists.newArrayList();
                groupCodeList.stream().forEach(userOCode -> {
                    CustFinancierVO custFinancierVO = new CustFinancierVO();
                    String userOName = financierMap.get(userOCode);
                    custFinancierVO.setCustOCode(userOCode);
                    custFinancierVO.setCustOName(userOName);
                    custFinancierVOs.add(custFinancierVO);
                });
                messageFinancierGroupInfoVO.setCustFinancierVOs(custFinancierVOs);
            }
            messageFinancierGroupInfoVOList.add(messageFinancierGroupInfoVO);
        }
        return messageFinancierGroupInfoVOList;
    }


    //通过groupId找到管理人下面的所有用户
    @Override
    public MethodResponse queryUserCodeByGroupIds(List<Long> groupIds) {
        try {
            List<MessageFinancierGroupInfoDO> messageFinancierGroupInfoList = Lists.newArrayList();
            QueryWrapper<MessageFinancierGroupInfoDO> queryWrapper = new QueryWrapper<>();
            if (!CollectionUtils.isEmpty(groupIds)) {
                queryWrapper.lambda().in(MessageFinancierGroupInfoDO::getRId, groupIds);
            }
            messageFinancierGroupInfoList = messageFinancierGroupInfoMapper.selectList(queryWrapper);
            List<MessageFinancierGroupInfoVO> messageFinancierGroupInfoVOs = Lists.newArrayList();
            //设置管理人名称
            for (MessageFinancierGroupInfoDO messageFinancierGroupInfo : messageFinancierGroupInfoList) {
                MessageFinancierGroupInfoVO messageFinancierGroupInfoVO = new MessageFinancierGroupInfoVO();
                messageFinancierGroupInfoVO.setRId(messageFinancierGroupInfo.getRId());
                String groupCode = messageFinancierGroupInfo.getGroupCode();
                List<String> financierList = Arrays.asList(groupCode.split(","));
                QueryWrapper<UserDO> query = new QueryWrapper<UserDO>();
                query.lambda().in(UserDO::getFinancier, financierList);
                query.lambda().in(UserDO::getEnabled, RbacUserEnable.ENABLE.getValue());
                List<UserDO> userDOList = userMapper.selectList(query);
                List<String> userOCodeList = userDOList.stream().map(e -> e.getUserOCode()).collect(Collectors.toList());
                userOCodeList = userOCodeList.stream().distinct().collect(Collectors.toList());
                messageFinancierGroupInfoVO.setGroupName(messageFinancierGroupInfo.getGroupName());
                messageFinancierGroupInfoVO.setUserOCodeList(userOCodeList);
                messageFinancierGroupInfoVOs.add(messageFinancierGroupInfoVO);
            }
            return MethodResponse.success(messageFinancierGroupInfoVOs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【用户分组信息】查询出错", e);
            return MethodResponse.transWithCodeMsgEnum(INSERT_FAIL.formatEntity(MessageFinancierGroupInfoDO.class, e));
        }
    }

    @Override
    public MethodResponse insertMessageUserGroupInfo(MessageFinancierGroupInfoDO messageFinancierGroupInfo) {
        try {
            messageFinancierGroupInfo.setCreateTime(DateUtil8.getNowTime_EN());
            messageFinancierGroupInfo.setUpdateTime(DateUtil8.getNowTime_EN());
            messageFinancierGroupInfoMapper.insert(messageFinancierGroupInfo);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(INSERT_FAIL.formatEntity(MessageFinancierGroupInfoDO.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(INSERT_FAIL.format(e.getMessage()), e);
        }
    }

    @Override
    public MethodResponse updateMessageUserGroupInfo(MessageFinancierGroupInfoDO messageFinancierGroupInfo) {
        try {
            messageFinancierGroupInfo.setUpdateTime(DateUtil8.getNowTime_EN());
            messageFinancierGroupInfoMapper.updateById(messageFinancierGroupInfo);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_UPDATE_FAIL.formatEntity(MessageFinancierGroupInfoDO.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_UPDATE_FAIL.format(e.getMessage()), e);
        }
    }


    @Override
    public MethodResponse batchDeleteMessageUserGroupInfo(List<Long> idList) {
        try {
            messageFinancierGroupInfoMapper.deleteBatchIds(idList);
            return MethodResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(BATCH_DELETE_FAIL.formatEntity(MessageFinancierGroupInfoDO.class, e).getErrorMsg(), e);
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_DELETE_FAIL.format(e.getMessage()), e);
        }
    }


    @Override
    public List<String> uploadTemp(MultipartFile file) throws IOException {
        //解析错误的信息
        List<String> errorMessageList = Lists.newArrayList();
        //数据库中不存在的管理人信息
        List<String> errorFinancierCodeList = Lists.newArrayList();
        try {
            ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).head(MessageFinancierGroupInfoImportVO.class).registerReadListener(new ReadListener<MessageFinancierGroupInfoImportVO>() {
                private List<MessageFinancierGroupInfoImportVO> dataList = new ArrayList<>();

                @Override
                public void onException(Exception exception, AnalysisContext context) throws Exception {
                    log.info("解析失败，继续解析执行下一行：{}", exception.getMessage());
                    if (exception instanceof ExcelDataConvertException) {
                        ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
                        Integer rowIndex = excelDataConvertException.getRowIndex();
                        Integer columnIndex = excelDataConvertException.getColumnIndex();
                        CellData<?> celData = excelDataConvertException.getCellData();
                        log.info("第{}行，第{}列，解析异常，数据为【{}】", rowIndex, columnIndex, celData);
                        String errorMessage = "第" + rowIndex + "行，第" + columnIndex + "列，解析异常";
                        errorMessageList.add(errorMessage);
                    } else {
                        errorMessageList.add(exception.getMessage());
                    }
                }

                // 读取到一行数据时调用
                @Override
                public void invoke(MessageFinancierGroupInfoImportVO data, AnalysisContext context) {
                    dataList.add(data);
                }

                //所有数据读取完成后调用
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    //校验传入的管理人id是否存在,如果不存在，加入错误信息列表
                    List<String> custOCodes = dataList.stream().map(e -> e.getCustOCode()).collect(Collectors.toList());
                    custOCodes.stream().forEach(custOCode -> {
                        if (!verifyFinancierInfo(custOCode)) {
                            errorFinancierCodeList.add("管理人不存在，管理人代码：" + custOCode);
                        }
                    });

                    //如果没有错误信息并且数据不为空，保存数据
                    if (errorFinancierCodeList.isEmpty() && errorMessageList.isEmpty() && !dataList.isEmpty()) {
                        saveData(dataList);
                        log.info("Excel 导入完成！");
                    } else {
                        log.info("Excel 导入失败！");
                    }
                }

            }).build();
            excelReader.read(readSheet);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("excel导入用户信息失败");
            return ChainExceptionUtils.throwExceptionWithReturn(BATCH_SAVE_FAIL.format(e.getMessage()), e);
        }

        if (!errorMessageList.isEmpty()) {
            log.info("解析失败的用户信息{}", errorMessageList.toString());
            return errorMessageList;
        }

        if (!errorFinancierCodeList.isEmpty()) {
            log.info("校验管理人信息失败的管理人信息{}", errorFinancierCodeList.toString());
            return errorFinancierCodeList;
        }

        return null;
    }

    private void saveData(List<MessageFinancierGroupInfoImportVO> dataList) {
        Map<String, List<MessageFinancierGroupInfoImportVO>> dataMap = dataList.stream().collect(Collectors.groupingBy(e -> e.getGroupName()));
        List<String> groupNameList = dataList.stream().map(e -> e.getGroupName()).distinct().collect(Collectors.toList());
        QueryWrapper<MessageFinancierGroupInfoDO> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().in(MessageFinancierGroupInfoDO::getGroupName, groupNameList);
        //删除已经存在的分组名称
        List<MessageFinancierGroupInfoDO> messageFinancierGroupInfoDOList = messageFinancierGroupInfoMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(messageFinancierGroupInfoDOList)) {
            log.info("excel导入用户分组已存在，需要删除：{}", messageFinancierGroupInfoDOList.toString());
            List<Long> ids = messageFinancierGroupInfoDOList.stream().map(e -> e.getRId()).collect(Collectors.toList());
            this.batchDeleteMessageUserGroupInfo(ids);
        }
        //保存分组信息
        for (String groupName : groupNameList) {
            MessageFinancierGroupInfoDO messageFinancierGroupInfoDO = new MessageFinancierGroupInfoDO();
            messageFinancierGroupInfoDO.setGroupName(groupName);
            List<MessageFinancierGroupInfoImportVO> userGroupInfos = dataMap.get(groupName);
            List<String> custOCodeList = userGroupInfos.stream().map(e -> e.getCustOCode()).collect(Collectors.toList());
            String groupOCode = custOCodeList.toString();
            groupOCode = groupOCode.substring(1, groupOCode.length() - 1);
            groupOCode = groupOCode.replaceAll(" ", "");
            messageFinancierGroupInfoDO.setGroupCode(groupOCode);
            this.insertMessageUserGroupInfo(messageFinancierGroupInfoDO);
        }
    }


    //校验导入的管理人是否存在 false 表示不存在，true表示存在
    public boolean verifyFinancierInfo(String custOCode) {
        if (StringUtils.isEmpty(custOCode)) {
            return false;
        }
        QueryWrapper<FinancierDO> query = new QueryWrapper<FinancierDO>();
        query.lambda().eq(FinancierDO::getCustOCode, custOCode);
        List<FinancierDO> financierDOList = financierMapper.selectList(query);
        if (CollectionUtils.isEmpty(financierDOList)) {
            return false;
        }
        return true;
    }


}




