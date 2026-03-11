--------------------------------------------------------------------------------------------------------------------------
MySQL DB Codes
--------------------------------------------------------------------------------------------------------------------------

CREATE TABLE kullanici (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kullanici_adi VARCHAR(255) NOT NULL UNIQUE,
    sifre VARCHAR(255) NOT NULL,
    ad_soyad VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    CONSTRAINT chk_rol CHECK (rol IN ('ADMIN', 'USER')) )
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS ariza (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    baslik VARCHAR(255) NOT NULL,
    aciklama VARCHAR(2000) NOT NULL,
    durum VARCHAR(50) NOT NULL,
    olusturma_tarihi DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    guncelleme_tarihi DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    kullanici_id BIGINT NOT NULL,
    cevap TEXT NULL,
    CONSTRAINT fk_ariza_kullanici FOREIGN KEY (kullanici_id) 
        REFERENCES kullanici(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT chk_durum CHECK (durum IN ('YENI', 'ISLEMDE', 'COZULDU', 'REDDEDILDI')))
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
