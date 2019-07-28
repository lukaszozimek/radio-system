package io.protone.language.service.pql;

import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.protone.core.domain.enumeration.CorEntityTypeEnum.*;

/**
 * Created by lukaszozimek on 19.07.2017.
 */
@Service
public class LaPQLMappingService {
    private final Logger log = LoggerFactory.getLogger(LaPQLMappingService.class);

    Map<CorEntityTypeEnum, String> mappingClass = new HashMap<>();
    @Autowired
    private ApplicationContext applicationContext;

    public LaPQLMappingService() {
        mappingClass.put(Person, "corPersonMapperImpl");
        mappingClass.put(Channel, "corChannelMapperImpl");
        mappingClass.put(Adress, "corAddressMapperImpl");
        mappingClass.put(Property, "corPropertyKeyMapperImpl");
        mappingClass.put(MediaPlan, "traMediaPlanMapperImpl");
        mappingClass.put(Advertisement, "traAdvertisementMapperImpl");
        mappingClass.put(Campaign, "traCampaignMapperImpl");
        mappingClass.put(Invoice, "traInvoiceMapperImpl");
        mappingClass.put(Order, "traOrderMapperImpl");
        mappingClass.put(Playlist, "traPlaylist");
        mappingClass.put(Customer, "crmAccountMapperImpl");
        mappingClass.put(Lead, "crmLeadMapperImpl");
        mappingClass.put(Opportunity, "crmOpportunityMapperImpl");
        mappingClass.put(Contact, "crmContactMapperImpl");
        mappingClass.put(Task, "crmTaskMapperImpl");
        mappingClass.put(MediaItem, "libItemMapperImpl");
        mappingClass.put(Library, "libLibraryMapperImpl");
        mappingClass.put(Label, "libLabelMapperImpl");
        mappingClass.put(Album, "libAlbumMapperImpl");
        mappingClass.put(Track, "libTrackMapperImpl");
        mappingClass.put(Artist, "libArtistMapperImpl");
        mappingClass.put(Events, "corPropertyKeyImpl");
    }

    public List DBs2DTOs(List elementsToMap, CorEntityTypeEnum type) {
        Object mapper = applicationContext.getBean(mappingClass.get(type));
        Method method = null;
        try {
            method = mapper.getClass().getMethod("DBs2DTOs", List.class);
            return (List) method.invoke(mapper, elementsToMap);

        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            log.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
