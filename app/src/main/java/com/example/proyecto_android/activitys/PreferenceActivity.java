package com.example.proyecto_android.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.example.proyecto_android.R;

import java.util.Locale;

public class PreferenceActivity extends android.preference.PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, new PreferenciasFragment());
        ft.commit();


    }

    public static class PreferenciasFragment extends PreferenceFragment {

        SoundPool soundPool;
        int sonido_de_reproduccion;
        PreferenciasFragment context = this;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.opciones);

            ListPreference idioma = (ListPreference) findPreference("idioma");
            idioma.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    switch (o.toString()) {
                        case "0":
                            Toast.makeText(context.getActivity(), "ESPAÑOL", Toast.LENGTH_SHORT).show();
                            Locale español = new Locale("es", "ES");
                            Locale.setDefault(español);
                            Configuration config_es = new Configuration();
                            config_es.locale = español;
                            getResources().updateConfiguration(config_es, getResources().getDisplayMetrics());
                            break;
                        case "1":
                            Toast.makeText(context.getActivity(), "ENGLISH", Toast.LENGTH_SHORT).show();
                            Locale ingles = new Locale("en", "EN");
                            Locale.setDefault(ingles);
                            Configuration config_en = new Configuration();
                            config_en.locale = ingles;
                            getResources().updateConfiguration(config_en, getResources().getDisplayMetrics());
                            break;
                        case "2":
                            Toast.makeText(context.getActivity(), "VALENCIÀ", Toast.LENGTH_SHORT).show();
                            Locale valencia = new Locale("ca", "ES");
                            Locale.setDefault(valencia);
                            Configuration config_ca = new Configuration();
                            config_ca.locale = valencia;
                            getResources().updateConfiguration(config_ca, getResources().getDisplayMetrics());
                            break;
                    }
                    return false;
                }

            });

            /*ListPreference fuente = (ListPreference) findPreference("fuente");
            fuente.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    switch (o.toString()) {
                        case "LSE":
                            Toast.makeText(context.getActivity(), "LIBERATION SERIF", Toast.LENGTH_SHORT).show();

                            break;
                        case "CAS":
                            Toast.makeText(context.getActivity(), "CASUAL", Toast.LENGTH_SHORT).show();

                            break;
                        case "CUR":
                            Toast.makeText(context.getActivity(), "CURSIVE", Toast.LENGTH_SHORT).show();

                            break;
                        case "ANV":
                            Toast.makeText(context.getActivity(), "MONOSPACE", Toast.LENGTH_SHORT).show();

                            break;
                        case "DOS":
                            Toast.makeText(context.getActivity(), "CHILANKA", Toast.LENGTH_SHORT).show();

                            break;
                    }

                    return false;
                }
            });*/

        }
    }

}