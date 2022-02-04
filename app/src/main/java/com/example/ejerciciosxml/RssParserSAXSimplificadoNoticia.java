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

public class RssParserSAXSimplificadoNoticia {

    private final URL rssUrl;
    private Noticia noticiaActual;
    public RssParserSAXSimplificadoNoticia(String url) {
        try {
            this.rssUrl = new URL (url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Noticia> parse(){
        final List<Noticia> noticias = new ArrayList<>();
        RootElement root = new RootElement("rss");
        Element channel = root.getChild("channel");
        Element item = channel.getChild("item");
        item.setStartElementListener(attributes -> noticiaActual = new Noticia());
        item.setEndElementListener(() -> noticias.add(noticiaActual));
        item.getChild("title").setEndTextElementListener(
                body -> noticiaActual.setTitulo(body));
        item.getChild("link").setEndTextElementListener(
                body -> noticiaActual.setLink(body));
        item.getChild("description").setEndTextElementListener(
                body -> noticiaActual.setDescripcion(body));
        item.getChild("guid").setEndTextElementListener(
                body -> noticiaActual.setGuid(body));
        item.getChild("pubDate").setEndTextElementListener(
                body -> noticiaActual.setFecha(body));
        try {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.UTF_8,
                    root.getContentHandler());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return noticias;
    }

    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
