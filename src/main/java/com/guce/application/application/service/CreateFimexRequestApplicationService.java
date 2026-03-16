package com.guce.application.application.service;

import com.guce.application.domain.model.CreateFimexRequestCommand;
import com.guce.application.domain.port.in.CreateFimexRequestUseCase;
import com.guce.application.domain.service.FimexRequestService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateFimexRequestApplicationService implements CreateFimexRequestUseCase {

    private final FimexRequestService fimexRequestService;

    @Override
    public UUID execute(CreateFimexRequestCommand command) {
        return fimexRequestService.create(command);
    }
}
