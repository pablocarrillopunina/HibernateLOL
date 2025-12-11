package com.pablo.hibernate;

import com.pablo.hibernate.entity.*;
import com.pablo.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;

/**
 * Clase principal que sirve como punto de entrada para la aplicación de demostración.
 * <p>
 * Su propósito es ejecutar una serie de operaciones de base de datos utilizando Hibernate
 * para ilustrar la creación, recuperación y actualización de entidades del modelo LOL.
 */
public class Main {

    /**
     * Orquesta una transacción de Hibernate para poblar o actualizar la base de datos.
     * <p>
     * El proceso sigue estos pasos:
     * <ol>
     *   <li>Busca o crea un {@link Jugador}.</li>
     *   <li>Busca o crea un {@link Tipo} de campeón.</li>
     *   <li>Busca o crea un conjunto de {@link Habilidad}.</li>
     *   <li>Busca o crea un {@link Campeon}, asociándolo con el tipo y habilidad anteriores.</li>
     *   <li>Registra una nueva {@link Partida} para el jugador y campeón.</li>
     *   <li>Modifica el tipo del campeón a uno nuevo (o existente) y actualiza la entidad.</li>
     * </ol>
     * Todas las operaciones se ejecutan dentro de una única transacción. Si ocurre cualquier
     * error, la transacción se revierte para mantener la consistencia de los datos.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        Transaction tx = null;
        // Abre una nueva sesión de Hibernate. Se usa try-with-resources para asegurar su cierre.
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // ========================================================
            //  BUSCAR O CREAR JUGADOR
            // ========================================================
            Jugador jugador = session.createQuery(
                            "FROM Jugador WHERE username = :u", Jugador.class)
                    .setParameter("u", "PabloCarrillo")
                    .uniqueResult();

            if (jugador == null) {
                jugador = new Jugador();
                jugador.setUsername("PabloCarrillo");
                jugador.setPassword("1234");
                session.persist(jugador);
                System.out.println("Jugador creado.");
            } else {
                System.out.println("Jugador reutilizado.");
            }


            // ========================================================
            // 2️⃣ BUSCAR O CREAR TIPO PRINCIPAL
            // ========================================================
            Tipo tipo = session.createQuery(
                            "FROM Tipo WHERE tipo = :t", Tipo.class)
                    .setParameter("t", "Luchador")
                    .uniqueResult();

            if (tipo == null) {
                tipo = new Tipo("Luchador");
                session.persist(tipo);
                System.out.println("Tipo creado.");
            } else {
                System.out.println("Tipo reutilizado.");
            }


            // ========================================================
            // 3️⃣ BUSCAR O CREAR HABILIDAD
            // ========================================================
            Habilidad habilidad = session.createQuery(
                            "FROM Habilidad WHERE q = :q", Habilidad.class)
                    .setParameter("q", "Golpe Rápido")
                    .uniqueResult();

            if (habilidad == null) {
                habilidad = new Habilidad();
                habilidad.setQ("Golpe Rápido");
                habilidad.setW("Defensa Férrea");
                habilidad.setE("Embate Brutal");
                habilidad.setR("Furia Guerrero");
                session.persist(habilidad);
                System.out.println("Habilidad creada.");
            } else {
                System.out.println("Habilidad reutilizada.");
            }


            // ========================================================
            // 4️⃣ BUSCAR O CREAR CAMPEÓN
            // ========================================================
            Campeon campeon = session.createQuery(
                            "FROM Campeon WHERE nombre = :n", Campeon.class)
                    .setParameter("n", "AXEL")
                    .uniqueResult();

            if (campeon == null) {
                campeon = new Campeon();
                campeon.setNombre("AXEL");
                campeon.setTipo(tipo);
                campeon.setHabilidad(habilidad);
                campeon.setVida(600);
                campeon.setEnergia(350);
                campeon.setdFisico(70);
                campeon.setdMagico(20);
                session.persist(campeon);
                System.out.println("Campeón creado.");
            } else {
                System.out.println("Campeón reutilizado.");
            }


            // ========================================================
            // 5️⃣ INSERTAR PARTIDA (SIEMPRE CREA UNA NUEVA)
            // ========================================================
            Partida partida = new Partida();
            partida.setFecha(LocalDateTime.now());
            partida.setJugador(jugador);
            partida.setCampeon(campeon);
            partida.setCalle("MID");
            partida.setResultado("VICTORIA");
            partida.setAsesinatos(5);
            partida.setMuertes(2);
            session.persist(partida);

            System.out.println("Partida creada.");


            // ========================================================
            // 6️⃣ CAMBIAR TIPO DEL CAMPEÓN — SOLO UNA VEZ
            // ========================================================
            Tipo nuevoTipo = session.createQuery(
                            "FROM Tipo WHERE tipo = :t", Tipo.class)
                    .setParameter("t", "Asesino")
                    .uniqueResult();

            if (nuevoTipo == null) {
                nuevoTipo = new Tipo("Asesino");
                session.persist(nuevoTipo);
                System.out.println("Nuevo tipo creado.");
            }

            // Si el campeón no era ya de tipo Asesino, se actualiza
            if (!campeon.getTipo().getTipo().equals("Asesino")) {
                campeon.setTipo(nuevoTipo);
                session.merge(campeon);
                System.out.println("Tipo del campeón actualizado a Asesino.");
            }


            // ========================================================
            // COMMIT
            // ========================================================
            tx.commit();
            System.out.println("\n✓ Operaciones completadas correctamente.");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
