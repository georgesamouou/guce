package com.guce.application.application.service;

import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import com.guce.application.domain.port.in.ClientSearchFimexRequestsUseCase;
import com.guce.application.domain.service.FimexRequestQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientSearchFimexRequestsApplicationService implements ClientSearchFimexRequestsUseCase {

    private final FimexRequestQueryService queryService;

    @Override
    public Page<FimexRequestSummary> execute(SearchCriteria criteria) {
        return queryService.searchForClient(criteria);
    }
}