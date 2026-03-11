package org.example.service;

import org.example.dao.ArizaDAO;
import org.example.dao.KullaniciDAO;
import org.example.entity.Ariza;
import org.example.entity.Kullanici;
import org.example.util.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@Transactional
public class ArizaServiceImpl implements ArizaService {

    private static final Logger logger = LoggerFactory.getLogger(ArizaServiceImpl.class);

    @Autowired
    private ArizaDAO arizaDAO;

    @Autowired
    private MailService mailService;

    @Autowired
    private KullaniciService kullaniciService;

    @Override
    public Ariza findById(Long id) {
        logger.info("Ariza findById called with id: {}", id);
        return arizaDAO.findById(id);
    }

    @Override
    public List<Ariza> findAll() {
        logger.info("Ariza findAll called");
        return arizaDAO.findAll();
    }

    @Override
    public List<Ariza> findByKullaniciId(Long kullaniciId) {
        logger.info("Ariza findByKullaniciId called with kullaniciId: {}", kullaniciId);
        return arizaDAO.findByKullaniciId(kullaniciId);
    }

    @Override
    public List<Ariza> findByDurum(String durum) {
        logger.info("Ariza findByDurum called with durum: {}", durum);
        return arizaDAO.findByDurum(durum);
    }

    @Override
    public void save(Ariza ariza) {
        logger.info("Saving new Ariza with title: {}", ariza.getBaslik());
        arizaDAO.save(ariza);

        // Send email notification
        String subject = "Yeni Arıza Bildirimi";
        String message = String.format("Sayın %s,\n\n'%s' başlıklı arıza bildiriminiz alınmıştır.\n\nDetaylar:\n%s",
                ariza.getKullanici().getAdSoyad(), ariza.getBaslik(), ariza.getAciklama());

        mailService.sendEmail(ariza.getKullanici().getEmail(), subject, message);
    }

    @Override
    public void update(Ariza ariza) {
        logger.info("Updating Ariza with id: {}", ariza.getId());

        Ariza existingAriza = arizaDAO.findById(ariza.getId());
        if (existingAriza != null) {
            String oldStatus = existingAriza.getDurum();

            // Eksik alanlar varsa eski nesneden tamamla
            if (ariza.getKullanici() == null) {
                ariza.setKullanici(existingAriza.getKullanici());
            }
            if (ariza.getBaslik() == null) {
                ariza.setBaslik(existingAriza.getBaslik());
            }
            if (ariza.getAciklama() == null) {
                ariza.setAciklama(existingAriza.getAciklama());
            }

            // ⛔️ LazyInitialization hatasını önle!
            Kullanici kullanici = ariza.getKullanici();
            if (kullanici != null && kullanici.getId() != null) {
                kullanici = kullaniciService.findById(kullanici.getId()); // tam yükle
                ariza.setKullanici(kullanici); // proxy yerine gerçek nesne
            }

            arizaDAO.update(ariza);

            if (!oldStatus.equals(ariza.getDurum())) {
                String subject = "Arıza Durum Güncellemesi";
                String message = String.format("Sayın %s,\n\n'%s' başlıklı arıza bildiriminizin durumu '%s' olarak güncellenmiştir.\n\n",
                        ariza.getKullanici().getAdSoyad(), ariza.getBaslik(), ariza.getDurum());

                if (ariza.getCevap() != null && !ariza.getCevap().isEmpty()) {
                    message += "Yönetici Cevabı:\n" + ariza.getCevap() + "\n\n";
                }

                mailService.sendEmail(ariza.getKullanici().getEmail(), subject, message);
            }
        }
    }


    @Override
    public void delete(Long id) {
        logger.info("Deleting Ariza with id: {}", id);
        arizaDAO.delete(id);
    }
    @Override
    public List<Ariza> findByBaslikContaining(String keyword) {
        logger.info("Ariza findByBaslikContaining called with keyword: {}", keyword);
        return arizaDAO.findByBaslikContaining(keyword);
    }

    @Override
    public List<Ariza> findByKullaniciIdAndBaslikContaining(Long kullaniciId, String keyword) {
        logger.info("Ariza findByKullaniciIdAndBaslikContaining called with kullaniciId: {}, keyword: {}", kullaniciId, keyword);
        return arizaDAO.findByKullaniciIdAndBaslikContaining(kullaniciId, keyword);
    }

}