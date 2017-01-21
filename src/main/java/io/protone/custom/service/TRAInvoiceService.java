package io.protone.custom.service;

import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.domain.TRAInvoice;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.TRAInvoiceRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class TRAInvoiceService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private TRAInvoiceRepository traInvoiceRepository;

    public List<TraInvoicePT> getAllInvoice() {

        return null;
    }

    public TraInvoicePT saveInvoice(TraInvoicePT traInvoicePT) {

        return null;
    }

    public void deleteInvoice(Long id) {

    }

    public TraInvoicePT getInvoice(Long id) {
        return null;
    }

}
