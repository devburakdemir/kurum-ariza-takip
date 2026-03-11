package org.example.dao;

import org.example.entity.Kullanici;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KullaniciDAO {
    Kullanici findByKullaniciAdi(String kullaniciAdi);
    Kullanici findById(Long id);
    List<Kullanici> findAll();
    void save(Kullanici kullanici);
    void update(Kullanici kullanici);
    void delete(Long id);
}