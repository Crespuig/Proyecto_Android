package com.example.proyecto_android.bbdd;

public class Constantes {

    public static final String C_MODO = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552;
    public static final int C_EDITAR = 553 ;

    //*********
    //* ESTRUCTURA DE LA TABLA MONUMENTOS
    //*********
    public static final String FIELD_MONUMENTOS_IDNOTES = "idNotes";
    public static final String FIELD_NOMBRE = "nombre";
    public static final String FIELD_NUMPOL = "numPol";
    public static final String FIELD_CODVIA = "codVia";
    public static final String FIELD_TELEFONO = "telefono";
    public static final String FIELD_RUTA = "ruta";
    public static final String FIELD_COORDENADAS = "coordenadas";
    public static final String MONUMENTOS_TABLE = "monumentos";

    public static final String[] CAMPOS_MONUMENTOS = new String[]{FIELD_MONUMENTOS_IDNOTES,FIELD_NOMBRE,FIELD_NUMPOL,FIELD_CODVIA,FIELD_TELEFONO,FIELD_RUTA,FIELD_COORDENADAS};

}
