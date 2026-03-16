package com.guce.application.domain.port.out;

import com.guce.application.domain.model.FimexRequestModel;

import java.util.Optional;

public interface FimexRequestRepositoryOutPort {
    Optional<FimexRequestModel> findById(java.util.UUID id);

    Optional<FimexRequestModel> findByIdForUpdate(java.util.UUID id);

    FimexRequestModel save(FimexRequestModel document);
}
