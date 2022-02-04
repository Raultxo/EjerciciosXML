package com.example.ejerciciosxml;

import android.sax.Element;
import android.sax.RootElement;
import android.util.Xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssParserSAXSimplificadoTiempo {

    private final URL rssUrl;
    private Tiempo tiempoActual;
    public RssParserSAXSimplificadoTiempo(String url) {
        try {
            this.rssUrl = new URL (url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tiempo> parse(){
        final String[] diaString = {""};
        final List<Tiempo> tiempos = new ArrayList<>();
        RootElement root = new RootElement("root");
        Element prediccion = root.getChild("prediccion");
        final Element[] dia = {prediccion.getChild("dia")};
        dia[0].setStartElementListener(attributes -> diaString[0] = attributes.getValue(0));
        Element temperatura = dia[0].getChild("temperatura");
        Element dato = temperatura.getChild("dato");
        dato.setStartElementListener(attributes -> {
            tiempoActual = new Tiempo();
            tiempoActual.setHora(attributes.getValue(0));
            tiempoActual.setDia(diaString[0]);
        });
        dato.setEndTextElementListener(body -> {
            tiempoActual.setTemperatura(body);
            tiempos.add(tiempoActual);
        });

        try {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.ISO_8859_1,
                    root.getContentHandler());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return tiempos;
    }

    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
