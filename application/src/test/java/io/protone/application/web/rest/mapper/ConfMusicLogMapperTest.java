package io.protone.application.web.rest.mapper;


/**
 * Created by lukaszozimek on 27.04.2017.
 */
public class ConfMusicLogMapperTest {
/*
    @Autowired
    private ConfMusicLogMapper confMusicLogMapper;

    private CfgExternalSystemLog cfgExternalSystemLog;

    private ConfMusicLogPT confMusicLogPT;

    private List<ConfMusicLogPT> confMusicLogPTS = new ArrayList<>();

    private List<CfgExternalSystemLog> cfgExternalSystemLogs = new ArrayList<>();

    private CorNetwork corNetwork;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        cfgExternalSystemLog = factory.manufacturePojo(CfgExternalSystemLog.class);
        cfgExternalSystemLogs.add(cfgExternalSystemLog);
        confMusicLogPT = factory.manufacturePojo(ConfMusicLogPT.class);
        confMusicLogPTS.add(confMusicLogPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        ConfMusicLogPT mappedDto = confMusicLogMapper.DB2DTO(cfgExternalSystemLog);

        assertNotNull(mappedDto.getId());
        assertNotNull(mappedDto.getName());
        assertNotNull(mappedDto.getLenght());
        assertNotNull(mappedDto.getLogColumn());
        assertNotNull(mappedDto.getDelimiter());
        assertNotNull(mappedDto.getColumnSequence());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfMusicLogPT> mappedDto = confMusicLogMapper.DBs2DTOs(cfgExternalSystemLogs);
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
        CfgExternalSystemLog entity = confMusicLogMapper.DTO2DB(confMusicLogPT, corNetwork);

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
        List<CfgExternalSystemLog> entities = confMusicLogMapper.DTOs2DBs(confMusicLogPTS, corNetwork);
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
