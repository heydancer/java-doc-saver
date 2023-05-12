package com.heydancer.service.impl;

import com.heydancer.DocumentRepository;
import com.heydancer.entity.BinaryContent;
import com.heydancer.entity.Document;
import com.heydancer.service.DocService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j
@Service
public class DocServiceImpl implements DocService {
    private final DocumentRepository documentRepository;

    public DocServiceImpl(DocumentRepository appPhotoRepository) {
        this.documentRepository = appPhotoRepository;
    }

    @Override
    public Document getPhoto(String photoId) {
        Long id = Long.parseLong(photoId);
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            //TODO добавить генерацию имени временного файла
            File temp = File.createTempFile("tempFile", ".bin");
            temp.deleteOnExit();
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }


    @Override
    public List<Document> getAllPhotos(LocalDate rangeStart, LocalDate rangeEnd) {
        //TODO  добавить проверку времен

        return documentRepository.findAll(rangeStart, rangeEnd);
    }
}
