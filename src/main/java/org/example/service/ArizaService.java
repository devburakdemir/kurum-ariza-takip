package org.example.service;

import org.example.entity.Ariza;
import java.util.List;

public interface ArizaService {
    Ariza findById(Long id);
    List<Ariza> findAll();
    List<Ariza> findByKullaniciId(Long kullaniciId);
    List<Ariza> findByDurum(String durum);
    void save(Ariza ariza);
    void update(Ariza ariza);
    void delete(Long id);
    // Tüm kayıtlar içinde başlığa göre filtreleme
    List<Ariza> findByBaslikContaining(String keyword);

    // Belirli kullanıcıya ait arızalar içinde başlığa göre filtreleme
    List<Ariza> findByKullaniciIdAndBaslikContaining(Long kullaniciId, String keyword);

}