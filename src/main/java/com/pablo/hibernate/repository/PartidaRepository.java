package com.pablo.hibernate.repository;

import com.pablo.hibernate.entity.Partida;
import com.pablo.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class PartidaRepository {

    public void save(Partida partida) {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo guardar la partida: sin conexión a la base de datos.");
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(partida);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("❌ Error guardando partida: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Partida> findAll() {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo listar partidas: sin conexión.");
            return Collections.emptyList();
        }

        try {
            return session.createQuery(
                    "FROM Partida ORDER BY fecha DESC", Partida.class
            ).list();
        } catch (Exception e) {
            System.err.println("❌ Error listando partidas: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public Partida findById(int id) {
        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("❌ No se pudo buscar partida: sin conexión.");
            return null;
        }

        try {
            return session.find(Partida.class, id);
        } catch (Exception e) {
            System.err.println("❌ Error buscando partida: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
}
