package com.pablo.hibernate.service;

import com.pablo.hibernate.entity.Campeon;
import com.pablo.hibernate.entity.Habilidad;
import com.pablo.hibernate.entity.Tipo;
import com.pablo.hibernate.repository.CampeonRepository;

import java.util.List;

public class CampeonService {

    private final CampeonRepository repo = new CampeonRepository();

    // ===================== CREAR COMPLETO =====================
    public void crearCampeonCompleto(String nombre, Tipo tipo, Habilidad habilidad,
                                     int vida, int energia, int dFisico, Integer dMagico) {

        Campeon c = new Campeon();
        c.setNombre(nombre);
        c.setTipo(tipo);
        c.setHabilidad(habilidad);
        c.setVida(vida);
        c.setEnergia(energia);
        c.setdFisico(dFisico);
        c.setdMagico(dMagico);

        repo.save(c);
    }

    // ===================== LISTAR =====================
    public List<Campeon> listarCampeones() {
        return repo.findAll();
    }

    // ===================== BUSCAR POR ID (AÑADIDO) =====================
    public Campeon buscarPorId(int id) {
        return repo.findById(id);
    }

    // ===================== ELIMINAR =====================
    public void eliminarCampeon(int id) {
        Campeon c = repo.findById(id);
        if (c != null) {
            repo.delete(c);
        }
    }

    // ===================== ACTUALIZAR COMPLETO =====================
    public void actualizarCampeonCompleto(int id, String nombre, Tipo tipo, Habilidad habilidad,
                                          int vida, int energia, int dFisico, Integer dMagico) {

        Campeon c = repo.findById(id);
        if (c != null) {
            c.setNombre(nombre);
            c.setTipo(tipo);
            c.setHabilidad(habilidad);
            c.setVida(vida);
            c.setEnergia(energia);
            c.setdFisico(dFisico);
            c.setdMagico(dMagico);

            repo.update(c);
        }
    }
}
