// GalleryPhotoAdminController.java
package com.snapthetitle.backend.controller.admin;

import com.snapthetitle.backend.dto.GalleryPhotoDto;
import com.snapthetitle.backend.service.GalleryPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/gallery-photos")
@RequiredArgsConstructor
public class GalleryPhotoAdminController {
    private final GalleryPhotoService service;

    @GetMapping
    public List<GalleryPhotoDto> list() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GalleryPhotoDto create(@RequestBody GalleryPhotoDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public GalleryPhotoDto update(@PathVariable Long id, @RequestBody GalleryPhotoDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
