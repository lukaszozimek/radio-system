package io.protone.custom.service;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.domain.CRMContact;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class CRMContactService {

    @Inject
    CustomCRMContactMapper customCRMContactMapper;

    @Inject
    private CRMContactRepository crmContactRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CORAddressRepository corAddressRepository;

    @Inject
    private CORAreaRepository corAreaRepository;

    @Inject
    private CORPersonRepository corPersonRepository;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject

    public List<CrmContactPT> getAllContact() {

        return null;
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT) {

        return null;
    }

    public void deleteContact() {

    }

    public CrmContactPT getContact(String shortcut) {
        return null;
    }
}
