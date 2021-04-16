package com.example.proyecto_android.model;

public class Favorito {
    
    private int id;
    private Monumento monumento;
    private Usuario usuario;

    public Favorito() {
    }

    public Favorito(int id, Monumento monumento, Usuario usuario) {
        this.id = id;
        this.monumento = monumento;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Monumento getMonumento() {
        return monumento;
    }

    public void setMonumento(Monumento monumento) {
        this.monumento = monumento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
