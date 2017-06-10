package io.protone.service.library.metadata.document;

/**
 * Created by lukaszozimek on 09/06/2017.
 */
public enum SupportedDocumentContentTypes {
    XLS("application/vnd.ms-excel");
    private String name;

    public String getName() {
        return this.name;
    }

    SupportedDocumentContentTypes(String name) {
        this.name = name;
    }
}
