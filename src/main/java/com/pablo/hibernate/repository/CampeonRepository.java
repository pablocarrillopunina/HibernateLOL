package com.pablo.hibernate.repository;

import com.pablo.hibernate.entity.Campeon;
import com.pablo.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class CampeonRepository {

    public void save(Campeon campeon) {
        Transaction tx = null;

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el campeón: sin conexión a la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tx = session.beginTransaction();
            session.persist(campeon);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Campeon> findAll() {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo obtener la lista de campeones: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }

        try {
            return session.createQuery("FROM Campeon", Campeon.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public Campeon findById(int id) {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo buscar el campeón: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            return session.find(Campeon.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void delete(Campeon campeon) {
        Transaction tx = null;

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo eliminar el campeón: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tx = session.beginTransaction();
            session.remove(campeon);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Campeon campeon) {
        Transaction tx = null;

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar el campeón: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tx = session.beginTransaction();
            session.merge(campeon);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
