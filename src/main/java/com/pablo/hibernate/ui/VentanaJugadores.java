package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Jugador;
import com.pablo.hibernate.service.JugadorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaJugadores {

    private final JugadorService service = new JugadorService();

    public void mostrar() {

        JFrame frame = new JFrame("Gestión de Jugadores");
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        // MODELO Y LISTA
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        lista.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lista.setBackground(Color.DARK_GRAY);
        lista.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(Color.RED));

        // BOTONES
        JButton btnCrear = crearBoton("Crear jugador");
        JButton btnActualizar = crearBoton("Actualizar jugador");
        JButton btnEliminar = crearBoton("Eliminar jugador");
        JButton btnRefrescar = crearBoton("Refrescar lista");

        // PANEL DE BOTONES
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setBackground(Color.BLACK);

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);

        // ACCIONES ===============================

        // REFRESCAR
        btnRefrescar.addActionListener(e -> cargarJugadores(modelo));

        // CREAR
        btnCrear.addActionListener(e -> formularioCrear(modelo));

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un jugador");
                return;
            }

            // FIX DEFINITIVO
            int id = Integer.parseInt(sel.substring(0, sel.indexOf(":")).trim());
            service.eliminarJugador(id);
            cargarJugadores(modelo);
        });

        // ACTUALIZAR
        btnActualizar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un jugador");
                return;
            }

            // FIX DEFINITIVO
            int id = Integer.parseInt(sel.substring(0, sel.indexOf(":")).trim());
            formularioActualizar(id, modelo);
        });

        // CARGAR LISTA AL INICIO
        cargarJugadores(modelo);

        frame.setVisible(true);
    }

    // ============================================
    // MÉTODO: BOTONES ESTILO GAMER
    // ============================================
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        Color rojo = new Color(179, 0, 0);
        Color rojoClaro = new Color(255, 51, 51);

        btn.setBackground(rojo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(rojoClaro);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(rojo);
            }
        });

        return btn;
    }

    // ============================================
    // CARGAR JUGADORES EN LA LISTA
    // ============================================
    private void cargarJugadores(DefaultListModel<String> modelo) {
        modelo.clear();
        List<Jugador> jugadores = service.listarJugadores();

        for (Jugador j : jugadores) {
            modelo.addElement(j.getId() + ": " + j.getUsername());
        }
    }

    // ============================================
    // FORMULARIO CREAR
    // ============================================
    private void formularioCrear(DefaultListModel<String> modelo) {

        JTextField txtUsuario = new JTextField();
        JTextField txtPass = new JTextField();

        Object[] campos = {
                "Usuario:", txtUsuario,
                "Contraseña:", txtPass
        };

        int r = JOptionPane.showConfirmDialog(null, campos,
                "Crear jugador", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {
            if (txtUsuario.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                return;
            }

            service.crearJugador(txtUsuario.getText(), txtPass.getText());
            cargarJugadores(modelo);
        }
    }

    // ============================================
// FORMULARIO ACTUALIZAR (con datos cargados)
// ============================================
    private void formularioActualizar(int id, DefaultListModel<String> modelo) {

        // Obtener jugador desde la BD
        Jugador j = service.buscarJugador(id);
        if (j == null) {
            JOptionPane.showMessageDialog(null, "Error: jugador no encontrado");
            return;
        }

        // Campos con valores actuales
        JTextField txtUsuario = new JTextField(j.getUsername());
        JTextField txtPass = new JTextField(j.getPassword());

        Object[] campos = {
                "Nuevo usuario:", txtUsuario,
                "Nueva contraseña:", txtPass
        };

        int r = JOptionPane.showConfirmDialog(null, campos,
                "Actualizar jugador", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {

            String nuevoUser = txtUsuario.getText();
            String nuevaPass = txtPass.getText();

            if (nuevoUser.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El usuario no puede estar vacío");
                return;
            }

            service.actualizarJugador(id, nuevoUser, nuevaPass);
            cargarJugadores(modelo);
        }
    }

}
