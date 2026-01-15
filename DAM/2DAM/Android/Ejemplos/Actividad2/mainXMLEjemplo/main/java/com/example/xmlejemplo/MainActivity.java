package com.example.xmlejemplo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<ini>" +
                "<item atr=\"atr1\">" +
                "<id>1</id>" +
                "<titulo>titulo1</titulo >" +
                "<desc>descripcion1</desc>" +
                "</item>" +
                "<item atr=\"atr2\">" +
                "<id>2</id>" +
                "<titulo>titulo2</titulo >" +
                "<desc>descripcion2</desc>" +
                "</item>" +
                "</ini>";

        Document document = convertirStringToXMLDocument(xml);
        NodeList listaItem = document.getElementsByTagName("item");
        ArrayList arrayList = new ArrayList<Item>();
        for (int i = 0; i < listaItem.getLength(); i++) {
            Element element = (Element) listaItem.item(i);
            String var_atr =  element.getAttribute("atr");
            String var_id =  element.getElementsByTagName("id").item(0).getTextContent();
            String var_titulo =  element.getElementsByTagName("titulo").item(0).getTextContent();
            String var_desc =  element.getElementsByTagName("desc").item(0).getTextContent();
            Item item = new Item(var_id, var_titulo, var_desc, var_atr);
            arrayList.add(item);
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter <String>
                (this, R.layout.support_simple_spinner_dropdown_item,arrayList);
        ListView listView = (ListView) findViewById(R.id.idListView);
        listView.setAdapter(adaptador);

    }

    private static Document convertirStringToXMLDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder ;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Item {
        String id;
        String titulo;
        String desc;
        String atr;

        public Item(String id, String titulo, String desc, String atr) {
            this.id = id;
            this.titulo = titulo;
            this.desc = desc;
            this.atr = atr;
        }

        @NonNull
        @Override
        public String toString() {
            return "Atributo: "+ atr + " ID: "+this.id + " Título: " + this.titulo + " Descripción: " + this.desc;
        }
    }
}