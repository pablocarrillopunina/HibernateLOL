package com.pablo.hibernate.entity;

import jakarta.persistence.*;

/**
 * Entidad que representa a un Campeón en el universo de League of Legends (LOL).
 * <p>
 * Esta clase se mapea contra la tabla 'campeones' en la base de datos.
 * Un campeón tiene un tipo, una habilidad única y estadísticas base.
 * <p>
 * Relaciones:
 * - {@link Tipo} (Muchos a Uno): Cada campeón pertenece a un único tipo (e.g., Mago, Luchador).
 * - {@link Habilidad} (Uno a Uno): Cada campeón posee una habilidad distintiva.
 * <p>
 * Campos relevantes:
 * - {@code nombre}: Nombre único del campeón.
 * - {@code vida}, {@code energia}, {@code dMagico}, {@code dFisico}: Atributos de combate.
 */
@Entity
@Table(name = "campeones")
public class Campeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Relación Muchos a Uno con la entidad Tipo.
     * Representa el arquetipo o clase al que pertenece el campeón.
     * Cada campeón está asociado a un solo tipo, pero un tipo puede englobar a múltiples campeones.
     */
    @ManyToOne
    @JoinColumn(name = "ID_TIPO")
    private Tipo tipo;

    /**
     * Relación Uno a Uno con la entidad Habilidad.
     * Define la habilidad única y característica de este campeón.
     * Cada campeón tiene una sola habilidad asociada directamente.
     */
    @OneToOne
    @JoinColumn(name = "ID_HABILIDAD")
    private Habilidad habilidad;

    @Column(name = "VIDA")
    private int vida;

    @Column(name = "ENERGIA")
    private int energia;

    @Column(name = "D_MAGICO")
    private Integer dMagico;

    @Column(name = "D_FISICO")
    private int dFisico;

    public Campeon() {
    }

    /**
     * Constructor para crear una nueva instancia de Campeon con sus atributos fundamentales.
     * Inicializa el campeón con valores predeterminados de vida, energía y daño físico.
     *
     * @param nombre    El nombre del campeón.
     * @param tipo      El {@link Tipo} al que pertenece el campeón.
     * @param habilidad La {@link Habilidad} única del campeón.
     */
    public Campeon(String nombre, Tipo tipo, Habilidad habilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidad = habilidad;
        this.vida = 100;
        this.energia = 100;
        this.dFisico = 10;
    }

    //Getter and Setter

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Habilidad getHabilidad() {
        return habilidad;
    }

    public int getVida() {
        return vida;
    }

    public int getEnergia() {
        return energia;
    }

    public Integer getdMagico() {
        return dMagico;
    }

    public int getdFisico() {
        return dFisico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setHabilidad(Habilidad habilidad) {
        this.habilidad = habilidad;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public void setdMagico(Integer dMagico) {
        this.dMagico = dMagico;
    }

    public void setdFisico(int dFisico) {
        this.dFisico = dFisico;
    }

    @Override
    public String toString() {
        return nombre;
    }

}


