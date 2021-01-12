package com.example.mislugares2021.presentacion;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mislugares2021.Aplicacion;
import com.example.mislugares2021.R;
import com.example.mislugares2021.casos_uso.CasosUsoLugar;
import com.example.mislugares2021.datos.LugaresBDAdapter;
import com.example.mislugares2021.modelo.Lugar;
import com.example.mislugares2021.modelo.TipoLugar;

import androidx.appcompat.app.AppCompatActivity;

public class EdicionLugarActivity extends AppCompatActivity {
   private LugaresBDAdapter lugares;
   private CasosUsoLugar usoLugar;
   private int pos;
   private int _id;
   private Lugar lugar;

   private EditText nombre;
   private Spinner tipo;
   private EditText direccion;
   private EditText telefono;
   private EditText url;
   private EditText comentario;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.edicion_lugar);
      Bundle extras = getIntent().getExtras();
      lugares = ((Aplicacion) getApplication()).lugares;
      usoLugar = new CasosUsoLugar(this, lugares);

      pos = extras.getInt("pos", -1);
      _id = extras.getInt("_id", -1);
      if (_id!=-1) lugar = lugares.elemento(_id);
      else         lugar = lugares.elementoPos(pos);
      actualizaVistas();
   }

   public void actualizaVistas() {
      nombre = findViewById(R.id.nombre);
      nombre.setText(lugar.getNombre());
      direccion = findViewById(R.id.direccion);
      direccion.setText(lugar.getDireccion());
      telefono = findViewById(R.id.telefono);
      telefono.setText(Integer.toString(lugar.getTelefono()));
      url = findViewById(R.id.url);
      url.setText(lugar.getUrl());
      comentario = findViewById(R.id.comentario);
      comentario.setText(lugar.getComentario());
      tipo = findViewById(R.id.tipo);
      ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
              android.R.layout.simple_spinner_item, TipoLugar.getNombres());
      adaptador.setDropDownViewResource(android.R.layout.
              simple_spinner_dropdown_item);
      tipo.setAdapter(adaptador);
      tipo.setSelection(lugar.getTipo().ordinal());

   }
   @Override public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.edicion_lugar, menu);
      return true;
   }

   @Override public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.accion_cancelar:
            finish();
            return true;
         case R.id.accion_guardar:
            lugar.setNombre(nombre.getText().toString());
            lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
            lugar.setDireccion(direccion.getText().toString());
            lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
            lugar.setUrl(url.getText().toString());
            lugar.setComentario(comentario.getText().toString());
            if (_id==-1) _id = lugares.getAdaptador().idPosicion(pos);
            usoLugar.guardar(_id, lugar);
            finish();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }
}