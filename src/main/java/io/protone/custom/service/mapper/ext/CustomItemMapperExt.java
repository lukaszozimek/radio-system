package io.protone.custom.service.mapper.ext;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.domain.LIBMediaItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomItemMapperExt {


    public LibItemPT DB2DTO(LIBMediaItem db) {

        if (db == null )
            return null;

        LibItemPT dto = new LibItemPT();

        //fill dto

        return dto;
    }

    public List<LibItemPT> DBs2DTOs(List<LIBMediaItem> dbs) {

        if ( dbs == null )
            return null;

        List<LibItemPT> dtos = new ArrayList<>();
        for (LIBMediaItem item : dbs )
            dtos.add( DB2DTO(item));

        return dtos;
    }

    public LIBMediaItem DTO2DB(LibItemPT dto) {

        if (dto == null)
            return null;

        LIBMediaItem db = new LIBMediaItem();

        //fill db

        return db;
    }

    public List<LIBMediaItem> DTOs2DBs(List<LibItemPT> dtos) {

        if (dtos == null)
            return null;

        List<LIBMediaItem> dbs = new ArrayList<>();
        for (LibItemPT item : dtos )
            dbs.add( DTO2DB(item));

        return dbs;
    }
}
