package com.guce.application.adapter.out.persistence.mapper;

import com.guce.application.domain.model.FimexAttachmentInfo;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.RefProductInfo;
import com.guce.application.infrastructure.persistence.entities.FimexAttachmentEntity;
import com.guce.application.infrastructure.persistence.entities.FimexProductEntity;
import com.guce.application.infrastructure.persistence.entities.FimexRequestEntity;
import com.guce.application.infrastructure.persistence.entities.RefProductEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {
        DocumentStatusMapper.class,
        RefDataPersistenceMapper.class
})
public interface FimexRequestPersistenceMapper {

    // ── FimexRequest : Domain → Entity ──
    // client & requestType : auto-mapped via RefDataPersistenceMapper (uses)

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "signatory", ignore = true)
    @Mapping(target = "renewal", source = "isRenewal")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FimexRequestEntity toEntity(FimexRequestModel model);

    @AfterMapping
    default void mapProducts(FimexRequestModel model, @MappingTarget FimexRequestEntity entity) {
        if (model.products() != null) {
            entity.setProducts(
                    model.products().stream()
                            .map(product -> {
                                RefProductEntity refProduct = new RefProductEntity();
                                refProduct.setCodeSh(product.codeSh());
                                return new FimexProductEntity(entity, refProduct);
                            })
                            .collect(Collectors.toList()));
        } else {
            entity.setProducts(new ArrayList<>());
        }
    }

    // ── FimexRequest : Entity → Domain ──
    // attachments mapped via toAttachmentInfo (metadata-only, no content)

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "requestType", source = "requestType")
    @Mapping(target = "products", source = "products", qualifiedByName = "productsToRefProductInfo")
    @Mapping(target = "attachments", source = "attachments")
    @Mapping(target = "isRenewal", source = "renewal")
    FimexRequestModel toDomain(FimexRequestEntity entity);

    // ── FimexRequest : Entity → Summary (lightweight for search) ──

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToStatus")
    @Mapping(target = "taxpayerNumber", source = "client.taxpayerNumber")
    @Mapping(target = "companyName", source = "client.companyName")
    @Mapping(target = "treatment", source = "status", qualifiedByName = "statusToTreatment")
    @Mapping(target = "decisionDate", source = "expiryDate")
    @Mapping(target = "requestTypeLabel", source = "requestType.label")
    FimexRequestSummary toSummary(FimexRequestEntity entity);

    // ── Product helpers ──

    @Named("productsToRefProductInfo")
    default List<RefProductInfo> productsToRefProductInfo(List<FimexProductEntity> products) {
        if (products == null)
            return new ArrayList<>();
        return products.stream()
                .map(p -> new RefProductInfo(p.getProduct().getCodeSh(), p.getProduct().getDescription()))
                .collect(Collectors.toList());
    }

    // ── Attachment : Entity → Info (metadata-only, no content) ──

    FimexAttachmentInfo toAttachmentInfo(FimexAttachmentEntity entity);
}
