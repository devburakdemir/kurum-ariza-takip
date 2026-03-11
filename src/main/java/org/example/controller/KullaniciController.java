package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.entity.Kullanici;
import org.example.service.KullaniciService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class KullaniciController {

    private static final Logger logger = LoggerFactory.getLogger(KullaniciController.class);

    @Autowired
    private KullaniciService kullaniciService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/kayit")
    public String showRegistrationForm(Model model) {
        model.addAttribute("kullanici", new Kullanici());
        return "kayit_islemi";
    }

    @PostMapping("/kayit")
    public String kayit(@ModelAttribute("kullanici") Kullanici kullanici,
                        @RequestParam("sifreTekrar") String sifreTekrar,
                        Model model) {

        if (!kullanici.getSifre().equals(sifreTekrar)) {
            model.addAttribute("error", "Şifreler eşleşmiyor");
            return "kayit_islemi";
        }

        try {
            kullanici.setRol("USER");
            kullaniciService.kaydet(kullanici);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "kayit_islemi";
        }

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("kullaniciAdi") String kullaniciAdi,
                        @RequestParam("sifre") String sifre,
                        @RequestParam("rol") String rol,
                        HttpSession session,
                        Model model) {

        Kullanici kullanici = kullaniciService.authenticate(kullaniciAdi, sifre);

        if (kullanici != null && kullanici.getRol().equalsIgnoreCase(rol)) {
            session.setAttribute("oturumdakiKullanici", kullanici);

            if ("ADMIN".equalsIgnoreCase(rol)) {
                return "redirect:/admin/anasayfa";
            } else {
                return "redirect:/anasayfa";
            }
        } else {
            model.addAttribute("error", "Giriş bilgileri hatalı veya rol uyuşmuyor.");
            return "login";
        }
    }

    @GetMapping("/admin/anasayfa")
    public String adminAnasayfa(HttpSession session, Model model) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null || !"ADMIN".equalsIgnoreCase(kullanici.getRol())) {
            return "redirect:/login";
        }
        model.addAttribute("oturumdakiKullanici", kullanici);
        return "admin-anasayfa";
    }

    @GetMapping("/anasayfa")
    public String anasayfa(HttpSession session, Model model) {
        Kullanici kullanici = (Kullanici) session.getAttribute("oturumdakiKullanici");
        if (kullanici == null) return "redirect:/login";
        model.addAttribute("oturumdakiKullanici", kullanici);
        return "anasayfa";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
