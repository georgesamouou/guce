package com.guce.application.domain.port.out;

import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ClientFimexRequestQueryOutPort {
    Optional<FimexRequestModel> findByIdForClient(UUID id, UUID clientId);

    Page<FimexRequestSummary> searchForClient(SearchCriteria criteria);
}