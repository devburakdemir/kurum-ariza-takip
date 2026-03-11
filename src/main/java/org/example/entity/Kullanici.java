package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "kullanici")
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter @Setter
    @Column(name = "kullanici_adi", nullable = false, unique = true)
    private String kullaniciAdi;
    @Getter @Setter
    @Column(name = "sifre", nullable = false)
    private String sifre;
    @Getter @Setter
    @Column(name = "ad_soyad", nullable = false)
    private String adSoyad;
    @Getter @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Getter @Setter
    @Column(name = "rol", nullable = false)
    private String rol; // ADMIN veya USER
    @Getter @Setter
    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ariza> arizalar = new HashSet<>();

    // Constructors, getters and setters
    public Kullanici() {
    }

    public Kullanici(String kullaniciAdi, String sifre, String adSoyad, String email, String rol) {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.adSoyad = adSoyad;
        this.email = email;
        this.rol = rol;
    }

}