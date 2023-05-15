package com.heydancer.mapper;

import com.heydancer.dto.DocumentDTO;
import com.heydancer.entity.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class DocumentMapper implements BaseMapper<DocumentDTO, Document> {
    @Override
    public DocumentDTO toDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .authorFirstName(document.getMover().getFirstName())
                .authorLastName(document.getMover().getLastName())
                .subdivision(document.getMover().getSubdivision())
                .link(document.getMover().getLink())
                .created(document.getCreated())
                .build();
    }

    @Override
    public List<DocumentDTO> toDTOList(List<Document> documents) {
        return documents.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
