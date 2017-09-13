package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.repository.SchLogColumnRepostiory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 29/08/2017.
 */
@Service
public class SchLogColumnService {
    private final Logger log = LoggerFactory.getLogger(SchLogColumnService.class);
    @Inject
    private SchLogColumnRepostiory schLogColumnRepostiory;

    @Transactional
    public Set<SchLogColumn> saveLogColumn(Set<SchLogColumn> schLogColumnSet) {
        if (schLogColumnSet != null) {
            return schLogColumnSet.stream().map(schLogColumn -> schLogColumnRepostiory.saveAndFlush(schLogColumn)).collect(toSet());
        }
        return null;
    }

    @Transactional
    public void deleteColumns(Set<SchLogColumn> schLogColumnSet) {
        if (schLogColumnSet != null) {
            schLogColumnSet.stream().forEach(schLogColumn -> schLogColumnRepostiory.delete(schLogColumn));
        }
        schLogColumnRepostiory.flush();

    }
}
