package com.heydancer.controller;

import com.heydancer.dto.DocumentDTO;
import com.heydancer.entity.BinaryContent;
import com.heydancer.entity.Document;
import com.heydancer.service.DocService;
import lombok.extern.log4j.Log4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j
@RequestMapping("/file")
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
public class DocumentController {
    private final DocService docService;

    public DocumentController(DocService docService) {
        this.docService = docService;
    }

    @GetMapping("/download")
    public ResponseEntity<?> getPhoto(@RequestParam("id") String id) {
        Document photo = docService.getPhoto(id);
        if (photo == null) {
            return ResponseEntity.badRequest().build();
        }
        BinaryContent binaryContent = photo.getBinaryContent();
        FileSystemResource fileSystemResource = docService.getFileSystemResource(binaryContent);

        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;")
                .body(fileSystemResource);
    }

    @GetMapping("/download/all")
    public ResponseEntity<?> getAllPhotoZip(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate rangeEnd) {

        List<Document> photo = docService.getAllPhotos(rangeStart, rangeEnd);
        List<Resource> resources = new ArrayList<>();
        for (Document appPhoto : photo) {
            BinaryContent binaryContent = appPhoto.getBinaryContent();
            Resource resource = docService.getFileSystemResource(binaryContent);
            resources.add(resource);
        }

        byte[] buffer = new byte[1024];

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            for (Resource resource : resources) {
                try {
                    InputStream inputStream = resource.getInputStream();
                    ZipEntry zipEntry = new ZipEntry(resource.getFilename() + ".jpeg");
                    zipOutputStream.putNextEntry(zipEntry);

                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }

                    inputStream.close();
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            zipOutputStream.close();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=photos.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public String getAllByFilter(Model model,
                                 @RequestParam(required = false, defaultValue = "") String authorLastName,
                                 @RequestParam(required = false, defaultValue = "") String subdivision,
                                 @RequestParam(required = false, defaultValue= "") String link,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate rangeStart,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate rangeEnd) {
        List<DocumentDTO> documentDTOS = docService.getAllByFilter(authorLastName, subdivision, link, rangeStart, rangeEnd);

        model.addAttribute("documents", documentDTOS);
        model.addAttribute("authorLastName", authorLastName);
        model.addAttribute("subdivision", subdivision);
        model.addAttribute("link", link);
        model.addAttribute("rangeStart", rangeStart);
        model.addAttribute("rangeEnd", rangeEnd);

        return "file";
    }
}
