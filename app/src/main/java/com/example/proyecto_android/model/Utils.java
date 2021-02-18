package com.example.proyecto_android.model;

import android.content.Context;

import com.example.proyecto_android.activitys.MostrarMonumentoActivity;
import com.example.proyecto_android.fragments.MapaFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class Utils {

    public static String leerJson(Context context, String fileName) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null){
            content = content + line;
        }
        return content;
    }

    public static double[] UTM2Deg(double north, double east, int umtZone) {
        long lngOrigin = (long) Math.toRadians(umtZone * 6 - 183);
        int falseNorth = 0;

        double ecc = 0.081819190842622;
        double eccsq = ecc * ecc;
        double ecc2sq = eccsq / (1. - eccsq);
        double ecc2 = Math.sqrt(ecc2sq);      // Secondary eccentricity
        double E1 = ( 1 - Math.sqrt(1-eccsq) ) / ( 1 + Math.sqrt(1-eccsq) );
        double E12 = E1 * E1;
        double E13 = E12 * E1;
        double E14 = E13 * E1;

        double SemiMajor = 6378137.0;         // Ellipsoidal semi-major axis (Meters)
        double FalseEast = 500000.0;          // UTM East bias (Meters)
        double ScaleFactor = 0.9996;          // Scale at natural origin

        // Calculate the Cassini projection parameters

        double M1 = (north - falseNorth) / ScaleFactor;
        double Mu1 = M1 / ( SemiMajor * (1 - eccsq/4.0 - 3.0*eccsq*eccsq/64.0 - 5.0*eccsq*eccsq*eccsq/256.0) );

        double Phi1 = Mu1 + (3.0*E1/2.0 - 27.0*E13/32.0) * Math.sin(2.0*Mu1);
        Phi1 += (21.0*E12/16.0 - 55.0*E14/32.0)           * Math.sin(4.0*Mu1);
        Phi1 += (151.0*E13/96.0)                          * Math.sin(6.0*Mu1);
        Phi1 += (1097.0*E14/512.0)                        * Math.sin(8.0*Mu1);

        double sin2phi1 = Math.sin(Phi1) * Math.sin(Phi1);
        double Rho1 = (SemiMajor * (1.0-eccsq) ) / Math.pow(1.0-eccsq*sin2phi1,1.5);
        double Nu1 = SemiMajor / Math.sqrt(1.0-eccsq*sin2phi1);

        double T1 = Math.tan(Phi1) * Math.tan(Phi1);
        double T12 = T1 * T1;
        double C1 = ecc2sq * Math.cos(Phi1) * Math.cos(Phi1);
        double C12 = C1 * C1;
        double D  = (east - FalseEast) / (ScaleFactor * Nu1);
        double D2 = D * D;
        double D3 = D2 * D;
        double D4 = D3 * D;
        double D5 = D4 * D;
        double D6 = D5 * D;

        double lat = Phi1 - Nu1*Math.tan(Phi1)/Rho1 * ( D2/2.0 - (5.0 + 3.0*T1 + 10.0*C1 - 4.0*C12 - 9.0*ecc2sq)*D4/24.0 + (61.0 + 90.0*T1 + 298.0*C1 + 45.0*T12 - 252.0*ecc2sq - 3.0*C12)*D6/720.0 );

        lat = Math.toDegrees(lat);

        double lon = lngOrigin + (D - (1.0 + 2.0*T1 + C1)*D3/6.0 + (5.0 - 2.0*C1 + 28.0*T1 - 3.0*C12 + 8.0*ecc2sq + 24.0*T12)*D5/120.0) / Math.cos(Phi1);

        lon = Math.toDegrees(lon);

        double[] coordinates = new double[2];
        coordinates[0] = lat;
        coordinates[1] = lon;

        return coordinates;
    }
}
