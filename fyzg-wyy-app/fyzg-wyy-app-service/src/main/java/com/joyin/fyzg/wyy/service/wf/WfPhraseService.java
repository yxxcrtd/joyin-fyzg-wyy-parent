package com.joyin.fyzg.wyy.service.wf;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.wyy.entity.wf.WfPhraseDO;

import java.util.List;

public interface WfPhraseService {

    MethodResponse insertPhrase(WfPhraseDO actCcDO);

    MethodResponse updatePhraseById(WfPhraseDO actCcDO);

    MethodResponse deletePhraseById(String rId);

    WfPhraseDO getPhraseById(String rId);

    List<WfPhraseDO> listPhraseByUser(String loginUserCode);

    List<WfPhraseDO> listAll();
}
