CREATE TABLE GALLERY_PHOTOS (
                                ID           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                SRC          VARCHAR(512)       NOT NULL COMMENT '이미지 URL 또는 경로',
                                CREATED_AT   DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                UPDATED_AT   DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                DELETED_YN   VARCHAR(1)         NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
                                PRIMARY KEY (ID)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;