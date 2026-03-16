package com.guce.application.domain.port.out;

import com.guce.application.domain.model.StatusHistoryModel;
import java.util.List;
import java.util.UUID;

public interface StatusHistoryOutPort {
    StatusHistoryModel save(StatusHistoryModel history);

    List<StatusHistoryModel> findByRequestId(UUID requestId);
}
