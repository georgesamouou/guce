package com.guce.application.domain.port.in;

import com.guce.application.domain.model.CreateFimexRequestCommand;
import java.util.UUID;

@FunctionalInterface
public interface CreateFimexRequestUseCase {
    UUID execute(CreateFimexRequestCommand command);
}
