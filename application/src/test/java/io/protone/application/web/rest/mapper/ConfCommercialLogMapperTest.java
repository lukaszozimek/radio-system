package io.protone.application.web.rest.mapper;

/**
 * Created by lukaszozimek on 27.04.2017.
 */
public class ConfCommercialLogMapperTest {
/*
    @Autowired
    private ConfCommercialLogMapper confCommercialLogMapper;

    private CfgExternalSystemLog cfgMarkerConfiguration;

    private ConfCommercialLogPT commercialLogPT;

    private List<ConfCommercialLogPT> confCommercialLogPTS = new ArrayList<>();

    private List<CfgExternalSystemLog> cfgExternalSystemLogs = new ArrayList<>();
    private CorNetwork corNetwork;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        cfgMarkerConfiguration = factory.manufacturePojo(CfgExternalSystemLog.class);
        cfgExternalSystemLogs.add(cfgMarkerConfiguration);
        commercialLogPT = factory.manufacturePojo(ConfCommercialLogPT.class);
        confCommercialLogPTS.add(commercialLogPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        ConfCommercialLogPT mappedDto = confCommercialLogMapper.DB2DTO(cfgMarkerConfiguration);

        assertNotNull(mappedDto.getId());
        assertNotNull(mappedDto.getName());
        assertNotNull(mappedDto.getLenght());
        assertNotNull(mappedDto.getLogColumn());
        assertNotNull(mappedDto.getDelimiter());
        assertNotNull(mappedDto.getColumnSequence());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfCommercialLogPT> mappedDto = confCommercialLogMapper.DBs2DTOs(cfgExternalSystemLogs);
        assertNotNull(mappedDto);
        assertEquals(mappedDto.size(), 1);
        mappedDto.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLenght());
            assertNotNull(dto.getLogColumn());
            assertNotNull(dto.getDelimiter());
            assertNotNull(dto.getColumnSequence());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CfgExternalSystemLog entity = confCommercialLogMapper.DTO2DB(commercialLogPT, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLenght());
        assertNotNull(entity.getLogColumn());
        assertNotNull(entity.getDelimiter());
        assertNotNull(entity.getColumnSequence());

        assertNotNull(entity.getNetwork());
        assertNull(entity.getChannel());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CfgExternalSystemLog> entities = confCommercialLogMapper.DTOs2DBs(confCommercialLogPTS, corNetwork);
        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLenght());
            assertNotNull(entity.getLogColumn());
            assertNotNull(entity.getDelimiter());
            assertNotNull(entity.getColumnSequence());

            assertNotNull(entity.getNetwork());
            assertNull(entity.getChannel());
        });
    }
    */

}
