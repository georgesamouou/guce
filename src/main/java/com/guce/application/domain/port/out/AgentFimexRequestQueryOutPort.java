package com.guce.application.domain.port.out;

import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface AgentFimexRequestQueryOutPort {
    Optional<FimexRequestModel> findByIdForFimexAgent(UUID id);

    Optional<FimexRequestModel> findByIdForFimexSignatory(UUID id);

    Page<FimexRequestSummary> searchForFimexAgent(SearchCriteria criteria);

    Page<FimexRequestSummary> searchForFimexSignatory(SearchCriteria criteria);
}