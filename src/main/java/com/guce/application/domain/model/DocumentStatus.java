package com.guce.application.domain.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum DocumentStatus {
    INITIATED("PENDING_SUBMISSION"),
    SUBMITTED("AGENT_PROCESSING"),
    VALIDATED_AGENT("SIGNATORY_SIGNATURE"),
    REJECTED("REJECTED"),
    COMPLEMENT_REQUESTED("PENDING_CLIENT"),
    SIGNED("SIGNED"),
    EXPIRED("EXPIRED");

    private final String treatmentKey;

    DocumentStatus(String treatmentKey) {
        this.treatmentKey = treatmentKey;
    }

    public String getTreatmentKey() {
        return treatmentKey;
    }

    private static final Map<DocumentStatus, Set<DocumentStatus>> TRANSITIONS = Map.of(
            INITIATED, Set.of(SUBMITTED),
            SUBMITTED, Set.of(VALIDATED_AGENT, REJECTED, COMPLEMENT_REQUESTED),
            COMPLEMENT_REQUESTED, Set.of(SUBMITTED),
            VALIDATED_AGENT, Set.of(SIGNED, REJECTED, COMPLEMENT_REQUESTED),
            SIGNED, Set.of(EXPIRED),
            EXPIRED, Set.of(INITIATED));

    public boolean canTransitionTo(DocumentStatus target) {
        return TRANSITIONS.getOrDefault(this, Set.of()).contains(target);
    }

    public DocumentStatus transitionTo(DocumentStatus target) {
        if (!canTransitionTo(target)) {
            throw new IllegalStateException(
                    "Transition invalide : " + this + " → " + target);
        }
        return target;
    }

    public static Set<String> getAllTreatments() {
        return Arrays.stream(DocumentStatus.values()).map(DocumentStatus::getTreatmentKey).collect(Collectors.toSet());
    }

    public static DocumentStatus fromTreatment(String treatment) {
        if (treatment == null) return null;
        return Arrays.stream(DocumentStatus.values())
                .filter(status -> status.getTreatmentKey().equals(treatment))
                .findFirst()
                .orElse(null);
    }
}