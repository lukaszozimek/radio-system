package io.protone.custom.service;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomTRAInvoiceMapper;
import io.protone.domain.CORAssociation;
import io.protone.domain.TRAInvoice;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.TRAInvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TRAInvoiceService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private TRAInvoiceRepository traInvoiceRepository;

    @Inject
    private CustomTRAInvoiceMapper customTRAInvoiceMapper;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    public List<TraInvoicePT> getAllInvoice() {

        List<CORAssociation> corAssociationList = new ArrayList<>();
        return null;
    }

    public TraInvoicePT saveInvoice(TraInvoicePT traInvoicePT) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        return null;
    }

    public void deleteInvoice(Long id) {

        List<CORAssociation> corAssociationList = new ArrayList<>();

    }

    public TraInvoicePT getInvoice(Long id) {

        List<CORAssociation> corAssociationList = new ArrayList<>();
        return null;
    }

}
