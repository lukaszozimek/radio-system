package io.protone.custom.service;

import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.cor.CorPersonRepository;
import io.protone.repository.library.LibLabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
public class LibLabelServiceTest {
    @Autowired
    private LibLabelService libLabelService;

    @Autowired
    private LibLabelRepository libLabelRepository;

    @Autowired
    private CorNetworkRepository networkRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = networkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void saveLibLabel() throws Exception {
    }

}
