package com.guce.application.domain.model;

public record AttachmentDownload(
        String fileName,
        String contentType,
        byte[] content) {
}
