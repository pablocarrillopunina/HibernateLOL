package com.pablo.hibernate.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_partida")
    private int id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "Id_jugador", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "Id_campeon", nullable = false)
    private Campeon campeon;

    @Column(name = "Calle")
    private String calle;

    @Column(name = "Resultado")
    private String resultado;

    @Column(name = "asesinatos")
    private Integer asesinatos;

    @Column(name = "muertes")
    private Integer muertes;

    public Partida() {}

    // GETTERS & SETTERS
    public int getId() { return id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    public Campeon getCampeon() { return campeon; }
    public void setCampeon(Campeon campeon) { this.campeon = campeon; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public Integer getAsesinatos() { return asesinatos; }
    public void setAsesinatos(Integer asesinatos) { this.asesinatos = asesinatos; }

    public Integer getMuertes() { return muertes; }
    public void setMuertes(Integer muertes) { this.muertes = muertes; }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", jugador=" + jugador +
                ", campeon=" + campeon +
                ", calle='" + calle + '\'' +
                ", resultado='" + resultado + '\'' +
                ", asesinatos=" + asesinatos +
                ", muertes=" + muertes +
                '}';
    }
}
