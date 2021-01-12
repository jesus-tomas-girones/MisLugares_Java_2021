package com.example.mislugares2021.casos_uso;

import android.app.Activity;
import android.content.Intent;

import com.example.mislugares2021.presentacion.AcercaDeActivity;
import com.example.mislugares2021.presentacion.PreferenciasActivity;

public class CasosUsoActividades {

   public static final int RESULTADO_PREFERENCIAS = 0;
   private Activity actividad;

   public CasosUsoActividades(Activity actividad) {
      this.actividad = actividad;
   }

   public void lanzarAcercaDe() {
      Intent i = new Intent(actividad, AcercaDeActivity.class);
      actividad.startActivity(i);
   }

   public void lanzarPreferencias() {
      Intent i = new Intent(actividad, PreferenciasActivity.class);
      actividad.startActivityForResult(i, RESULTADO_PREFERENCIAS);
   }

   public void lanzarMapa() {
      Intent i = new Intent(actividad, PreferenciasActivity.class);
      actividad.startActivity(i);
   }

}
