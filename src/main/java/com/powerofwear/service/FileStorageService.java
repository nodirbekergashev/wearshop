package com.powerofwear.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.max-size-mb}")
    private long maxSizeInMb;

    @Value("${app.upload.allowed-types}")
    private Set<String> allowedTypes;

    private static final String BASE_URL = "http://localhost:8080/uploads/products/";

    @PostConstruct
    public void init() {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory!", e);
            }
        }
    }

    public String store(MultipartFile file, String subFolder) throws IOException {
        if (file.isEmpty()) throw new BadRequestException("File is empty");

        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new BadRequestException("File type not allowed: " + contentType);
        }

        if (file.getSize() > maxSizeInMb * 1024 * 1024) {
            throw new BadRequestException("File too large. Max size: " + maxSizeInMb + "MB");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + fileExtension;

        Path targetDir = Paths.get(uploadDir, subFolder);
        Files.createDirectories(targetDir);

        Path targetPath = targetDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return BASE_URL + subFolder + "/" + fileName;
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || !fileUrl.startsWith(BASE_URL)) return;

        try {
            String relativePath = fileUrl.substring(BASE_URL.length());
            Path filePath = Paths.get(uploadDir, relativePath);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            log.warn("Could not delete file: {}", fileUrl, e);
        }
    }
}