package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Partida;
import com.pablo.hibernate.service.PartidaService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaPartidas {

    private final PartidaService service = new PartidaService();

    public void mostrar() {

        JFrame frame = new JFrame("Registrar / Ver Partidas");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        // ======================
        // TABLA DE PARTIDAS
        // ======================
        String[] columnas = {
                "ID", "Fecha", "Jugador", "Campeón",
                "Calle", "Resultado", "Asesinatos", "Muertes"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla solo lectura
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(22);
        tabla.setBackground(Color.DARK_GRAY);
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(Color.RED);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(120, 0, 0));
        tabla.getTableHeader().setForeground(Color.WHITE);

// 🔹 CENTRAR CONTENIDO DE LA TABLA
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        frame.add(scroll, BorderLayout.CENTER);


        // ======================
        // BOTONES
        // ======================
        JButton btnCrear = crearBoton("Registrar partida");
        JButton btnVolver = crearBoton("Volver al menú");

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.add(btnCrear);
        panelBotones.add(btnVolver);

        frame.add(panelBotones, BorderLayout.SOUTH);

        // ======================
        // ACCIONES
        // ======================

        btnCrear.addActionListener(e -> {
            new VentanaRegistrarPartida().mostrar();
            cargarPartidas(modelo);
        });

        btnVolver.addActionListener(e -> {
            frame.dispose();
            for (Window w : Window.getWindows()) {
                if (w instanceof JFrame jf &&
                        "Menú LOL - CRUD Hibernate".equals(jf.getTitle())) {
                    jf.setVisible(true);
                    jf.toFront();
                    break;
                }
            }
        });

        cargarPartidas(modelo);

        frame.setVisible(true);
    }

    // ==================================================
    // ESTILO GAMER BOTONES
    // ==================================================
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

    // ==================================================
    // CARGAR PARTIDAS EN LA TABLA
    // ==================================================
    private void cargarPartidas(DefaultTableModel modelo) {

        modelo.setRowCount(0);

        List<Partida> lista = service.listarPartidas();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Partida p : lista) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getFecha().format(fmt),
                    p.getJugador().getUsername(),
                    p.getCampeon().getNombre(),
                    p.getCalle(),
                    p.getResultado(),
                    p.getAsesinatos(),
                    p.getMuertes()
            });
        }
    }
}
