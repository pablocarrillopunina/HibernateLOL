package com.pablo.hibernate.repository;

import com.pablo.hibernate.entity.Jugador;
import com.pablo.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class JugadorRepository {

    public void save(Jugador jugador) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar el jugador: sin conexión a la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(jugador);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al guardar jugador: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Jugador> findAll() {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("No se pudo listar jugadores: sin conexión.");
            return Collections.emptyList();
        }

        try {
            return session.createQuery("FROM Jugador", Jugador.class).list();
        } catch (Exception e) {
            System.err.println("Error al listar jugadores: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    public Jugador findById(int id) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("No se pudo buscar jugador: sin conexión.");
            return null;
        }

        try {
            return session.find(Jugador.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar jugador: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    public void delete(Jugador jugador) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("No se pudo eliminar jugador: sin conexión.");
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.remove(jugador);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al eliminar jugador: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void update(Jugador jugador) {

        Session session = HibernateUtil.getSession();
        if (session == null) {
            System.err.println("No se pudo actualizar jugador: sin conexión.");
            return;
        }

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(jugador);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al actualizar jugador: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
