package com.pablo.hibernate.service;

import com.pablo.hibernate.entity.Partida;
import com.pablo.hibernate.repository.PartidaRepository;

import java.util.List;

public class PartidaService {

    private final PartidaRepository repo = new PartidaRepository();

    public void crearPartida(Partida partida) {
        repo.save(partida);
    }

    public List<Partida> listarPartidas() {
        return repo.findAll();
    }

    public Partida buscarPorId(int id) {
        return repo.findById(id);
    }
}
