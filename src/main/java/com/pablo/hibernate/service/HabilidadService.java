package com.pablo.hibernate.service;

import com.pablo.hibernate.entity.Habilidad;
import com.pablo.hibernate.repository.HabilidadRepository;

import java.util.List;

public class HabilidadService {

    private final HabilidadRepository repo = new HabilidadRepository();

    public void crearHabilidad(String q, String w, String e, String r) {
        Habilidad h = new Habilidad();
        h.setQ(q);
        h.setW(w);
        h.setE(e);
        h.setR(r);
        repo.save(h);
    }

    public List<Habilidad> listarHabilidades() {
        return repo.findAll();
    }

    public void eliminarHabilidad(int id) {
        Habilidad h = repo.findById(id);
        if (h != null) repo.delete(h);
    }

    public void actualizarHabilidad(int id, String q, String w, String e, String r) {
        Habilidad h = repo.findById(id);
        if (h != null) {
            h.setQ(q);
            h.setW(w);
            h.setE(e);
            h.setR(r);
            repo.update(h);
        }
    }
}
