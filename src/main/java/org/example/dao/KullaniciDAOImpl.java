package org.example.dao;

import org.example.entity.Kullanici;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KullaniciDAOImpl implements KullaniciDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Kullanici findByKullaniciAdi(String kullaniciAdi) {
        Session session = sessionFactory.getCurrentSession();
        Query<Kullanici> query = session.createQuery("from Kullanici where kullaniciAdi = :kullaniciAdi", Kullanici.class);
        query.setParameter("kullaniciAdi", kullaniciAdi);
        return query.uniqueResult();
    }

    @Override
    public Kullanici findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Kullanici.class, id);
    }

    @Override
    public List<Kullanici> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Kullanici> query = session.createQuery("from Kullanici", Kullanici.class);
        return query.getResultList();
    }

    @Override
    public void save(Kullanici kullanici) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(kullanici);
    }

    @Override
    public void update(Kullanici kullanici) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(kullanici);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Kullanici kullanici = session.get(Kullanici.class, id);
        if (kullanici != null) {
            session.remove(kullanici);
        }
    }
}