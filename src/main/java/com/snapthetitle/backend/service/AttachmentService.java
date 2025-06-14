// AttachmentService.java
package com.snapthetitle.backend.service;

import com.snapthetitle.backend.dto.AttachmentDto;
import com.snapthetitle.backend.entity.Attachment;
import com.snapthetitle.backend.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository repo;

    public List<AttachmentDto> getByEntity(String type, Long id) {
        return repo.findByEntityTypeAndEntityIdAndDeletedYn(type, id, "N")
                .stream()
                .map(e -> {
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
                })
                .collect(Collectors.toList());
    }
}
