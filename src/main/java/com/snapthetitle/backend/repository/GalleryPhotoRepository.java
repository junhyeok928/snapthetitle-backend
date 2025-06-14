// GalleryPhotoRepository.java
package com.snapthetitle.backend.repository;

import com.snapthetitle.backend.entity.GalleryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Long> {
    List<GalleryPhoto> findByDeletedYn(String deletedYn);
}
