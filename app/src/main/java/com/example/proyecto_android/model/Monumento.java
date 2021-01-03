package com.example.proyecto_android.model;

public class Monumento {

    public String name;
    public int imagen;

    public Monumento() {
    }

    public Monumento(String name, int imagen) {
        this.name = name;
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Monumento{" +
                "name='" + name + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
