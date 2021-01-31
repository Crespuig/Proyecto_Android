package com.example.proyecto_android.model;

import android.content.Context;

import com.example.proyecto_android.activitys.MostrarMonumentoActivity;
import com.example.proyecto_android.fragments.MapaFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    public static String leerJson(MostrarMonumentoActivity context, String fileName) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null){
            content = content + line;
        }
        return content;
    }
}
