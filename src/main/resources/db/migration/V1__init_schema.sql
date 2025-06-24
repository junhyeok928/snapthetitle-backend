-- ============================================
-- Unified schema (모든 이전 마이그레이션 통합 + DELETED_YN 수정 반영)
-- ============================================

-- 1. ATTACHMENTS
CREATE TABLE IF NOT EXISTS ATTACHMENTS (
                                           ID             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                           ENTITY_TYPE    VARCHAR(50)      NOT NULL COMMENT '파일 소속 엔티티명 (예: "GALLERY", "PRODUCT", "GUIDE")',
    ENTITY_ID      BIGINT UNSIGNED  NOT NULL COMMENT '소속 레코드의 PK 값',
    FILE_URL       VARCHAR(512)     NOT NULL COMMENT '저장된 파일 URL 또는 경로',
    ORIGINAL_NAME  VARCHAR(255)     NOT NULL COMMENT '업로드 당시 원본 파일명',
    MIME_TYPE      VARCHAR(100)     NULL     COMMENT '파일 MIME 타입',
    IS_THUMBNAIL   BOOLEAN          NOT NULL COMMENT '썸네일 여부',
    CREATED_AT     DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT     DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN     VARCHAR(1)       NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y:삭제, N:활성)',
    PRIMARY KEY (ID),
    INDEX IDX_ATTACH_ENTITY (ENTITY_TYPE, ENTITY_ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. PRODUCTS
CREATE TABLE IF NOT EXISTS PRODUCTS (
                                        ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                        YEAR          INT              NOT NULL COMMENT '상품 연도 (예: 2025)',
                                        NAME          VARCHAR(100)     NOT NULL,
    DESCRIPTION   TEXT,
    PRICE         VARCHAR(50)      NOT NULL COMMENT '원 단위 문자열 (예: "400,000원")',
    IMAGE_URL     VARCHAR(512),
    DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '정렬 순서',
    CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN    VARCHAR(1)       NOT NULL DEFAULT 'N',
    PRIMARY KEY (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2-1. PRODUCT_OPTIONS
CREATE TABLE IF NOT EXISTS PRODUCT_OPTIONS (
                                               ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                               PRODUCT_ID    BIGINT UNSIGNED NOT NULL COMMENT 'PRODUCTS(ID)',
                                               LABEL         VARCHAR(50)      NOT NULL COMMENT '옵션 라벨 (예: 장소)',
    VALUE         VARCHAR(100)     NOT NULL COMMENT '옵션 값 (예: 1곳)',
    DISPLAY_ORDER INT              NOT NULL DEFAULT 0,
    CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN    VARCHAR(1)       NOT NULL DEFAULT 'N',
    PRIMARY KEY (ID),
    INDEX IDX_PO_PRODUCT (PRODUCT_ID),
    CONSTRAINT FK_PO_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS(ID) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. FAQS
CREATE TABLE IF NOT EXISTS FAQS (
                                    ID         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                    CATEGORY   VARCHAR(50)      NOT NULL COMMENT 'FAQ 카테고리',
    QUESTION   TEXT             NOT NULL COMMENT '질문 내용',
    ANSWER     TEXT             NOT NULL COMMENT '답변 내용',
    CREATED_AT DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN VARCHAR(1)       NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
    PRIMARY KEY (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. PARTNERS
CREATE TABLE IF NOT EXISTS PARTNERS (
                                        ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                        CATEGORY      VARCHAR(50)      NOT NULL COMMENT '제휴 카테고리 (예: 부케, 수트 등)',
    NAME          VARCHAR(100)     NOT NULL COMMENT '업체명',
    INSTAGRAM     VARCHAR(100)     NOT NULL COMMENT '인스타그램 핸들 (예: @ittageum)',
    LINK_URL      VARCHAR(255)     NOT NULL COMMENT '인스타그램 링크 URL',
    DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '프론트 배열 순서',
    CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN    VARCHAR(1)       NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y:삭제, N:활성)',
    PRIMARY KEY (ID),
    INDEX IDX_PARTNER_ORDER (DISPLAY_ORDER)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. GUIDES
CREATE TABLE IF NOT EXISTS GUIDES (
                                      ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                      CATEGORY      VARCHAR(50)      NOT NULL COMMENT '가이드 카테고리',
    CONTENT       TEXT             NOT NULL COMMENT '가이드 본문 내용',
    LINK_TEXT     VARCHAR(100)     COMMENT '링크 텍스트',
    LINK_URL      VARCHAR(255)     COMMENT '링크 URL',
    DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '정렬 순서',
    CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN    VARCHAR(1)       NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
    PRIMARY KEY (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5-1. GUIDE_DETAILS
CREATE TABLE IF NOT EXISTS GUIDE_DETAILS (
                                             ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                             GUIDE_ID      BIGINT UNSIGNED NOT NULL COMMENT 'GUIDES(ID)',
                                             SUBTITLE      VARCHAR(100)     NOT NULL COMMENT '세부 제목',
    DESCRIPTION   TEXT             NOT NULL COMMENT '세부 설명',
    DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '정렬 순서',
    CREATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN    VARCHAR(1)       NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
    PRIMARY KEY (ID),
    INDEX IDX_GD_GUIDE (GUIDE_ID),
    CONSTRAINT FK_GD_GUIDE FOREIGN KEY (GUIDE_ID) REFERENCES GUIDES(ID) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. GALLERY_PHOTOS (카테고리 컬럼 추가)
CREATE TABLE IF NOT EXISTS GALLERY_PHOTOS (
                                              ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                              CATEGORY      VARCHAR(50)       NOT NULL COMMENT '이미지 카테고리 (예: 바다, 들판, 스튜디오, 기타)',
    DISPLAY_ORDER INT              NOT NULL DEFAULT 0 COMMENT '정렬 순서',
    CREATED_AT    DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT    DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    DELETED_YN    VARCHAR(1)        NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
    PRIMARY KEY (ID)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci;

-- 7. ADMIN_USERS
CREATE TABLE IF NOT EXISTS admin_users (
                                           id         INT AUTO_INCREMENT PRIMARY KEY,
                                           username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    role       VARCHAR(20)  NOT NULL DEFAULT 'ADMIN',
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8. VISIT_LOGS
CREATE TABLE IF NOT EXISTS VISIT_LOGS (
                                          ID            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                          IP_ADDRESS    VARCHAR(45)     COMMENT '방문자 IP 주소 (IPv6까지 지원)',
    USER_AGENT    TEXT            COMMENT '브라우저/디바이스 User-Agent 문자열',
    REFERER       TEXT            COMMENT '이전 페이지 Referer (유입 경로)',
    URL           VARCHAR(255)    COMMENT '요청한 URL 경로',
    METHOD        VARCHAR(10)     COMMENT 'HTTP 메서드 (GET, POST 등)',
    STATUS_CODE   INT             COMMENT '응답 상태 코드 (예: 200, 404)',
    VISITED_AT    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '방문 시각',
    PRIMARY KEY (ID),
    INDEX IDX_VISIT_TIME (VISITED_AT),
    INDEX IDX_VISIT_URL (URL),
    INDEX IDX_VISIT_IP (IP_ADDRESS)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='방문자 로그 기록 테이블';

-- 9 MAIN_PHOTOS
CREATE TABLE IF NOT EXISTS MAIN_PHOTOS (
                                           ID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                           DISPLAY_ORDER INT NOT NULL DEFAULT 0 COMMENT '출력 순서',
                                           CREATED_AT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           UPDATED_AT DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           DELETED_YN VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y/N)',
    PRIMARY KEY (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='메인 이미지 테이블';
-- 초기 관리자 계정 (BCrypt 해시된 비밀번호)
INSERT INTO admin_users (username, password, role)
VALUES ('admin', '$2a$10$Dow1YzK1jGxDdZf0H3LhkuO/0wFz5s7Yb3F7zhS1XkJv9QnV2pO2W', 'ADMIN')
    ON DUPLICATE KEY UPDATE username=username;