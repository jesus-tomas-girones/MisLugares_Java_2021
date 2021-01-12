package com.example.mislugares2021.datos;

import android.content.Context;

import com.example.mislugares2021.modelo.Lugar;
import com.example.mislugares2021.presentacion.AdaptadorLugaresBD;

public class LugaresBDAdapter extends LugaresBD {

   private AdaptadorLugaresBD adaptador;

   public LugaresBDAdapter(Context contexto) {
      super(contexto);
   }

   //Devuelve el elemento dada su posición
   public Lugar elementoPos(int pos) {
      return adaptador.lugarPosicion (pos);
   }

   public AdaptadorLugaresBD getAdaptador() {
      return adaptador;
   }

   public void setAdaptador(AdaptadorLugaresBD adaptador) {
      this.adaptador = adaptador;
   }

   @Override public void actualiza(int id, Lugar lugar) {
      super.actualiza(id,lugar);
      adaptador.setCursor(extraeCursor());
      adaptador.notifyDataSetChanged();
   }

   public int actualizaPosLugar(int pos, Lugar lugar) {
      int id = adaptador.idPosicion(pos);
      actualiza(id, lugar);
      return adaptador.posicionId(id); //devolvemos la nueva posición
   }

   @Override public int tamaño(){
      return adaptador.getItemCount();
   }
}
