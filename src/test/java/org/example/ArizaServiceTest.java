package org.example;

import org.example.config.WebAppConfig;
import org.example.entity.Ariza;
import org.example.entity.Kullanici;
import org.example.service.ArizaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebAppConfig.class)
@Transactional
public class ArizaServiceTest {

    @Autowired
    private ArizaService arizaService;

    private Kullanici testKullanici;

    @BeforeEach
    public void setup() {
        testKullanici = new Kullanici();
        testKullanici.setKullaniciAdi("testuser");
        testKullanici.setSifre("testpass");
        testKullanici.setAdSoyad("Test User");
        testKullanici.setEmail("test@example.com");
        testKullanici.setRol("USER");
    }

    @Test
    public void testSaveAndFindAriza() {
        Ariza ariza = new Ariza();
        ariza.setBaslik("Test Arıza");
        ariza.setAciklama("Test açıklama");
        ariza.setKullanici(testKullanici);
        ariza.setOlusturmaTarihi(new Date());

        arizaService.save(ariza);

        Ariza found = arizaService.findById(ariza.getId());
        assertNotNull(found);
        assertEquals("Test Arıza", found.getBaslik());
    }

    // Diğer test metodları...
}