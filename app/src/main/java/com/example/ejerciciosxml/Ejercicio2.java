package com.example.ejerciciosxml;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Ejercicio2 extends AppCompatActivity {

    public String url = "https://www.aemet.es/xml/municipios/localidad_01059.xml";
    private List<Tiempo> tiempos;
    private ListView listaTiempos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        cargarConSAXSimplificado();

        listaTiempos = findViewById(R.id.listaDias);
    }

    public void cargarConSAXSimplificado(){
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute(url);
    }

    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            RssParserSAXSimplificadoTiempo saxparserSimplificado =
                    new RssParserSAXSimplificadoTiempo(params[0]);
            tiempos = saxparserSimplificado.parse();
            return true;
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(Boolean result) {
            Tiempo[] datos = new Tiempo[tiempos.size()];
            for (int i = 0; i < tiempos.size(); i++) {
                datos[i] = tiempos.get(i);
            }
            AdaptadorTiempos adaptadorTiempos = new AdaptadorTiempos(getApplicationContext(), datos);
            listaTiempos.setAdapter(adaptadorTiempos);
        }
    }

    private static class AdaptadorTiempos extends ArrayAdapter<Tiempo> {

        private final Tiempo[] datos;

        public AdaptadorTiempos(Context context, Tiempo[] datos) {
            super(context, R.layout.listitem_tiempo, datos);
            this.datos = datos;
        }

        @SuppressLint("SetTextI18n")
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            @SuppressLint({"InflateParams", "ViewHolder"}) View item = inflater.inflate(R.layout.listitem_tiempo, null);

            String hora = datos[position].getHora().trim();
            String temperatura = datos[position].getTemperatura().trim();
            String dia = datos[position].getDia().trim();
            TextView txtTitulo = item.findViewById(R.id.titulo);
            txtTitulo.setText("Temperatura del " + dia + " a las " + hora + ":00 " + temperatura + "ºC");

            return item;
        }
    }
}