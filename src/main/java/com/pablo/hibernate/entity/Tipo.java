package com.pablo.hibernate.entity;


import jakarta.persistence.*;

/**
 * Entidad que representa una categoría o arquetipo de campeón (e.g., Mago, Tanque, Asesino).
 * <p>
 * Esta clase se mapea contra la tabla 'tipos' en la base de datos.
 * Sirve para clasificar a los campeones según su rol principal en el juego.
 * <p>
 * Relaciones:
 * - {@link Campeon} (Uno a Muchos): Un tipo puede estar asociado a múltiples campeones,
 *   pero la relación es gestionada desde la entidad {@code Campeon}.
 * <p>
 * Campos relevantes:
 * - {@code tipo}: El nombre de la categoría (e.g., "Luchador").
 */
@Entity
@Table(name = "tipos")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO")
    private int id;

    @Column (name = "TIPO")
    private String tipo;

    public Tipo() {
    }

    /**
     * Constructor para crear una nueva instancia de Tipo.
     *
     * @param tipo El nombre del arquetipo o categoría.
     */
    public Tipo(String tipo){
        this.tipo = tipo;
    }

    //Getters y Setters.

    public int getId(){
        return id;
    }
    public String getTipo(){
        return  tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return tipo;
    }



}
