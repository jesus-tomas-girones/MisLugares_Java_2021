package com.example.mislugares2021.casos_uso;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mislugares2021.R;
import com.example.mislugares2021.datos.LugaresBDAdapter;
import com.example.mislugares2021.modelo.Lugar;
import com.example.mislugares2021.presentacion.DialogoSelectorFecha;
import com.example.mislugares2021.presentacion.DialogoSelectorHora;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class CasosUsoLugarFecha implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

   protected AppCompatActivity actividad;
   protected LugaresBDAdapter lugares;
   private int pos =-1;
   private Lugar lugar;

   public CasosUsoLugarFecha(AppCompatActivity actividad, LugaresBDAdapter lugares){
      this.actividad = actividad;
      this.lugares = lugares;
   }

   public void cambiarHora(int pos) {
      lugar = lugares.elementoPos(pos);
      this.pos = pos;
      DialogoSelectorHora dialogo = new DialogoSelectorHora();
      dialogo.setOnTimeSetListener(this);
      Bundle args = new Bundle();
      args.putLong("fecha", lugar.getFecha());
      dialogo.setArguments(args);
      dialogo.show(actividad.getSupportFragmentManager(), "selectorHora");
   }

   @Override public void onTimeSet(TimePicker vista, int hora, int minuto) {
      Calendar calendario = Calendar.getInstance();
      calendario.setTimeInMillis(lugar.getFecha());
      calendario.set(Calendar.HOUR_OF_DAY, hora);
      calendario.set(Calendar.MINUTE, minuto);
      lugar.setFecha(calendario.getTimeInMillis());
      lugares.actualizaPosLugar(pos, lugar);
      TextView textView = actividad.findViewById(R.id.hora);
      textView.setText(DateFormat.getTimeInstance().format(
              new Date(lugar.getFecha())));
   }

   public void cambiarFecha(int pos) {
      lugar = lugares.elementoPos(pos);
      this.pos = pos;
      DialogoSelectorFecha dialogo = new DialogoSelectorFecha();
      dialogo.setOnDateSetListener(this);
      Bundle args = new Bundle();
      args.putLong("fecha", lugar.getFecha());
      dialogo.setArguments(args);
      dialogo.show(actividad.getSupportFragmentManager(),"selectorFecha");
   }

   @Override
   public void onDateSet(DatePicker view, int año, int mes, int dia) {
      Calendar calendario = Calendar.getInstance();
      calendario.setTimeInMillis(lugar.getFecha());
      calendario.set(Calendar.YEAR, año);
      calendario.set(Calendar.MONTH, mes);
      calendario.set(Calendar.DAY_OF_MONTH, dia);
      lugar.setFecha(calendario.getTimeInMillis());
      lugares.actualizaPosLugar(pos, lugar);
      TextView textView = actividad.findViewById(R.id.fecha);
      textView.setText(DateFormat.getDateInstance().format(
              new Date(lugar.getFecha())));
   }

}
