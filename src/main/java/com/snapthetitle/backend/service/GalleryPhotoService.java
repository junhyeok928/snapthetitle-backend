package com.snapthetitle.backend.service;

import com.snapthetitle.backend.dto.DisplayOrderDto;
import com.snapthetitle.backend.dto.GalleryPhotoDto;
import com.snapthetitle.backend.entity.GalleryPhoto;
import com.snapthetitle.backend.repository.GalleryPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GalleryPhotoService {
    private final GalleryPhotoRepository repo;
    private final AttachmentService attachmentService;

    public List<GalleryPhotoDto> getAll() {
        return repo.findByDeletedYnOrderByDisplayOrderAscCreatedAtDesc("N")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public GalleryPhotoDto createWithFiles(GalleryPhotoDto dto, MultipartFile[] files) throws IOException {
        GalleryPhotoDto created = createMetadata(dto);
        Long id = created.getId();
        for (MultipartFile file : files) {
            attachmentService.storeFileAndCreate("GALLERY_PHOTO", id, file);
        }
        return getById(id);
    }

    public GalleryPhotoDto updateWithFiles(Long id, GalleryPhotoDto dto, MultipartFile[] files) throws IOException {
        // 메타 업데이트
        GalleryPhotoDto updated = updateMetadata(id, dto);

        // 기존 첨부파일 모두 삭제
        attachmentService.deleteByEntity("GALLERY_PHOTO", id);

        // 새 파일 1개 저장 (files 배열의 첫 번째 파일)
        if (files != null && files.length > 0) {
            attachmentService.storeFileAndCreate("GALLERY_PHOTO", id, files[0]);
        }

        return getById(id);
    }


    private GalleryPhotoDto createMetadata(GalleryPhotoDto dto) {
        GalleryPhoto e = new GalleryPhoto();
        e.setCategory(dto.getCategory());
        LocalDateTime now = LocalDateTime.now();
        e.setCreatedAt(now);
        e.setUpdatedAt(now);
        e.setDeletedYn("N");

        // displayOrder 설정: 가장 큰 값 + 1
        Integer maxOrder = repo.findMaxDisplayOrder();
        e.setDisplayOrder((maxOrder == null ? 0 : maxOrder + 1));

        return toDto(repo.save(e));
    }

    private GalleryPhotoDto updateMetadata(Long id, GalleryPhotoDto dto) {
        GalleryPhoto e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GalleryPhoto not found: " + id));
        e.setCategory(dto.getCategory());
        e.setUpdatedAt(LocalDateTime.now());
        return toDto(repo.save(e));
    }

    public void delete(Long id) {
        GalleryPhoto e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GalleryPhoto not found: " + id));
        e.setDeletedYn("Y");
        e.setUpdatedAt(LocalDateTime.now());
        repo.save(e);
    }

    public void updateDisplayOrder(List<DisplayOrderDto> orderList) {
        for (DisplayOrderDto dto : orderList) {
            GalleryPhoto photo = repo.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("GalleryPhoto not found: " + dto.getId()));
            photo.setDisplayOrder(dto.getDisplayOrder());
            photo.setUpdatedAt(LocalDateTime.now());
            repo.save(photo);
        }
    }


    private GalleryPhotoDto getById(Long id) {
        GalleryPhoto e = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GalleryPhoto not found: " + id));
        return toDto(e);
    }

    private GalleryPhotoDto toDto(GalleryPhoto e) {
        GalleryPhotoDto dto = new GalleryPhotoDto();
        dto.setId(e.getId());
        dto.setCategory(e.getCategory());
        dto.setDisplayOrder(e.getDisplayOrder());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());
        dto.setDeletedYn(e.getDeletedYn());
        dto.setAttachments(attachmentService.getByEntity("GALLERY_PHOTO", e.getId()));
        return dto;
    }
}
