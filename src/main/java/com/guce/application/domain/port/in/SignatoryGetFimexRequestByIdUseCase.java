package com.guce.application.domain.port.in;

import com.guce.application.domain.model.FimexRequestModel;
import java.util.UUID;

public interface SignatoryGetFimexRequestByIdUseCase {
    FimexRequestModel execute(UUID requestId);
}
