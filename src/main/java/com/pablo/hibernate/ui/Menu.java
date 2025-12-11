package com.pablo.hibernate.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

    public void mostrar() {
        JFrame frame = new JFrame("Menú LOL - CRUD Hibernate");
        frame.setSize(400, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.setLocationRelativeTo(null);

        // Fondo NEGRO
        frame.getContentPane().setBackground(Color.BLACK);

        // Crear botones con estilo gamer ROJO
        JButton btnJugador = crearBoton("Gestionar Jugadores");
        JButton btnCampeon = crearBoton("Gestionar Campeones");
        JButton btnHabilidad = crearBoton("Gestionar Habilidades");
        JButton btnTipo = crearBoton("Gestionar Tipos");
        JButton btnPartida = crearBoton("Registrar / Ver Partidas");
        JButton btnSalir = crearBoton("Salir");

        // Añadir botones al frame
        frame.add(btnJugador);
        frame.add(btnCampeon);
        frame.add(btnHabilidad);
        frame.add(btnTipo);
        frame.add(btnPartida);
        frame.add(btnSalir);

        // ACCIONES
        btnSalir.addActionListener(e -> System.exit(0));
        btnJugador.addActionListener(e -> new VentanaJugadores().mostrar());
        btnCampeon.addActionListener(e -> new VentanaCampeones().mostrar());
        btnHabilidad.addActionListener(e -> new VentanaHabilidades().mostrar());
        btnTipo.addActionListener(e -> new VentanaTipos().mostrar());
        btnPartida.addActionListener(e -> new VentanaPartidas().mostrar());

        frame.setVisible(true);
    }

    // MÉTODO PARA CREAR BOTONES ROJOS GAMER
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        // Colores principales
        Color rojoOscuro = new Color(179, 0, 0);
        Color rojoHover = new Color(255, 51, 51);

        btn.setBackground(rojoOscuro);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Efecto hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(rojoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(rojoOscuro);
            }
        });

        return btn;
    }
}
