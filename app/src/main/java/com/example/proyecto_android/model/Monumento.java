package com.example.proyecto_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Monumento {

    @SerializedName("idnotes")
    @Expose
    private int idNotes;
    @SerializedName("nombre")
    @Expose
    private String name;
    @SerializedName("numpol")
    @Expose
    private String numPol;
    @SerializedName("codvia")
    @Expose
    private int codVia;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("ruta")
    @Expose
    private int ruta;
    @SerializedName("latitud")
    @Expose
    private float latitud;
    @SerializedName("longitud")
    @Expose
    private float longitud;
    @SerializedName("img")
    @Expose
    private String imagen;

    public Monumento() {
    }


    public Monumento(int idNotes, String name, String numPol, int codVia, String telefono, int ruta, float latitud, float longitud, String imagen) {
        this.idNotes = idNotes;
        this.name = name;
        this.numPol = numPol;
        this.codVia = codVia;
        this.telefono = telefono;
        this.ruta = ruta;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdNotes() {
        return idNotes;
    }

    public void setIdNotes(int idNotes) {
        this.idNotes = idNotes;
    }

    public String getNumPol() {
        return numPol;
    }

    public void setNumPol(String numPol) {
        this.numPol = numPol;
    }

    public int getCodVia() {
        return codVia;
    }

    public void setCodVia(int codVia) {
        this.codVia = codVia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Monumento{" +
                "idNotes=" + idNotes +
                ", name='" + name + '\'' +
                ", numPol='" + numPol + '\'' +
                ", codVia=" + codVia +
                ", telefono='" + telefono + '\'' +
                ", ruta=" + ruta +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
