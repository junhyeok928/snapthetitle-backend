// GalleryPhotoUserController.java
package com.snapthetitle.backend.controller.user;

import com.snapthetitle.backend.dto.GalleryPhotoDto;
import com.snapthetitle.backend.service.GalleryPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery-photos")
@RequiredArgsConstructor
public class GalleryPhotoUserController {
    private final GalleryPhotoService service;

    @GetMapping
    public List<GalleryPhotoDto> getAll() {
        return service.getAll();
    }
}
