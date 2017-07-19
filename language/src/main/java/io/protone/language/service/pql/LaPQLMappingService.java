package io.protone.language.service.pql;

import io.protone.core.domain.CorPropertyKey;
import io.protone.core.domain.enumeration.CorEntityTypeEnum;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.core.mapper.CorContactMapper;
import io.protone.core.mapper.CorPersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lukaszozimek on 19.07.2017.
 */
@Service
public class LaPQLMappingService {

    Map<String, String> mappingClass = new HashMap<>();
    @Autowired
    private ApplicationContext applicationContext;

    public LaPQLMappingService() {
        mappingClass.put("Person", "corPersonMapperImpl");
        mappingClass.put("Channel", "corChannelMapperImpl");
        mappingClass.put("Adress", "corAddressMapperImpl");
        mappingClass.put("Contact", "corContactMapperImpl");
        mappingClass.put("Property", "corPropertyKeyImpl");
    }

    public List DBs2DTOs(List elementsToMap, CorEntityTypeEnum type) {
        Object mapper = applicationContext.getBean(mappingClass.get(type));
        Method method = null;
        try {
            method = mapper.getClass().getMethod("DBs2DTOs", List.class);
            return (List) method.invoke(mapper, elementsToMap);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}
