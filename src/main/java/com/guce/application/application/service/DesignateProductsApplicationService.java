package com.guce.application.application.service;

import com.guce.application.domain.model.DesignateProductsCommand;
import com.guce.application.domain.port.in.DesignateProductsUseCase;
import com.guce.application.domain.service.FimexRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignateProductsApplicationService implements DesignateProductsUseCase {

    private final FimexRequestService fimexRequestService;

    @Override
    public void execute(DesignateProductsCommand command) {
        fimexRequestService.designateProducts(command);
    }
}
