package com.guce.application.infrastructure.persistence.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FimexProductId implements Serializable {

    private UUID request;
    private String product;

    public FimexProductId() {
    }

    public FimexProductId(UUID request, String product) {
        this.request = request;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FimexProductId that = (FimexProductId) o;
        return Objects.equals(request, that.request) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, product);
    }
}
