package com.heydancer.service;

import com.heydancer.entity.BinaryContent;
import com.heydancer.entity.Document;
import org.springframework.core.io.FileSystemResource;

import java.time.LocalDate;
import java.util.List;

public interface DocService {
    Document getPhoto(String id);
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);

    List<Document> getAllPhotos(LocalDate rangeStart, LocalDate rangeEnd);

}
