package org.example.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ariza")
public class Ariza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Column(name = "baslik", nullable = false)
    private String baslik;

    @Getter @Setter
    @Column(name = "aciklama", nullable = false, length = 2000)
    private String aciklama;

    @Getter @Setter
    @Column(name = "durum", nullable = false)
    private String durum; // YENI, ISLEMDE, COZULDU, REDDEDILDI

    @Getter @Setter
    @Column(name = "olusturma_tarihi", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date olusturmaTarihi;

    @Getter @Setter
    @Column(name = "guncelleme_tarihi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date guncellemeTarihi;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Getter @Setter
    @Column(name = "cevap")
    private String cevap;

    public Ariza() {
        this.olusturmaTarihi = new Date();
        this.durum = "YENI";
    }
}