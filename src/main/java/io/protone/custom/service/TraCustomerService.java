package io.protone.custom.service;

import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.custom.service.dto.TraInvoicePT;
import io.protone.service.mapper.CrmAccountMapper;
import io.protone.domain.*;
import io.protone.repository.custom.CustomCorAddressRepository;
import io.protone.repository.custom.CustomCorContactRepository;
import io.protone.repository.custom.CustomCorPersonRepository;
import io.protone.repository.custom.CustomCrmAccountRepositoryEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service

@Transactional
public class TraCustomerService {

    private final Logger log = LoggerFactory.getLogger(TraCustomerService.class);

    @Inject
    private CustomCrmAccountRepositoryEx crmAccountRepository;

    @Inject
    private CrmAccountMapper customCrmAccountMapper;

    @Inject
    private CustomCorContactRepository corContactRepository;

    @Inject
    private CustomCorPersonRepository personRepository;

    @Inject
    private CustomCorAddressRepository addressRepository;


    public List<TraCustomerPT> getAllCustomers(CorNetwork corNetwork, Pageable pageable) {
        return crmAccountRepository.findAllByNetwork_Shortcut(corNetwork.getShortcut(), pageable).stream().map(customers -> getCustomer(customers, corNetwork)).collect(toList());
    }

    public TraCustomerPT saveCustomers(TraCustomerPT traCustomerPT, CorNetwork corNetwork) {

        CrmAccount crmAccount = customCrmAccountMapper.traDTO2DB(traCustomerPT, corNetwork);

        log.debug("Persisting CorAddress: {}", crmAccount.getAddres());
        CorAddress address = addressRepository.saveAndFlush(crmAccount.getAddres());

        log.debug("Persisting CorPerson: {}", crmAccount.getPerson());
        CorPerson person = personRepository.saveAndFlush(crmAccount.getPerson());

        log.debug("Persisting CorContact: {}", crmAccount.getPerson().getContacts());
        List<CorContact> corContact = corContactRepository.save(crmAccount.getPerson().getContacts());
        crmAccount.getPerson().setContacts(corContact.stream().collect(Collectors.toSet()));

        log.debug("Persisting CorPerson Contacts");
        corContact.stream().forEach(corContact1 -> corContactRepository.save(corContact1.person(person)));
        crmAccount.setAddres(address);
        crmAccount.person(person);

        log.debug("Persisting CrmAccount: {}", crmAccount);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        return customCrmAccountMapper.traDB2DTO(crmAccount);

    }

    public void deleteCustomer(String shortcut, CorNetwork corNetwork) {
        crmAccountRepository.deleteByShortNameAndNetwork_Shortcut(shortcut, corNetwork.getShortcut());
    }

    public TraCustomerPT getCustomer(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork.getShortcut());
        return getCustomer(crmAccount, corNetwork);
    }

    public TraCustomerPT update(TraCustomerPT traCustomerPT, CorNetwork corNetwork) {
        return saveCustomers(traCustomerPT, corNetwork);
    }

    public TraCustomerPT getCustomer(CrmAccount crmAccount, CorNetwork corNetwork) {

        return customCrmAccountMapper.traDB2DTO(crmAccount);
    }


    public TraInvoicePT getCustomerInvoice(String shortcut) {
        return null;
    }

}
