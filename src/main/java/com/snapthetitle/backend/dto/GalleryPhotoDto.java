// GalleryPhotoDto.java
package com.snapthetitle.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GalleryPhotoDto {
    private Long id;
    private String src;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String deletedYn;  // 'Y' or 'N'
}
