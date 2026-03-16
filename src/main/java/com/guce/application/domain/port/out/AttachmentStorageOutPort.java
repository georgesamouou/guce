package com.guce.application.domain.port.out;

import com.guce.application.domain.model.FimexAttachmentModel;
import java.util.Optional;
import java.util.UUID;

public interface AttachmentStorageOutPort {
    FimexAttachmentModel save(FimexAttachmentModel attachment);

    Optional<FimexAttachmentModel> findById(UUID attachmentId);

    void deleteById(UUID attachmentId);
}
