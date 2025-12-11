package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Partida;
import com.pablo.hibernate.service.PartidaService;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaPartidas {

    private final PartidaService service = new PartidaService();

    public void mostrar() {

        JFrame frame = new JFrame("Registrar / Ver Partidas");
        frame.setSize(600, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        // ============================
        // ÁREA DE TEXTO (ESTILO GAMER)
        // ============================
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(Color.DARK_GRAY);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        frame.add(scroll, BorderLayout.CENTER);


        // ======================
        // BOTONES (ABAJO)
        // ======================
        JButton btnCrear = crearBoton("Registrar partida");
        JButton btnRefrescar = crearBoton("Refrescar lista");

        JPanel panelBotones = new JPanel(new GridLayout(1, 2));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.add(btnCrear);
        panelBotones.add(btnRefrescar);

        frame.add(panelBotones, BorderLayout.SOUTH);


        // ======================
        // ACCIONES
        // ======================

        btnCrear.addActionListener(e -> {
            new VentanaRegistrarPartida().mostrar();
            cargarPartidas(area);
        });

        btnRefrescar.addActionListener(e -> cargarPartidas(area));

        // Cargar partidas al inicio
        cargarPartidas(area);

        frame.setVisible(true);
    }

    // ==========================================================
    // Método para dar estilo gamer a los botones
    // ==========================================================
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        Color rojo = new Color(179, 0, 0);
        Color rojoClaro = new Color(255, 51, 51);

        btn.setBackground(rojo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(rojoClaro);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(rojo);
            }
        });

        return btn;
    }

    // ==========================================================
    // MOSTRAR LAS PARTIDAS EN FORMATO BONITO
    // ==========================================================
    private void cargarPartidas(JTextArea area) {

        area.setText("");

        List<Partida> lista = service.listarPartidas();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder sb = new StringBuilder();

        for (Partida p : lista) {
            sb.append(String.format(
                    "[ID %d] %s\n" +
                            "Jugador:   %s\n" +
                            "Campeón:   %s\n" +
                            "Calle:     %s\n" +
                            "Resultado: %s\n" +
                            "KDA:       %d / %d\n" +
                            "----------------------------------------------\n\n",
                    p.getId(),
                    p.getFecha().format(fmt),
                    p.getJugador().getUsername(),
                    p.getCampeon().getNombre(),
                    p.getCalle(),
                    p.getResultado(),
                    p.getAsesinatos(),
                    p.getMuertes()
            ));
        }

        area.setText(sb.toString());
    }
}
