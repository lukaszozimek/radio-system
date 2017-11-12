package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEventTemplateEvnetTemplate;
import io.protone.scheduler.domain.SchEventTemplateEvnetTemplateId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 11/11/2017.
 */
public interface SchEventTemplateEvnetTemplateRepostiory extends JpaRepository<SchEventTemplateEvnetTemplate, SchEventTemplateEvnetTemplateId> {
    void deleteAllByPk_ParentTemplate_Id(Long id);
}
