package org.example;

import org.example.config.WebAppConfig;
import org.example.entity.Kullanici;
import org.example.service.KullaniciService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {TestConfig.class})
@ExtendWith(SpringExtension.class)
@Transactional
public class KullaniciServiceTest {

    @Autowired
    private KullaniciService kullaniciService;

    @Test
    public void testAuthenticate() {
        // Test verisi oluştur
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testuser");
        kullanici.setSifre("testpass");
        kullanici.setAdSoyad("Test User");
        kullanici.setEmail("test@example.com");
        kullanici.setRol("USER");

        // Kaydet
        kullaniciService.save(kullanici);

        // Doğrula
        Kullanici authenticated = kullaniciService.authenticate("testuser", "testpass");
        assertNotNull(authenticated);
        assertEquals("Test User", authenticated.getAdSoyad());
    }

    @Test
    public void testSaveAndFindKullanici() {
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testuser");
        kullanici.setSifre("testpass");
        kullanici.setAdSoyad("Test User");
        kullanici.setEmail("test@example.com");
        kullanici.setRol("USER");

        kullaniciService.save(kullanici);

        Kullanici found = kullaniciService.findById(kullanici.getId());
        assertNotNull(found);
        assertEquals("testuser", found.getKullaniciAdi());
    }

    @Test
    public void testUpdateKullanici() {
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testuser");
        kullanici.setSifre("testpass");
        kullanici.setAdSoyad("Test User");
        kullanici.setEmail("test@example.com");
        kullanici.setRol("USER");
        kullaniciService.save(kullanici);

        kullanici.setAdSoyad("Updated User");
        kullaniciService.update(kullanici);

        Kullanici updated = kullaniciService.findById(kullanici.getId());
        assertEquals("Updated User", updated.getAdSoyad());
    }

    @Test
    public void testDeleteKullanici() {
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testuser");
        kullanici.setSifre("testpass");
        kullanici.setAdSoyad("Test User");
        kullanici.setEmail("test@example.com");
        kullanici.setRol("USER");
        kullaniciService.save(kullanici);

        Long id = kullanici.getId();
        kullaniciService.delete(id);

        Kullanici deleted = kullaniciService.findById(id);
        assertNull(deleted);
    }
}