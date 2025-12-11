package com.pablo.hibernate.ui;

import com.pablo.hibernate.entity.Campeon;
import com.pablo.hibernate.entity.Jugador;
import com.pablo.hibernate.entity.Partida;
import com.pablo.hibernate.service.CampeonService;
import com.pablo.hibernate.service.JugadorService;
import com.pablo.hibernate.service.PartidaService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class VentanaRegistrarPartida {

    private final PartidaService partidaService = new PartidaService();
    private final JugadorService jugadorService = new JugadorService();
    private final CampeonService campeonService = new CampeonService();

    public void mostrar() {

        JDialog dialog = new JDialog();
        dialog.setTitle("Registrar Partida");
        dialog.setSize(350, 400);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new GridLayout(8, 2, 10, 10));

        JComboBox<Jugador> cboJugador = new JComboBox<>();
        JComboBox<Campeon> cboCampeon = new JComboBox<>();
        JComboBox<String> cboCalle = new JComboBox<>(new String[]{"TOP", "MID", "JUNGLA", "ADC", "SUPPORT"});
        JComboBox<String> cboResultado = new JComboBox<>(new String[]{"Victoria", "Derrota"});

        JTextField txtAsesinatos = new JTextField();
        JTextField txtMuertes = new JTextField();

        for (Jugador j : jugadorService.listarJugadores()) cboJugador.addItem(j);
        for (Campeon c : campeonService.listarCampeones()) cboCampeon.addItem(c);

        dialog.add(new JLabel("Jugador:"));
        dialog.add(cboJugador);

        dialog.add(new JLabel("Campeón:"));
        dialog.add(cboCampeon);

        dialog.add(new JLabel("Calle:"));
        dialog.add(cboCalle);

        dialog.add(new JLabel("Resultado:"));
        dialog.add(cboResultado);

        dialog.add(new JLabel("Asesinatos:"));
        dialog.add(txtAsesinatos);

        dialog.add(new JLabel("Muertes:"));
        dialog.add(txtMuertes);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        dialog.add(btnGuardar);
        dialog.add(btnCancelar);

        btnGuardar.addActionListener(e -> {

            try {
                Partida p = new Partida();
                p.setFecha(LocalDateTime.now());
                p.setJugador((Jugador) cboJugador.getSelectedItem());
                p.setCampeon((Campeon) cboCampeon.getSelectedItem());
                p.setCalle((String) cboCalle.getSelectedItem());
                p.setResultado((String) cboResultado.getSelectedItem());
                p.setAsesinatos(Integer.parseInt(txtAsesinatos.getText()));
                p.setMuertes(Integer.parseInt(txtMuertes.getText()));

                partidaService.crearPartida(p);
                JOptionPane.showMessageDialog(dialog, "Partida registrada correctamente.");
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error al registrar: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
