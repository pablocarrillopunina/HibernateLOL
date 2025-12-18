package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Habilidad;
import com.pablo.hibernate.service.HabilidadService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaHabilidades {

    private final HabilidadService service = new HabilidadService();

    public void mostrar() {

        JFrame frame = new JFrame("Gestión de Habilidades");
        frame.setSize(550, 550);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        // LISTA
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);

        lista.setBackground(Color.DARK_GRAY);
        lista.setForeground(Color.WHITE);
        lista.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(Color.RED));

        // BOTONES
        JButton btnCrear = crearBoton("Crear habilidad");
        JButton btnActualizar = crearBoton("Actualizar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnVolver = crearBoton("Volver al menú");

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setBackground(Color.BLACK);

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);

        cargarHabilidades(modelo);

        // ---------------- ACCIONES ----------------

        // CREAR
        btnCrear.addActionListener(e -> formularioCrear(modelo));

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona una habilidad");
                return;
            }
            int id = Integer.parseInt(sel.split(" ")[0]);
            service.eliminarHabilidad(id);
            cargarHabilidades(modelo);
        });

        // ACTUALIZAR
        btnActualizar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona una habilidad");
                return;
            }
            int id = Integer.parseInt(sel.split(" ")[0]);
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

    // ---------------------- BOTÓN ESTILO GAMER ----------------------
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

    // ---------------------- CARGAR HABILIDADES ----------------------
    private void cargarHabilidades(DefaultListModel<String> modelo) {
        modelo.clear();
        List<Habilidad> lista = service.listarHabilidades();

        for (Habilidad h : lista) {
            modelo.addElement(
                    h.getId() +
                            " | Q: " + h.getQ() +
                            " | W: " + h.getW() +
                            " | E: " + h.getE() +
                            " | R: " + h.getR()
            );
        }
    }

    // ---------------------- FORMULARIO CREAR ----------------------
    private void formularioCrear(DefaultListModel<String> modelo) {

        JTextField q = new JTextField();
        JTextField w = new JTextField();
        JTextField e = new JTextField();
        JTextField r = new JTextField();

        Object[] campos = {
                "Habilidad Q:", q,
                "Habilidad W:", w,
                "Habilidad E:", e,
                "Habilidad R:", r
        };

        int res = JOptionPane.showConfirmDialog(null, campos,
                "Crear habilidad", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {

            if (q.getText().isEmpty() || w.getText().isEmpty()
                    || e.getText().isEmpty() || r.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "Todos los campos deben tener texto");
                return;
            }

            service.crearHabilidad(q.getText(), w.getText(), e.getText(), r.getText());
            cargarHabilidades(modelo);
        }
    }

    // ---------------------- FORMULARIO ACTUALIZAR ----------------------
    private void formularioActualizar(int id, DefaultListModel<String> modelo) {

        JTextField q = new JTextField();
        JTextField w = new JTextField();
        JTextField e = new JTextField();
        JTextField r = new JTextField();

        Object[] campos = {
                "Nueva Q:", q,
                "Nueva W:", w,
                "Nueva E:", e,
                "Nueva R:", r
        };

        int res = JOptionPane.showConfirmDialog(null, campos,
                "Actualizar habilidad", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {

            if (q.getText().isEmpty() || w.getText().isEmpty()
                    || e.getText().isEmpty() || r.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "Todos los campos deben tener texto");
                return;
            }

            service.actualizarHabilidad(
                    id,
                    q.getText(),
                    w.getText(),
                    e.getText(),
                    r.getText()
            );

            cargarHabilidades(modelo);
        }
    }
}
