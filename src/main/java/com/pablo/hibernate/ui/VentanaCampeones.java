package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Campeon;
import com.pablo.hibernate.entity.Habilidad;
import com.pablo.hibernate.entity.Tipo;
import com.pablo.hibernate.service.CampeonService;
import com.pablo.hibernate.service.HabilidadService;
import com.pablo.hibernate.service.TipoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VentanaCampeones {

    private final CampeonService service = new CampeonService();
    private final TipoService tipoService = new TipoService();
    private final HabilidadService habilidadService = new HabilidadService();

    public void mostrar() {

        JFrame frame = new JFrame("Gestión de Campeones");
        frame.setSize(750, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        // LISTA REAL DE CAMPEONES
        DefaultListModel<Campeon> modelo = new DefaultListModel<>();
        JList<Campeon> lista = new JList<>(modelo);

        lista.setBackground(new Color(30, 30, 30));
        lista.setForeground(new Color(255, 230, 200));
        lista.setFont(new Font("Consolas", Font.BOLD, 14));
        lista.setSelectionBackground(new Color(120, 0, 0));
        lista.setSelectionForeground(Color.WHITE);
        lista.setFixedCellHeight(-1);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 3));
        scroll.getViewport().setBackground(new Color(20, 20, 20));

        // RENDERER
        lista.setCellRenderer((list, c, index, isSelected, cellHasFocus) -> {

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(isSelected ? new Color(120, 0, 0) : new Color(30, 30, 30));
            panel.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 2));

            JLabel id = new JLabel("[ID " + c.getId() + "]  " + c.getNombre());
            id.setForeground(new Color(255, 230, 200));
            id.setFont(new Font("Consolas", Font.BOLD, 14));

            JLabel tipo = new JLabel("Tipo: " + c.getTipo().getTipo());
            tipo.setForeground(new Color(200, 200, 200));

            JLabel hab = new JLabel("Habilidad:  Q=" + c.getHabilidad().getQ()
                    + " | W=" + c.getHabilidad().getW()
                    + " | E=" + c.getHabilidad().getE()
                    + " | R=" + c.getHabilidad().getR());
            hab.setForeground(new Color(180, 180, 255));

            JLabel stats = new JLabel(
                    "Stats → Vida: " + c.getVida()
                            + " | Energía: " + c.getEnergia()
                            + " | Físico: " + c.getdFisico()
                            + " | Mágico: " + c.getdMagico()
            );
            stats.setForeground(new Color(200, 160, 255));

            panel.add(id);
            panel.add(tipo);
            panel.add(hab);
            panel.add(stats);

            return panel;
        });

        JButton btnCrear = crearBoton("Crear campeón");
        JButton btnActualizar = crearBoton("Actualizar");
        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnRefrescar = crearBoton("Refrescar");

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);

        cargarCampeones(modelo);

        btnRefrescar.addActionListener(e -> cargarCampeones(modelo));

        btnCrear.addActionListener(e -> formularioCrear(modelo));

        btnEliminar.addActionListener(e -> {
            Campeon sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un campeón");
                return;
            }
            service.eliminarCampeon(sel.getId());
            cargarCampeones(modelo);
        });

        btnActualizar.addActionListener(e -> {
            Campeon sel = lista.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un campeón");
                return;
            }
            formularioActualizar(sel.getId(), modelo);
        });

        frame.setVisible(true);
    }

    // =========================== BOTÓN ===========================
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        Color rojo = new Color(179, 0, 0);
        Color rojoClaro = new Color(255, 51, 51);

        btn.setBackground(rojo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
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

    // =========================== CARGAR ===========================
    private void cargarCampeones(DefaultListModel<Campeon> modelo) {
        modelo.clear();
        List<Campeon> lista = service.listarCampeones();
        lista.forEach(modelo::addElement);
    }

    // =========================== CREAR ===========================
    private void formularioCrear(DefaultListModel<Campeon> modelo) {

        JTextField txtNombre = new JTextField();
        JTextField txtVida = new JTextField("100");
        JTextField txtEnergia = new JTextField("100");
        JTextField txtFisico = new JTextField("10");
        JTextField txtMagico = new JTextField("0");

        JComboBox<Tipo> comboTipo = new JComboBox<>();
        JComboBox<Habilidad> comboHab = new JComboBox<>();

        tipoService.listarTipos().forEach(comboTipo::addItem);
        habilidadService.listarHabilidades().forEach(comboHab::addItem);

        Object[] campos = {
                "Nombre:", txtNombre,
                "Tipo:", comboTipo,
                "Habilidad:", comboHab,
                "Vida:", txtVida,
                "Energía:", txtEnergia,
                "Daño físico:", txtFisico,
                "Daño mágico:", txtMagico
        };

        int r = JOptionPane.showConfirmDialog(null, campos,
                "Crear campeón", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {

            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                return;
            }

            service.crearCampeonCompleto(
                    txtNombre.getText(),
                    (Tipo) comboTipo.getSelectedItem(),
                    (Habilidad) comboHab.getSelectedItem(),
                    Integer.parseInt(txtVida.getText()),
                    Integer.parseInt(txtEnergia.getText()),
                    Integer.parseInt(txtFisico.getText()),
                    Integer.parseInt(txtMagico.getText())
            );

            cargarCampeones(modelo);
        }
    }

    // =========================== ACTUALIZAR (CORREGIDO) ===========================
    private void formularioActualizar(int id, DefaultListModel<Campeon> modelo) {

        Campeon c = service.buscarPorId(id);

        JTextField txtNombre = new JTextField(c.getNombre());
        JTextField txtVida = new JTextField(String.valueOf(c.getVida()));
        JTextField txtEnergia = new JTextField(String.valueOf(c.getEnergia()));
        JTextField txtFisico = new JTextField(String.valueOf(c.getdFisico()));
        JTextField txtMagico = new JTextField(String.valueOf(c.getdMagico()));

        JComboBox<Tipo> comboTipo = new JComboBox<>();
        JComboBox<Habilidad> comboHab = new JComboBox<>();

        tipoService.listarTipos().forEach(comboTipo::addItem);
        habilidadService.listarHabilidades().forEach(comboHab::addItem);

        comboTipo.setSelectedItem(c.getTipo());
        comboHab.setSelectedItem(c.getHabilidad());

        Object[] campos = {
                "Nombre:", txtNombre,
                "Tipo:", comboTipo,
                "Habilidad:", comboHab,
                "Vida:", txtVida,
                "Energía:", txtEnergia,
                "Daño físico:", txtFisico,
                "Daño mágico:", txtMagico
        };

        int r = JOptionPane.showConfirmDialog(null, campos,
                "Actualizar campeón", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {

            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                return;
            }

            service.actualizarCampeonCompleto(
                    id,
                    txtNombre.getText(),
                    (Tipo) comboTipo.getSelectedItem(),
                    (Habilidad) comboHab.getSelectedItem(),
                    Integer.parseInt(txtVida.getText()),
                    Integer.parseInt(txtEnergia.getText()),
                    Integer.parseInt(txtFisico.getText()),
                    Integer.parseInt(txtMagico.getText())
            );

            cargarCampeones(modelo);
        }
    }
}
