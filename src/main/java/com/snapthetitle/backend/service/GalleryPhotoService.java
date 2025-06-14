// GalleryPhotoService.java
package com.snapthetitle.backend.service;

import com.snapthetitle.backend.dto.GalleryPhotoDto;
import com.snapthetitle.backend.entity.GalleryPhoto;
import com.snapthetitle.backend.repository.GalleryPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryPhotoService {
    private final GalleryPhotoRepository repo;

    public List<GalleryPhotoDto> getAll() {
        return repo.findByDeletedYn("N").stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public GalleryPhotoDto create(GalleryPhotoDto dto) {
        GalleryPhoto e = new GalleryPhoto();
        e.setSrc(dto.getSrc());
        // ...set other fields...
        GalleryPhoto saved = repo.save(e);
        return toDto(saved);
    }

    public GalleryPhotoDto update(Long id, GalleryPhotoDto dto) {
        GalleryPhoto e = repo.findById(id).orElseThrow();
        e.setSrc(dto.getSrc());
        // ...update other fields...
        return toDto(repo.save(e));
    }

    public void delete(Long id) {
        GalleryPhoto e = repo.findById(id).orElseThrow();
        e.setDeletedYn("Y");
        repo.save(e);
    }

    private GalleryPhotoDto toDto(GalleryPhoto e) {
        GalleryPhotoDto dto = new GalleryPhotoDto();
        dto.setId(e.getId());
        dto.setSrc(e.getSrc());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        dto.setDeletedYn(e.getDeletedYn());
        return dto;
    }
}
