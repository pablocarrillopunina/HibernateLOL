package com.pablo.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory factory = config.buildSessionFactory();

// 🔥 MENSAJE DE ÉXITO (SOLO SE MUESTRA UNA VEZ)
            JOptionPane.showMessageDialog(
                    null,
                    "✔ Conexión exitosa con la base de datos.",
                    "Conexión establecida",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return factory;

        } catch (Exception e) {

            // 💥 Mensaje bonito y claro
            JOptionPane.showMessageDialog(
                    null,
                    "❌ Error conectando a la base de datos:\n\n" +
                            e.getMessage() +
                            "\n\nVerifica:\n" +
                            "• Que MySQL está iniciado\n" +
                            "• Que el puerto es correcto\n" +
                            "• Que el usuario/contraseña son válidos",
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE
            );

            return null; // evita que Hibernate reviente la app
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 3️⃣ ESTE ES EL NUEVO MÉTODO QUE USARÁS EN TODO EL PROYECTO
    public static Session getSession() {
        if (sessionFactory == null) return null;
        try {
            return sessionFactory.openSession();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "❌ No se pudo abrir una sesión con la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
}
