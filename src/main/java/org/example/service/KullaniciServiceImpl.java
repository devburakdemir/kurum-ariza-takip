package org.example.service;

import org.example.dao.KullaniciDAO;
import org.example.entity.Kullanici;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@Transactional
public class KullaniciServiceImpl implements KullaniciService {

    @Autowired
    private KullaniciDAO kullaniciDAO;

    @Override
    public Kullanici authenticate(String kullaniciAdi, String sifre) {
        Kullanici kullanici = kullaniciDAO.findByKullaniciAdi(kullaniciAdi);
        if (kullanici != null) {
            System.out.println(">>> DB kullanıcı adı: " + kullanici.getKullaniciAdi());
            System.out.println(">>> DB şifre: [" + kullanici.getSifre() + "]");
            System.out.println(">>> Giriş şifresi: [" + sifre + "]");
            System.out.println(">>> Eşleşme sonucu: " + kullanici.getSifre().equals(sifre));
            System.out.println(">>> Gelen kullanıcı adı: [" + kullaniciAdi + "]");

        } else {
            System.out.println(">>> Kullanıcı bulunamadı.");
        }

        if (kullanici != null && kullanici.getSifre().equals(sifre)) {
            return kullanici;
        }
        return null;
    }

    @Override
    public void kullaniciAdiveMailKontrol(String kullaniciAdi, String email) {
        Kullanici kullaniciAdiKontrol = kullaniciDAO.findByKullaniciAdi(kullaniciAdi);
        if (kullaniciAdiKontrol != null) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten kullanımda.");
        }

        List<Kullanici> tumKullanicilar = kullaniciDAO.findAll();
        for (Kullanici k : tumKullanicilar) {
            if (k.getEmail().equals(email)) {
                throw new IllegalArgumentException("Bu e-posta adresi zaten kullanımda.");
            }
        }
    }

    @Override
    public void kaydet(Kullanici kullanici) {
        kullaniciAdiveMailKontrol(kullanici.getKullaniciAdi(), kullanici.getEmail());
        kullaniciDAO.save(kullanici);
    }


    @Override
    public Kullanici findById(Long id) {
        return kullaniciDAO.findById(id);
    }

    @Override
    public List<Kullanici> findAll() {
        return kullaniciDAO.findAll();
    }

    @Override
    public void save(Kullanici kullanici) {
        kullaniciDAO.save(kullanici);
    }

    @Override
    public void update(Kullanici kullanici) {
        kullaniciDAO.update(kullanici);
    }

    @Override
    public void delete(Long id) {
        kullaniciDAO.delete(id);
    }
}