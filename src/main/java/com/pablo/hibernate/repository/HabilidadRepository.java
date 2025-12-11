package com.pablo.hibernate.repository;

import com.pablo.hibernate.entity.Habilidad;
import com.pablo.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class HabilidadRepository {

    public void save(Habilidad habilidad) {
        Transaction tx = null;

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar la habilidad: sin conexión a la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tx = session.beginTransaction();
            session.persist(habilidad);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Habilidad> findAll() {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo obtener la lista de habilidades: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }

        try {
            return session.createQuery("FROM Habilidad", Habilidad.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public Habilidad findById(int id) {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo buscar la habilidad: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            return session.find(Habilidad.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void delete(Habilidad habilidad) {
        Transaction tx = null;

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo eliminar la habilidad: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tx = session.beginTransaction();
            session.remove(habilidad);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void update(Habilidad habilidad) {
        Transaction tx = null;

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar la habilidad: sin conexión.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tx = session.beginTransaction();
            session.merge(habilidad);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
