package io.protone.library.service.metadata.document;

/**
 * Created by lukaszozimek on 09/06/2017.
 */
public enum SupportedDocumentContentTypes {
    XLS("application/vnd.ms-excel"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    private String name;

    public String getName() {
        return this.name;
    }

    SupportedDocumentContentTypes(String name) {
        this.name = name;
    }
}
