package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.entity.Ariza;
import org.example.entity.Kullanici;
import org.example.service.ArizaService;
import org.example.service.KullaniciService;
import org.example.util.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@Qualifier("arizaServiceImpl")
@RequestMapping("/ariza")
public class ArizaController {

    private static final Logger logger = LoggerFactory.getLogger(ArizaController.class);

    @Autowired
    private ArizaService arizaService;

    @Autowired
    private KullaniciService kullaniciService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @GetMapping("/liste")
    public String listArizalar(@RequestParam(value = "arama", required = false) String arama,
                               HttpSession session, Model model, Locale locale) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";

        List<Ariza> arizalar;
        if ("ADMIN".equalsIgnoreCase(kullanici.getRol())) {
            arizalar = (arama == null || arama.isEmpty())
                    ? arizaService.findAll()
                    : arizaService.findByBaslikContaining(arama);
            model.addAttribute("adminPanel", true); // JSP'de kullanılabilir
            model.addAttribute("arizalar", arizalar);
            model.addAttribute("oturumdakiKullanici", kullanici);
            model.addAttribute("arama", arama); // filtre alanı yeniden doldurulsun
            return "admin-ariza-list"; // Admin özel sayfası
        } else {
            arizalar = arama == null || arama.isEmpty()
                    ? arizaService.findByKullaniciId(kullanici.getId())
                    : arizaService.findByKullaniciIdAndBaslikContaining(kullanici.getId(), arama);
            model.addAttribute("arizalar", arizalar);
            model.addAttribute("oturumdakiKullanici", kullanici);
            model.addAttribute("arama", arama);
            return "ariza-list";
        }
    }


    @GetMapping("/yeni")
    public String showArizaForm(Model model, HttpSession session) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";

        model.addAttribute("ariza", new Ariza());
        return "ariza-form";
    }

    @PostMapping("/kaydet")
    public String saveAriza(@ModelAttribute Ariza ariza,
                            HttpSession session,
                            RedirectAttributes redirectAttributes,
                            Locale locale) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";

        ariza.setKullanici(kullanici);
        ariza.setOlusturmaTarihi(new Date());
        ariza.setDurum("YENI");

        arizaService.save(ariza);
        logger.info("New fault created by user {} with title: {}", kullanici.getKullaniciAdi(), ariza.getBaslik());

        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("fault.add.success", null, locale));
        return "redirect:/ariza/liste";
    }

    @GetMapping("/duzenle/{id}")
    public String editAriza(@PathVariable("id") Long id, Model model, HttpSession session) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";

        Ariza ariza = arizaService.findById(id);
        if (ariza == null || (!"ADMIN".equalsIgnoreCase(kullanici.getRol()) &&
                !ariza.getKullanici().getId().equals(kullanici.getId()))) {
            return "redirect:/ariza/liste";
        }

        model.addAttribute("ariza", ariza);
        if ("ADMIN".equalsIgnoreCase(kullanici.getRol())) {
            model.addAttribute("isAdmin", true);
        }

        logger.info("Editing fault with id: {} by user: {}", id, kullanici.getKullaniciAdi());
        return "ariza-form";
    }

    @PostMapping("/guncelle")
    public String updateAriza(@ModelAttribute Ariza ariza,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {

        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";

        Ariza existingAriza = arizaService.findById(ariza.getId());
        if (existingAriza == null ||
                (!"ADMIN".equalsIgnoreCase(kullanici.getRol()) &&
                        !existingAriza.getKullanici().getId().equals(kullanici.getId()))){
            return "redirect:/ariza/liste";
        }

        // Burada kullanıcıyı yeniden setle (Null check sonrasında)
        ariza.setKullanici(existingAriza.getKullanici());

        existingAriza.setBaslik(ariza.getBaslik());
        existingAriza.setAciklama(ariza.getAciklama());
        existingAriza.setDurum(ariza.getDurum());
        existingAriza.setCevap(ariza.getCevap());

        arizaService.update(existingAriza);

        redirectAttributes.addFlashAttribute("success",
                messageSource.getMessage("fault.update.success", null, locale));

        return "redirect:/ariza/liste";
    }


    @GetMapping("/sil/{id}")
    public String deleteAriza(@PathVariable("id") Long id,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";

        Ariza ariza = arizaService.findById(id);
        if (ariza != null && ("ADMIN".equalsIgnoreCase(kullanici.getRol()) ||
                ariza.getKullanici().getId().equals(kullanici.getId()))) {
            arizaService.delete(id);
            logger.info("Fault with id: {} deleted by user: {}", id, kullanici.getKullaniciAdi());
            redirectAttributes.addFlashAttribute("success",
                    messageSource.getMessage("fault.delete.success", null, locale));
        }

        return "redirect:/ariza/liste";
    }

    @GetMapping("/guncelle/{id}")
    public String adminArizaGuncelleForm(@PathVariable("id") Long id,
                                         HttpSession session,
                                         Model model) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null || !"ADMIN".equalsIgnoreCase(kullanici.getRol())) {
            return "redirect:/login";
        }

        Ariza ariza = arizaService.findById(id);
        if (ariza == null) {
            return "redirect:/ariza/liste";
        }

        model.addAttribute("ariza", ariza);
        return "admin-ariza-guncelle";
    }
}
