package com.joyin.fyzg.wyy.controller.common;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.wyy.entity.common.ModelEnumDO;
import com.joyin.fyzg.wyy.service.common.ModelEnumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("modelEnum")
@Slf4j
public class ModelEnumController {
    @Autowired
    ModelEnumService modelEnumService;

    @PostMapping("insertDefineEnum")
    public RestResponse insertDefineEnum(@RequestBody ModelEnumDO modelEnumDO){
        return  RestResponse.transMethodResponse(modelEnumService.insertEnum(modelEnumDO));
    }

    @PostMapping("updateEnumById")
    public RestResponse updateEnumById(@RequestBody ModelEnumDO modelEnumDO){
        return  RestResponse.transMethodResponse(modelEnumService.updateEnumById(modelEnumDO));
    }

    @DeleteMapping("deleteEnumById")
    public RestResponse deleteEnumById(@RequestParam("enumCode") String enumCode){
        return  RestResponse.transMethodResponse(modelEnumService.deleteEnumById(enumCode));
    }

    @GetMapping("listAll")
    public RestResponse<List<ModelEnumDO>> listAll(){
        return RestResponse.success(modelEnumService.listAll());
    }

    @GetMapping("listAllByOType")
    public RestResponse<List<ModelEnumDO>> listAll(String oType){
        return RestResponse.success(modelEnumService.listAllByType(oType));
    }

    @GetMapping("listEnumByEnumName")
    public RestResponse<List<ModelEnumDO>> listEnumByEnumName(@RequestParam("enumName") String enumName){
        return RestResponse.success(modelEnumService.listEnumByEnumName(enumName));
    }
}
