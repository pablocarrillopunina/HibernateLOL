package com.pablo.hibernate.repository;

import com.pablo.hibernate.entity.Tipo;
import com.pablo.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class TipoRepository {

    public void save(Tipo tipo) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo guardar el tipo: sin conexión a la base de datos.");
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(tipo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Tipo> findAll() {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo listar tipos: sin conexión.");
            return Collections.emptyList();
        }

        try {
            return session.createQuery("FROM Tipo", Tipo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public Tipo findById(int id) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo buscar tipo: sin conexión.");
            return null;
        }

        try {
            return session.find(Tipo.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void delete(Tipo tipo) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo eliminar tipo: sin conexión.");
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.remove(tipo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Tipo tipo) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo actualizar tipo: sin conexión.");
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(tipo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
