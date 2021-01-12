package com.example.mislugares2021.presentacion;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mislugares2021.R;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class PreferenciasFragment extends PreferenceFragmentCompat {
   @Override
   public void onCreatePreferences(Bundle savedInstanceState,
                                   String rootKey) {
      setPreferencesFromResource(R.xml.preferencias, rootKey);
      final EditTextPreference fragmentos = (EditTextPreference)
              findPreference("maximo");
      fragmentos.setOnPreferenceChangeListener(
              new Preference.OnPreferenceChangeListener() {
                 @Override
                 public boolean onPreferenceChange(Preference preference, Object
                         newValue) {
                    int valor;
                    try {
                       valor = Integer.parseInt((String)newValue);
                    } catch(Exception e) {
                       Toast.makeText(getActivity(), "Ha de ser un número",
                               Toast.LENGTH_SHORT).show();
                       return false;
                    }
                    if (valor>=0 && valor<=99) {
                       fragmentos.setSummary(
                               "Limita en número de valores que se muestran ("+valor+")");
                       return true;
                    } else {
                       Toast.makeText(getActivity(), "Valor Máximo 99",
                               Toast.LENGTH_SHORT).show();
                       return false;
                    }
                 }
              });

   }
}
