package com.guce.application.adapter.in.web.mapper;

import com.guce.application.adapter.in.web.dto.ClientDTO;
import com.guce.application.adapter.in.web.dto.FimexAttachmentDTO;
import com.guce.application.adapter.in.web.dto.FimexRequestDetailDTO;
import com.guce.application.adapter.in.web.dto.FimexRequestListDTO;
import com.guce.application.adapter.in.web.dto.ProductDTO;
import com.guce.application.adapter.in.web.dto.RequestTypeDTO;
import com.guce.application.adapter.out.persistence.mapper.DocumentStatusMapper;
import com.guce.application.domain.model.FimexAttachmentInfo;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.RefClientModel;
import com.guce.application.domain.model.RefProductInfo;
import com.guce.application.domain.model.RefRequestTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { DocumentStatusMapper.class })
public interface FimexWebMapper {

    // ── FimexRequestModel → DetailDTO ──

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    FimexRequestDetailDTO toDetailDTO(FimexRequestModel model);

    // ── FimexRequestSummary → ListDTO ──

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    FimexRequestListDTO toListDTO(FimexRequestSummary summary);

    // ── Nested DTOs (MapStruct auto-uses these) ──

    ClientDTO toClientDTO(RefClientModel model);

    RequestTypeDTO toRequestTypeDTO(RefRequestTypeModel model);

    ProductDTO toProductDTO(RefProductInfo info);

    FimexAttachmentDTO toAttachmentDTO(FimexAttachmentInfo info);
}
