package com.example.mislugares2021.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mislugares2021.Aplicacion;
import com.example.mislugares2021.R;
import com.example.mislugares2021.casos_uso.CasosUsoLugar;
import com.example.mislugares2021.casos_uso.CasosUsoLugarFecha;
import com.example.mislugares2021.datos.LugaresBDAdapter;
import com.example.mislugares2021.modelo.Lugar;

import java.text.DateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class VistaLugarActivity extends AppCompatActivity {
   final static int RESULTADO_EDITAR = 1;
   final static int RESULTADO_GALERIA = 2;
   final static int RESULTADO_FOTO = 3;

   private ImageView foto;
   private Uri uriUltimaFoto;
   private LugaresBDAdapter lugares;
   private CasosUsoLugar usoLugar;
   private CasosUsoLugarFecha usoLugarFecha;
   private int pos;
   private int _id = -1;
   private Lugar lugar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.vista_lugar);
      Bundle extras = getIntent().getExtras();
      lugares = ((Aplicacion) getApplication()).lugares;
      pos = extras.getInt("pos", 0);
      _id = lugares.getAdaptador().idPosicion(pos);
      usoLugar = new CasosUsoLugar(this, lugares);
      usoLugarFecha = new CasosUsoLugarFecha(this, lugares);
      lugar = lugares.elementoPos(pos);
      foto = findViewById(R.id.foto);
      findViewById(R.id.icono_hora).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugarFecha.cambiarHora(pos); } });
      findViewById(R.id.hora).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugarFecha.cambiarHora(pos); } });

      findViewById(R.id.icono_fecha).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugarFecha.cambiarFecha(pos); } });
      findViewById(R.id.fecha).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugarFecha.cambiarFecha(pos); } });
      actualizaVistas();
   }

   public void actualizaVistas() {
      TextView nombre = findViewById(R.id.nombre);
      nombre.setText(lugar.getNombre());
      ImageView logo_tipo = findViewById(R.id.logo_tipo);
      logo_tipo.setImageResource(lugar.getTipo().getRecurso());
      TextView tipo = findViewById(R.id.tipo);
      tipo.setText(lugar.getTipo().getTexto());
      TextView direccion = findViewById(R.id.direccion);
      direccion.setText(lugar.getDireccion());
      TextView telefono = findViewById(R.id.telefono);
      telefono.setText(Integer.toString(lugar.getTelefono()));
      TextView url = findViewById(R.id.url);
      url.setText(lugar.getUrl());
      TextView comentario = findViewById(R.id.comentario);
      comentario.setText(lugar.getComentario());
      TextView fecha = findViewById(R.id.fecha);
      fecha.setText(DateFormat.getDateInstance().format(
              new Date(lugar.getFecha())));
      TextView hora = findViewById(R.id.hora);
      hora.setText(DateFormat.getTimeInstance().format(
              new Date(lugar.getFecha())));
      RatingBar valoracion = findViewById(R.id.valoracion);
      valoracion.setOnRatingBarChangeListener(null);
      valoracion.setRating(lugar.getValoracion());
      valoracion.setOnRatingBarChangeListener(
              new RatingBar.OnRatingBarChangeListener() {
                 @Override
                 public void onRatingChanged(RatingBar ratingBar,
                                             float valor, boolean fromUser) {
                    lugar.setValoracion(valor);
                    pos = lugares.actualizaPosLugar(pos, lugar);
                 }
              });

      usoLugar.visualizarFoto(lugar, foto);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.vista_lugar, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.accion_compartir:
            usoLugar.compartir(lugar);
            return true;
         case R.id.accion_llegar:
            usoLugar.verMapa(lugar);
            return true;
         case R.id.accion_editar:
            usoLugar.editar(pos, RESULTADO_EDITAR);
            return true;
         case R.id.accion_borrar:
            usoLugar.borrarPos(pos);
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode,
                                   Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == RESULTADO_EDITAR) {
         lugar = lugares.elemento(_id);
         actualizaVistas();
      } else if (requestCode == RESULTADO_GALERIA) {
         if (resultCode == Activity.RESULT_OK) {
            usoLugar.ponerFoto(pos, data.getDataString(), foto);
         } else {
            Toast.makeText(this, "Foto no cargada", Toast.LENGTH_LONG).show();
         }
      } else if (requestCode == RESULTADO_FOTO) {
         if (resultCode == Activity.RESULT_OK && uriUltimaFoto != null) {
            lugar.setFoto(uriUltimaFoto.toString());
            usoLugar.ponerFoto(pos, lugar.getFoto(), foto);
         } else {
            Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
         }
      }
   }

   public void verMapa(View view) {
      usoLugar.verMapa(lugar);
   }

   public void llamarTelefono(View view) {
      usoLugar.llamarTelefono(lugar);
   }

   public void verPgWeb(View view) {
      usoLugar.verPgWeb(lugar);
   }

   public void ponerDeGaleria(View view) {
      usoLugar.ponerDeGaleria(RESULTADO_GALERIA);
   }

   public void tomarFoto(View view) {
      uriUltimaFoto = usoLugar.tomarFoto(RESULTADO_FOTO);
   }

   public void eliminarFoto(View view) {
      usoLugar.ponerFoto(pos, "", foto);
   }

}
