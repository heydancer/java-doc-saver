package com.heydancer.service;

import com.heydancer.dto.DocumentDTO;
import com.heydancer.entity.BinaryContent;
import com.heydancer.entity.Document;
import org.springframework.core.io.FileSystemResource;

import java.time.LocalDate;
import java.util.List;

public interface DocService {
    Document getPhoto(Long id);

    FileSystemResource getFileSystemResource(BinaryContent binaryContent);

    List<Document> getAllPhotos(LocalDate rangeStart, LocalDate rangeEnd);

    List<DocumentDTO> getAllByFilter(String authorLastName,
                                     String subdivision,
                                     String link,
                                     LocalDate rangeStart,
                                     LocalDate rangeEnd);
}
