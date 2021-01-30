package com.example.proyecto_android.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable{

    private int id;
    private String nif;
    private String nombre;
    private String apellidos;
    private String claveSeguridad;
    private String email;
    private boolean admin;

    public Usuario(int id, String nif, String nombre, String apellidos, String claveSeguridad, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.claveSeguridad = claveSeguridad;
        this.email = email;
    }

    public Usuario(int id, String nif, String nombre, String apellidos, String claveSeguridad, String email, boolean admin) {
        this.id = id;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.claveSeguridad = claveSeguridad;
        this.email = email;
        this.admin = admin;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClaveSeguridad() {
        return claveSeguridad;
    }

    public void setClaveSeguridad(String claveSeguridad) {
        this.claveSeguridad = claveSeguridad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Usuario(){
        super();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nif='" + nif + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", claveSeguridad='" + claveSeguridad + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                '}';
    }
}
