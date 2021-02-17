package com.example.proyecto_android.model;

public class Favorito {
    
    private int idUsuario = -1;
    private int idMonumento = -1;
    private int idFavoritos = -1;

    public Favorito() {
        
    }

    public Favorito(int idUsuario, int idMonumento, int idFavoritos) {
        this.idUsuario = idUsuario;
        this.idMonumento = idMonumento;
        this.idFavoritos = idFavoritos;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMonumento() {
        return idMonumento;
    }

    public void setIdMonumento(int idMonumento) {
        this.idMonumento = idMonumento;
    }

    public int getIdFavoritos() {
        return idFavoritos;
    }

    public void setIdFavoritos(int idFavoritos) {
        this.idFavoritos = idFavoritos;
    }

    @Override
    public String toString() {
        return "Favorito{" +
                "idUsuario=" + idUsuario +
                ", idMonumento=" + idMonumento +
                ", idFavoritos=" + idFavoritos +
                '}';
    }
}
