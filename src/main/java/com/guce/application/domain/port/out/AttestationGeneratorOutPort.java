package com.guce.application.domain.port.out;

import java.util.Map;

public interface AttestationGeneratorOutPort {
    byte[] generate(Map<String, Object> data);
}
