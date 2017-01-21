package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.TRACampaignRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
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

    public TraCampaignPT saveCampaign() {

        return null;
    }

    public void deleteCampaign() {

    }

    public TraCampaignPT getCampaign(String shortcut) {
        return null;
    }
}
