package com.tokioshool.filmotokio.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tokioshool.filmotokio.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${images.upload.dir}")
    private String uploadDir;

    @Override
    public void uploadFile(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Path targetLocation = uploadPath.resolve(fileName);

                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                logger.info("Arquivo salvo em: " + targetLocation.toString());
            }
        } catch (Exception e) {
            logger.error("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}
