package com.sebarys.gazeWebsite.model;

public enum AttachmentType {
    PHOTO, MOVIE, DOCUMENT ;

    public String attachmentType;

    private AttachmentType() {

    }

    private AttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}
