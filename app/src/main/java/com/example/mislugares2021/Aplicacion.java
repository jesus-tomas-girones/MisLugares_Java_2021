package com.example.mislugares2021;

import android.app.Application;

import com.example.mislugares2021.datos.LugaresBDAdapter;
import com.example.mislugares2021.modelo.GeoPunto;
import com.example.mislugares2021.presentacion.AdaptadorLugaresBD;

public class Aplicacion extends Application {

   public LugaresBDAdapter lugares;
   public AdaptadorLugaresBD adaptador;
   public GeoPunto posicionActual = new GeoPunto(0.0, 0.0);

   @Override public void onCreate() {
      super.onCreate();
      lugares = new LugaresBDAdapter(this);
      adaptador= new AdaptadorLugaresBD(lugares, lugares.extraeCursor());
      lugares.setAdaptador(adaptador);
   }
}
