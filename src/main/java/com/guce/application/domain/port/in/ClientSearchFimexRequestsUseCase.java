package com.guce.application.domain.port.in;

import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import org.springframework.data.domain.Page;

public interface ClientSearchFimexRequestsUseCase {
    Page<FimexRequestSummary> execute(SearchCriteria criteria);
}