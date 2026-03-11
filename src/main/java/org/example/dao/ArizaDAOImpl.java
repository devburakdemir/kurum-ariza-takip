package org.example.dao;

import org.example.entity.Ariza;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArizaDAOImpl implements ArizaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Ariza findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Ariza.class, id);
    }

    @Override
    public List<Ariza> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Ariza> query = session.createQuery("from Ariza order by olusturmaTarihi desc", Ariza.class);
        return query.getResultList();
    }

    @Override
    public List<Ariza> findByKullaniciId(Long kullaniciId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ariza> query = session.createQuery("from Ariza where kullanici.id = :kullaniciId order by olusturmaTarihi desc", Ariza.class);
        query.setParameter("kullaniciId", kullaniciId);
        return query.getResultList();
    }

    @Override
    public List<Ariza> findByDurum(String durum) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ariza> query = session.createQuery("from Ariza where durum = :durum order by olusturmaTarihi desc", Ariza.class);
        query.setParameter("durum", durum);
        return query.getResultList();
    }

    @Override
    public void save(Ariza ariza) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(ariza);
    }

    @Override
    public void update(Ariza ariza) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(ariza);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Ariza ariza = session.get(Ariza.class, id);
        if (ariza != null) {
            session.remove(ariza);
        }
    }
    @Override
    public List<Ariza> findByBaslikContaining(String keyword) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ariza> query = session.createQuery(
                "from Ariza where lower(baslik) like :keyword order by olusturmaTarihi desc", Ariza.class);
        query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Ariza> findByKullaniciIdAndBaslikContaining(Long kullaniciId, String keyword) {
        Session session = sessionFactory.getCurrentSession();
        Query<Ariza> query = session.createQuery(
                "from Ariza where kullanici.id = :kullaniciId and lower(baslik) like :keyword order by olusturmaTarihi desc",
                Ariza.class);
        query.setParameter("kullaniciId", kullaniciId);
        query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

}