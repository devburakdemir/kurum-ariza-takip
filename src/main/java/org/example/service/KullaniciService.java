package org.example.service;

import org.example.entity.Kullanici;
import java.util.List;

public interface KullaniciService {
    Kullanici authenticate(String kullaniciAdi, String sifre);
    Kullanici findById(Long id);
    List<Kullanici> findAll();
    void save(Kullanici kullanici);
    void update(Kullanici kullanici);
    void delete(Long id);
    void kullaniciAdiveMailKontrol(String kullaniciAdi, String email);
    void kaydet(Kullanici kullanici);
}
