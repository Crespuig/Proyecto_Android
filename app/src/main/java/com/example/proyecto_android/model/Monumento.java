package com.example.proyecto_android.model;

public class Monumento {

    public int idNotes;
    public String name;
    public int numPol;
    public int codVia;
    public int telefono;
    public int ruta;
    public float coordenadas;
    public int imagen;

    public Monumento() {
    }

    public Monumento(String name, int imagen) {
        this.name = name;
        this.imagen = imagen;
    }

    public Monumento(int idNotes, String name, int numPol, int codVia, int telefono, int ruta, float coordenadas) {
        this.idNotes = idNotes;
        this.name = name;
        this.numPol = numPol;
        this.codVia = codVia;
        this.telefono = telefono;
        this.ruta = ruta;
        this.coordenadas = coordenadas;
    }

    public Monumento(int idNotes, String name, int numPol, int codVia, int telefono, int ruta, float coordenadas, int imagen) {
        this.idNotes = idNotes;
        this.name = name;
        this.numPol = numPol;
        this.codVia = codVia;
        this.telefono = telefono;
        this.ruta = ruta;
        this.coordenadas = coordenadas;
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

    public int getIdNotes() {
        return idNotes;
    }

    public void setIdNotes(int idNotes) {
        this.idNotes = idNotes;
    }

    public int getNumPol() {
        return numPol;
    }

    public void setNumPol(int numPol) {
        this.numPol = numPol;
    }

    public int getCodVia() {
        return codVia;
    }

    public void setCodVia(int codVia) {
        this.codVia = codVia;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public float getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(float coordenadas) {
        this.coordenadas = coordenadas;
    }

    @Override
    public String toString() {
        return "Monumento{" +
                "idNotes=" + idNotes +
                ", name='" + name + '\'' +
                ", numPol=" + numPol +
                ", codVia=" + codVia +
                ", telefono=" + telefono +
                ", ruta=" + ruta +
                ", coordenadas=" + coordenadas +
                ", imagen=" + imagen +
                '}';
    }
}
