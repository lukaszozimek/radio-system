package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.domain.LIBMediaItem;
import io.protone.domain.enumeration.LIBItemStateEnum;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomItemMapperExt {

    @Inject
    CustomLIBAlbumMapper albumMapper;

    @Inject
    CustomLIBArtistMapper artistMapper;

    @Inject
    CustomCORPersonMapperExt personMapper;

    @Inject
    CustomLIBLabelMapperExt labelMapper;

    @Inject
    CustomLIBLibraryMapperExt libraryMapper;

    @Inject
    CustomLIBMarkerMapperExt markerMapper;

    @Inject
    CustomLIBTrackMapperExt trackMapper;

    public LibItemPT DB2DTO(LIBMediaItem db) {

        if (db == null )
            return null;

        LibItemPT dto = new LibItemPT()
            .idx(db.getIdx())
            .length(db.getLength().intValue())
            .name(db.getName())
            //.resourceType(LibItemPT.ResourceTypeEnum.valueOf(db.getItemType().toString()))
            .state(LibItemPT.StateEnum.fromValue(db.getState().toString()))
            .label(labelMapper.DB2DTO(db.getLabel()))
            .album(albumMapper.DB2DTO(db.getAlbum()))
            .artist(artistMapper.DB2DTO(db.getArtist()))
            .library(libraryMapper.DB2DTO(db.getLibrary()))
            .track(trackMapper.DB2DTO(db.getTrack()))
            ;

        dto.author(null).composer(null); //add author and composer associacions
        dto.markers(null); //add markers

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

        LIBMediaItem db = new LIBMediaItem()
            .idx(dto.getIdx())
            .length(dto.getLength().longValue())
            .name(dto.getName())
            //.itemType(LIBItemTypeEnum.valueOf(dto.getResourceType().toString()))
            .state(LIBItemStateEnum.valueOf(dto.getState().toString()))
            .label(labelMapper.DTO2DB(dto.getLabel()))
            .album(albumMapper.DTO2DB(dto.getAlbum()))
            .artist(artistMapper.DTO2DB(dto.getArtist()))
            .library(libraryMapper.DTO2DB(dto.getLibrary()))
            .track(trackMapper.DTO2DB(dto.getTrack()));

        //add author and composer associacions
        //add markers

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
