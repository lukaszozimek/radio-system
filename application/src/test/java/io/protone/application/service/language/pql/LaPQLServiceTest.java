package io.protone.application.service.language.pql;


import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.language.service.pql.LaPQLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static io.protone.application.util.TestConstans.TEST_ORGANIZATION_ID;
import static io.protone.application.util.TestConstans.TEST_ORGANIZATION_SHORTCUT;

/**
 * Created by lukaszozimek on 29.04.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LaPQLServiceTest {
    private static final String SAMPLE = "sample";
    private static final String SAMPLE_SHORTCUT = "sam";
    private static final String SAMPLE_NET = "net";

    @Autowired
    private LaPQLService pqlService;

    @Autowired
    private CorChannelRepository corChannelRepository;

    private CorOrganization corOrganization;

    @Before
    public void setUp() throws Exception {
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        CorChannel corChannel = new CorChannel().shortcut(SAMPLE_SHORTCUT).name(SAMPLE).organization(corOrganization);
        corChannelRepository.saveAndFlush(corChannel);

    }

    @Test
    public void shouldReturnCorChannel() throws Exception {
        //when
        CorFilter corFilter = new CorFilter().value("Core Channel");
        List list = pqlService.getObjectList(corFilter);
        Assert.notNull(list);

    }

    @Test
    public void shouldReturnCorChannelWithPredicateName() throws Exception {
        //when
        CorFilter corFilter = new CorFilter().value("Core Channel AND name='sample'");
        List list = pqlService.getObjectList(corFilter);
        Assert.notNull(list);
        List<CorChannel> listChannel = (List<CorChannel>) list;
    }

    @Test
    public void shouldReturnCorChannelWithPredicateNameAndorganizationShortcutPredicate() throws Exception {
        //when
        CorFilter corFilter = new CorFilter().value("Core Channel AND name='sample'");
        List list = pqlService.getObjectList(corFilter);
        Assert.notNull(list);
        List<CorChannel> listChannel = (List<CorChannel>) list;
        Assert.notEmpty(list);
    }

    @Test
    public void shouldReturnCorObjectRequestedInQueryWithComplexPredicate() throws Exception {
        CorFilter corFilter = new CorFilter().value("Core Channel AND name='sample' AND organization.shortcut='test'");
        List list = pqlService.getObjectList(corFilter);
        Assert.notNull(list);
        List<CorChannel> listChannel = (List<CorChannel>) list;
        Assert.notEmpty(list);

    }

    @Test
    public void shouldReturnListCorObjectRequestedInComplexPrediactQuery() throws Exception {
        CorFilter corFilter = new CorFilter().value("Core Channel AND ( name='xx' AND organization.shortcut='xx' ) OR organization.shortcut='test'");
        List list = pqlService.getObjectList(corFilter);
        Assert.notNull(list);
        List<CorChannel> listChannel = (List<CorChannel>) list;
        Assert.notEmpty(list);
    }

    @Test
    public void shouldReturnListCorObjectRequestedInQueryMulitiPredicateQuery() throws Exception {
        CorFilter corFilter = new CorFilter().value("Core Channel AND ( name='xx' AND organization.shortcut='xx' ) OR ( organization.shortcut='test' AND name='sample') OR  name='x'");
        List list = pqlService.getObjectList(corFilter);
        Assert.notNull(list);
        List<CorChannel> listChannel = (List<CorChannel>) list;
        Assert.notEmpty(list);
    }

    //TODO: Implement Test
    @Test
    public void shouldReturnListWithLimitationAndGroupedByObjectRequestedInQuery() {

    }


}
