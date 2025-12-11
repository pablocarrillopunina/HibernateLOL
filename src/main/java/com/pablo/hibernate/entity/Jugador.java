package com.pablo.hibernate.entity;

import jakarta.persistence.*;

/**
 * Entidad que representa a un Jugador (usuario) en el sistema.
 * <p>
 * Esta clase se mapea contra la tabla 'jugadiores' en la base de datos.
 * Almacena la información de autenticación de un usuario.
 * <p>
 * No tiene relaciones directas con otras entidades en este modelo, pero representa
 * la cuenta que participaría en una {@link Partida}.
 * <p>
 * Campos relevantes:
 * - {@code username}: Identificador único del jugador.
 * - {@code password}: Credencial de acceso.
 */
@Entity
@Table(name = "jugadiores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_jugador")
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    public Jugador() {
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return username;
    }

}
