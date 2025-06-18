// GalleryPhotoRepository.java
package com.snapthetitle.backend.repository;

import com.snapthetitle.backend.entity.GalleryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Long> {
    List<GalleryPhoto> findByDeletedYnOrderByDisplayOrderAscCreatedAtDesc(String deletedYn);

    @Query("SELECT COALESCE(MAX(g.displayOrder), 0) FROM GalleryPhoto g WHERE g.deletedYn = 'N'")
    Integer findMaxDisplayOrder();
}
