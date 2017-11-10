package io.protone.scheduler.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * A SchEventTemplate.
 */
@Embeddable
public class SchEventTemplateEvnetTemplateId implements Serializable {


    @ManyToOne
    private SchEventTemplate parentTemplate;

    @ManyToOne
    private SchEventTemplate childTemplate;


    public SchEventTemplate getParentTemplate() {
        return parentTemplate;
    }

    public void setParentTemplate(SchEventTemplate parentTemplate) {
        this.parentTemplate = parentTemplate;
    }

    public SchEventTemplate getChildTemplate() {
        return childTemplate;
    }

    public void setChildTemplate(SchEventTemplate childTemplate) {
        this.childTemplate = childTemplate;
    }
}
