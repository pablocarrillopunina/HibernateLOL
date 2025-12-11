package com.pablo.hibernate.service;

import com.pablo.hibernate.entity.Tipo;
import com.pablo.hibernate.repository.TipoRepository;

import java.util.List;

public class TipoService {

    private final TipoRepository repo = new TipoRepository();

    public void crearTipo(String nombre) {
        Tipo t = new Tipo();
        t.setTipo(nombre);   // ✔ CORREGIDO (antes setNombre)
        repo.save(t);
    }

    public List<Tipo> listarTipos() {
        return repo.findAll();
    }

    public void eliminarTipo(int id) {
        Tipo t = repo.findById(id);
        if (t != null) repo.delete(t);
    }

    public void actualizarTipo(int id, String nombre) {
        Tipo t = repo.findById(id);
        if (t != null) {
            t.setTipo(nombre);   // ✔ CORREGIDO (antes setNombre)
            repo.update(t);
        }
    }
}
