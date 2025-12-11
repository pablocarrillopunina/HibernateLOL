package com.pablo.hibernate.entity;

import jakarta.persistence.*;

/**
 * Entidad que representa el conjunto de habilidades de un campeón en el universo LOL.
 * <p>
 * Esta clase se mapea contra la tabla 'habilidades' en la base de datos.
 * Cada registro corresponde a un set completo de habilidades (Q, W, E, R) que puede ser
 * asociado a un campeón.
 * <p>
 * Relaciones:
 * - {@link Campeon} (Uno a Uno): Esta entidad es la parte propietaria de la relación
 *   con Campeon, aunque la columna de unión está en la tabla 'campeones'. Cada conjunto
 *   de habilidades está diseñado para un único campeón.
 * <p>
 * Campos relevantes:
 * - {@code q}, {@code w}, {@code e}, {@code r}: Nombres o descripciones de las habilidades
 *   asignadas a las teclas Q, W, E y R respectivamente.
 */
@Entity
@Table(name = "habilidades")
public class Habilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABILIDAD")
    private int id;

    @Column(name = "HABILIDAD_Q")
    private String q;

    @Column(name = "HABILIDAD_W")
    private String w;

    @Column(name = "HABILIDAD_E")
    private String e;

    @Column(name = "HABILIDAD_R")
    private String r;

    public Habilidad(){

    }

    /**
     * Constructor para crear una instancia de Habilidad con todas sus propiedades.
     *
     * @param id El identificador único del conjunto de habilidades.
     * @param q  La habilidad asociada a la tecla 'Q'.
     * @param w  La habilidad asociada a la tecla 'W'.
     * @param e  La habilidad asociada a la tecla 'E'.
     * @param r  La habilidad (ultimate) asociada a la tecla 'R'.
     */
    public Habilidad(int id, String q, String w, String e, String r) {
        this.id = id;
        this.q = q;
        this.w = w;
        this.e = e;
        this.r = r;
    }

    //Getter and Setter

    public int getId() {
        return id;
    }

    public String getQ() {
        return q;
    }

    public String getW() {
        return w;
    }

    public String getE() {
        return e;
    }

    public String getR() {
        return r;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public void setW(String w) {
        this.w = w;
    }

    public void setE(String e) {
        this.e = e;
    }

    public void setR(String r) {
        this.r = r;
    }


    @Override
    public String toString() {
        return "Q: " + q + " | W: " + w + " | E: " + e + " | R: " + r;
    }

}
