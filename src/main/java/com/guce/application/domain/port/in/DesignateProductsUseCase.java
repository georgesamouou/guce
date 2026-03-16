package com.guce.application.domain.port.in;

import com.guce.application.domain.model.DesignateProductsCommand;

public interface DesignateProductsUseCase {
    void execute(DesignateProductsCommand command);
}
