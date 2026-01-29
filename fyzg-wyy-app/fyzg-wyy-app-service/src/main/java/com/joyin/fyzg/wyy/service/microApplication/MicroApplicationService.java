package com.joyin.fyzg.wyy.service.microApplication;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.wyy.entity.microApplication.MicroApplicationDO;

import java.util.List;

public interface MicroApplicationService {

    MethodResponse insertMicroApplication(MicroApplicationDO microApplicationDO);

    MethodResponse updateMicroApplicationById(MicroApplicationDO microApplicationDO);

    MethodResponse deleteMicroApplicationById(String rId);

	MicroApplicationDO getMicroApplicationById(String rId);

	List<MicroApplicationDO> listAll();

	List<MicroApplicationDO> listMicroApplication(RequestParamMap params);
}
