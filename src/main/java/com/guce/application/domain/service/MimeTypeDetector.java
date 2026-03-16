package com.guce.application.domain.service;

import java.util.Map;

public final class MimeTypeDetector {

    private MimeTypeDetector() {
    }

    private static final Map<String, byte[]> MAGIC_BYTES = Map.ofEntries(
            Map.entry("application/pdf", new byte[] { 0x25, 0x50, 0x44, 0x46 }), // %PDF
            Map.entry("image/png", new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47 }), // .PNG
            Map.entry("image/jpeg", new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF }), // JPEG
            Map.entry("image/gif", new byte[] { 0x47, 0x49, 0x46, 0x38 }), // GIF8
            Map.entry("image/bmp", new byte[] { 0x42, 0x4D }), // BM
            Map.entry("image/tiff", new byte[] { 0x49, 0x49, 0x2A, 0x00 }), // II*.
            Map.entry("application/zip", new byte[] { 0x50, 0x4B, 0x03, 0x04 }), // PK..
            Map.entry("application/gzip", new byte[] { 0x1F, (byte) 0x8B }), // gzip
            Map.entry("application/xml", new byte[] { 0x3C, 0x3F, 0x78, 0x6D, 0x6C }) // <?xml
    );

    /**
     * Detects MIME type from file content using magic bytes.
     * Falls back to application/octet-stream if unrecognized.
     */
    public static String detect(byte[] content, String fileName) {
        if (content == null || content.length < 2) {
            return "application/octet-stream";
        }

        for (Map.Entry<String, byte[]> entry : MAGIC_BYTES.entrySet()) {
            if (startsWith(content, entry.getValue())) {
                // ZIP-based formats: differentiate by extension
                if ("application/zip".equals(entry.getKey()) && fileName != null) {
                    return detectZipBasedFormat(fileName);
                }
                return entry.getKey();
            }
        }

        return "application/octet-stream";
    }

    private static boolean startsWith(byte[] content, byte[] magic) {
        if (content.length < magic.length)
            return false;
        for (int i = 0; i < magic.length; i++) {
            if (content[i] != magic[i])
                return false;
        }
        return true;
    }

    private static String detectZipBasedFormat(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".docx"))
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (lower.endsWith(".xlsx"))
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (lower.endsWith(".pptx"))
            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        if (lower.endsWith(".odt"))
            return "application/vnd.oasis.opendocument.text";
        if (lower.endsWith(".ods"))
            return "application/vnd.oasis.opendocument.spreadsheet";
        return "application/zip";
    }
}
