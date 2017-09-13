package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchLogColumn;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 29/08/2017.
 */
public interface SchLogColumnRepostiory extends JpaRepository<SchLogColumn, Long> {
}
