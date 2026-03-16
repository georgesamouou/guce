package com.guce.application.adapter.out.persistence;

import com.guce.application.domain.port.out.FimexReferenceNumberPort;
import com.guce.application.infrastructure.persistence.repository.FimexSequenceJpaRepository;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FimexReferenceNumberAdapter implements FimexReferenceNumberPort {

    private final FimexSequenceJpaRepository sequenceRepository;

    @Override
    public long nextValue() {
        return sequenceRepository.getNextReferenceNumber();
    }
}
