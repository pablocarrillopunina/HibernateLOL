package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Tipo;
import com.pablo.hibernate.service.TipoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaTipos {

    private final TipoService service = new TipoService();

    public void mostrar() {

        JFrame frame = new JFrame("Gestión de Tipos");
        frame.setSize(450, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        // LISTA
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        lista.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lista.setBackground(Color.DARK_GRAY);
        lista.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(Color.RED));

        // BOTONES
        JButton btnCrear = crearBoton("Crear tipo");
        JButton btnActualizar = crearBoton("Actualizar tipo");
        JButton btnEliminar = crearBoton("Eliminar tipo");
        JButton btnVolver = crearBoton("Volver al menú");

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);

        cargarTipos(modelo);

        // ACCIONES

        btnCrear.addActionListener(e -> formularioCrear(modelo));

        btnEliminar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un tipo");
                return;
            }
            int id = Integer.parseInt(sel.split(":")[0]);
            service.eliminarTipo(id);
            cargarTipos(modelo);
        });

        btnActualizar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un tipo");
                return;
            }
            int id = Integer.parseInt(sel.split(":")[0]);
            formularioActualizar(id, modelo);
        });

        // 🔙 VOLVER AL MENÚ (reutiliza el menú existente)
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

        frame.setVisible(true);
    }

    // ---------------------- BOTÓN ESTILO MODERNO ----------------------
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

    // ---------------------- CARGAR TIPOS ----------------------
    private void cargarTipos(DefaultListModel<String> modelo) {
        modelo.clear();
        List<Tipo> lista = service.listarTipos();

        for (Tipo t : lista) {
            modelo.addElement(t.getId() + ": " + t.getTipo());
        }
    }

    // ---------------------- FORMULARIO CREAR ----------------------
    private void formularioCrear(DefaultListModel<String> modelo) {
        JTextField txtTipo = new JTextField();

        Object[] campos = {
                "Nombre del tipo:", txtTipo
        };

        int r = JOptionPane.showConfirmDialog(null, campos,
                "Crear tipo", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {

            if (txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                return;
            }

            service.crearTipo(txtTipo.getText().trim());
            cargarTipos(modelo);
        }
    }

    // ---------------------- FORMULARIO ACTUALIZAR ----------------------
    private void formularioActualizar(int id, DefaultListModel<String> modelo) {

        JTextField txtTipo = new JTextField();

        Object[] campos = {
                "Nuevo nombre del tipo:", txtTipo
        };

        int r = JOptionPane.showConfirmDialog(null, campos,
                "Actualizar tipo", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {

            if (txtTipo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                return;
            }

            service.actualizarTipo(id, txtTipo.getText().trim());
            cargarTipos(modelo);
        }
    }
}
