package com.example.mislugares2021.presentacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.mislugares2021.Aplicacion;
import com.example.mislugares2021.R;
import com.example.mislugares2021.casos_uso.CasosUsoActividades;
import com.example.mislugares2021.casos_uso.CasosUsoLocalizacion;
import com.example.mislugares2021.casos_uso.CasosUsoLugar;
import com.example.mislugares2021.datos.LugaresBDAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

   private LugaresBDAdapter lugares;
   private CasosUsoLugar usoLugar;
   private CasosUsoActividades usoActividades;
   private RecyclerView recyclerView;
   public AdaptadorLugaresBD adaptador;
   private static final int SOLICITUD_PERMISO_LOCALIZACION = 1;
   private CasosUsoLocalizacion usoLocalizacion;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      lugares = ((Aplicacion) getApplication()).lugares;
      usoLugar = new CasosUsoLugar(this, lugares);
      usoActividades = new CasosUsoActividades(this);
      usoLocalizacion = new CasosUsoLocalizacion(this,
              SOLICITUD_PERMISO_LOCALIZACION);

      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
      toolBarLayout.setTitle(getTitle());

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            usoLugar.nuevo();
         }
      });
      adaptador = ((Aplicacion) getApplication()).adaptador;
      adaptador.setOnItemClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            int pos = (Integer)(v.getTag());
            usoLugar.mostrar(pos);
         }
      });
      recyclerView = findViewById(R.id.recyclerView);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setAdapter(adaptador);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == R.id.action_settings) {
         usoActividades.lanzarPreferencias();
         return true;
      }
      if (id==R.id.menu_mapa) {
         Intent intent = new Intent(this, MapaActivity.class);
         startActivity(intent);
      }
      return super.onOptionsItemSelected(item);
   }

   @Override public void onRequestPermissionsResult(int requestCode,
                                                    String[] permissions, int[] grantResults) {
      if (requestCode == SOLICITUD_PERMISO_LOCALIZACION
              && grantResults.length == 1
              && grantResults[0] == PackageManager.PERMISSION_GRANTED)
         usoLocalizacion.permisoConcedido();
   }
   public void lanzarVistaLugar(View view) {
      final EditText entrada = new EditText(this);
      entrada.setText("0");
      new AlertDialog.Builder(this)
              .setTitle("Selección de lugar")
              .setMessage("indica su id:")
              .setView(entrada)
              .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {
                    int id = Integer.parseInt(entrada.getText().toString());
                    usoLugar.mostrar(id);
                 }
              })
              .setNegativeButton("Cancelar", null)
              .show();
   }

   @Override protected void onResume() {
      super.onResume();
      usoLocalizacion.activar();
   }

   @Override protected void onPause() {
      super.onPause();
      usoLocalizacion.desactivar();
   }

   public void lanzarAcercaDe(View view) {
      usoActividades.lanzarAcercaDe();
   }

   public void lanzarPreferencias(View view) {
      usoActividades.lanzarPreferencias();
   }

   @Override protected void onActivityResult(int requestCode, int resultCode,
                                             Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == CasosUsoActividades.RESULTADO_PREFERENCIAS) {
         adaptador.setCursor(((LugaresBDAdapter) lugares).extraeCursor());
         adaptador.notifyDataSetChanged();
      }
   }
}