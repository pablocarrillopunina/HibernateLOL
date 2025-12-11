package com.pablo.hibernate.service;

import com.pablo.hibernate.entity.Jugador;
import com.pablo.hibernate.repository.JugadorRepository;

import java.util.List;

public class JugadorService {

    private final JugadorRepository repo = new JugadorRepository();

    public void crearJugador(String username, String password) {
        if (username == null || username.isBlank()) return;
        Jugador j = new Jugador();
        j.setUsername(username);
        j.setPassword(password);
        repo.save(j);
    }

    public List<Jugador> listarJugadores() {
        return repo.findAll();
    }

    public Jugador buscarJugador(int id) {
        return repo.findById(id);
    }


    public void eliminarJugador(int id) {
        Jugador j = repo.findById(id);
        if (j != null) repo.delete(j);
    }

    public void actualizarJugador(int id, String username, String password) {
        Jugador j = repo.findById(id);
        if (j != null) {
            j.setUsername(username);
            j.setPassword(password);
            repo.update(j);
        }
    }
}
