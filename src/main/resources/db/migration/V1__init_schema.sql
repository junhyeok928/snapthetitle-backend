CREATE TABLE ATTACHMENTS (
                             ID             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                             ENTITY_TYPE    VARCHAR(50)      NOT NULL COMMENT '파일 소속 엔티티명 (예: "GALLERY", "PRODUCT", "GUIDE")',
                             ENTITY_ID      BIGINT UNSIGNED  NOT NULL COMMENT '소속 레코드의 PK 값',
                             FILE_URL       VARCHAR(512)     NOT NULL COMMENT '저장된 파일 URL 또는 경로',
                             ORIGINAL_NAME  VARCHAR(255)     NOT NULL COMMENT '업로드 당시 원본 파일명',
                             MIME_TYPE      VARCHAR(100)     NULL     COMMENT '파일 MIME 타입',
                             CREATED_AT     DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             UPDATED_AT     DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             DELETED_YN     CHAR(1)          NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y:삭제, N:활성)',
                             PRIMARY KEY (ID),
                             INDEX IDX_ATTACH_ENTITY (ENTITY_TYPE, ENTITY_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. PRODUCTS
CREATE TABLE PRODUCTS (
                          ID           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                          YEAR         INT              NOT NULL COMMENT '상품 연도 (예: 2025)',
                          NAME         VARCHAR(100)     NOT NULL,
                          DESCRIPTION  TEXT,
                          PRICE        VARCHAR(50)      NOT NULL COMMENT '원 단위 문자열 (예: "400,000원")',
                          IMAGE_URL    VARCHAR(512),
                          CREATED_AT   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          UPDATED_AT   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          DELETED_YN   CHAR(1)          NOT NULL DEFAULT 'N',
                          PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2-1. PRODUCT_OPTIONS
CREATE TABLE PRODUCT_OPTIONS (
                                 ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                 PRODUCT_ID    BIGINT UNSIGNED NOT NULL COMMENT 'PRODUCTS(ID)',
                                 LABEL         VARCHAR(50)      NOT NULL COMMENT '옵션 라벨 (예: 장소)',
                                 VALUE         VARCHAR(100)     NOT NULL COMMENT '옵션 값 (예: 1곳)',
                                 DISPLAY_ORDER INT              NOT NULL DEFAULT 0,
                                 CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 DELETED_YN    CHAR(1)          NOT NULL DEFAULT 'N',
                                 PRIMARY KEY (ID),
                                 INDEX IDX_PO_PRODUCT (PRODUCT_ID),
                                 CONSTRAINT FK_PO_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS(ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. FAQS
CREATE TABLE FAQS (
                      ID          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                      CATEGORY    VARCHAR(50)      NOT NULL COMMENT 'FAQ 카테고리',
                      QUESTION    TEXT             NOT NULL COMMENT '질문 내용',
                      ANSWER      TEXT             NOT NULL COMMENT '답변 내용',
                      CREATED_AT  DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      UPDATED_AT  DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      DELETED_YN  CHAR(1)          NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
                      PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE PARTNERS (
                          ID               BIGINT UNSIGNED    NOT NULL AUTO_INCREMENT,
                          CATEGORY         VARCHAR(50)        NOT NULL COMMENT '제휴 카테고리 (예: 부케, 수트 등)',
                          NAME             VARCHAR(100)       NOT NULL COMMENT '업체명',
                          INSTAGRAM        VARCHAR(100)       NOT NULL COMMENT '인스타그램 핸들 (예: @ittageum)',
                          LINK_URL         VARCHAR(255)       NOT NULL COMMENT '인스타그램 링크 URL',
                          DISPLAY_ORDER    INT                NOT NULL DEFAULT 0 COMMENT '프론트 배열 순서',
                          CREATED_AT       DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          UPDATED_AT       DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          DELETED_YN       CHAR(1)            NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y:삭제, N:활성)',
                          PRIMARY KEY (ID),
                          INDEX IDX_PARTNER_ORDER (DISPLAY_ORDER)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;
-- 5. GUIDES (메인 가이드 항목)
CREATE TABLE GUIDES (
                        ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                        CATEGORY      VARCHAR(50)      NOT NULL COMMENT '가이드 카테고리',
                        CONTENT       TEXT             NOT NULL COMMENT '가이드 본문 내용',
                        LINK_TEXT     VARCHAR(100)     COMMENT '링크 텍스트',
                        LINK_URL      VARCHAR(255)     COMMENT '링크 URL',
                        DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '정렬 순서',
                        CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        DELETED_YN    CHAR(1)          NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
                        PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5-1. GUIDE_DETAILS (가이드 상세)
CREATE TABLE GUIDE_DETAILS (
                               ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                               GUIDE_ID      BIGINT UNSIGNED NOT NULL COMMENT 'GUIDES(ID)',
                               SUBTITLE      VARCHAR(100)     NOT NULL COMMENT '세부 제목',
                               DESCRIPTION   TEXT             NOT NULL COMMENT '세부 설명',
                               DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '정렬 순서',
                               CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               DELETED_YN    CHAR(1)          NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
                               PRIMARY KEY (ID),
                               INDEX IDX_GD_GUIDE (GUIDE_ID),
                               CONSTRAINT FK_GD_GUIDE FOREIGN KEY (GUIDE_ID) REFERENCES GUIDES(ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;