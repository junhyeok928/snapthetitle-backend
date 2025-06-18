package com.snapthetitle.backend.service;

import com.snapthetitle.backend.dto.AttachmentDto;
import com.snapthetitle.backend.entity.Attachment;
import com.snapthetitle.backend.repository.AttachmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository repo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<AttachmentDto> getByEntity(String type, Long id) {
        return repo.findByEntityTypeAndEntityIdAndDeletedYn(type, id, "N")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AttachmentDto create(String entityType, Long entityId, String fileUrl, String originalName, String mimeType) {
        Attachment e = new Attachment();
        e.setEntityType(entityType);
        e.setEntityId(entityId);
        e.setFileUrl(fileUrl);
        e.setOriginalName(originalName);
        e.setMimeType(mimeType);
        e.setDeletedYn("N");
        LocalDateTime now = LocalDateTime.now();
        e.setCreatedAt(now);
        e.setUpdatedAt(now);

        return toDto(repo.save(e));
    }

    public void delete(Long id) {
        Attachment e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attachment not found: " + id));
        e.setDeletedYn("Y");
        e.setUpdatedAt(LocalDateTime.now());
        repo.save(e);
    }

    public String storeFileToDisk(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String uniqueName = System.currentTimeMillis() + "_" + originalName;
        Path savePath = Paths.get(uploadDir, uniqueName);
        Files.createDirectories(savePath.getParent());
        file.transferTo(savePath.toFile());
        return "/uploads/" + uniqueName;
    }

    public AttachmentDto storeFileAndCreate(String entityType, Long entityId, MultipartFile file) throws IOException {
        String url = storeFileToDisk(file);
        return create(entityType, entityId, url, file.getOriginalFilename(), file.getContentType());
    }

    private AttachmentDto toDto(Attachment e) {
        AttachmentDto dto = new AttachmentDto();
        dto.setId(e.getId());
        dto.setEntityType(e.getEntityType());
        dto.setEntityId(e.getEntityId());
        dto.setFileUrl(e.getFileUrl());
        dto.setOriginalName(e.getOriginalName());
        dto.setMimeType(e.getMimeType());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        dto.setDeletedYn(e.getDeletedYn());
        return dto;
    }

    @Transactional
    public void deleteByEntity(String entityType, Long entityId) {
        List<Attachment> attachments = repo.findByEntityTypeAndEntityIdAndDeletedYn(entityType, entityId, "N");
        LocalDateTime now = LocalDateTime.now();

        for (Attachment att : attachments) {
            att.setDeletedYn("Y");
            att.setUpdatedAt(now);
        }
        repo.saveAll(attachments);
    }
}
