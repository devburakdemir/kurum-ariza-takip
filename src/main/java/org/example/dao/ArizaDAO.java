package org.example.dao;

import org.example.entity.Ariza;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArizaDAO {
    Ariza findById(Long id);
    List<Ariza> findAll();
    List<Ariza> findByKullaniciId(Long kullaniciId);
    List<Ariza> findByDurum(String durum);
    void save(Ariza ariza);
    void update(Ariza ariza);
    void delete(Long id);
    List<Ariza> findByBaslikContaining(String keyword);
    List<Ariza> findByKullaniciIdAndBaslikContaining(Long kullaniciId, String keyword);

}