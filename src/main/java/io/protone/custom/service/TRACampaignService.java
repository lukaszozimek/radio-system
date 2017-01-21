package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.TRACampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TRACampaignService {

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CORAssociationRepository corAssociationRepositor;

    @Inject
    private TRACampaignRepository traCampaignRepository;


    public List<TraCampaignPT> getAllCampaign() {

        return null;
    }

    public TraCampaignPT saveCampaign(TraCampaignPT campaignPT) {

        return null;
    }

    public void deleteCampaign(String shortcut) {

    }

    public TraCampaignPT getCampaign(String shortcut) {
        return null;
    }

    public List<TraCampaignPT> getCustomerCampaing(String shortcut) {
        return null;
    }

}
