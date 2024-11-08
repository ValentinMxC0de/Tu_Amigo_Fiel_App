package com.example.TuAmigoFiel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Leer extends AppCompatActivity {

    private ListView lst1;
    private ArrayList<String> arreglo = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        try {
            SQLiteDatabase db = openOrCreateDatabase("BD_TUAMIGOFIEL", Context.MODE_PRIVATE, null);

            lst1 = findViewById(R.id.lst1);
            final Cursor c = db.rawQuery("select * from producto_mascota", null);
            // Obtener los índices de las nuevas columnas
            int id = c.getColumnIndex("id");
            int nombreProducto = c.getColumnIndex("nombre_producto");
            int marca = c.getColumnIndex("marca");
            int precio = c.getColumnIndex("precio");
            int categoria = c.getColumnIndex("categoria");
            int descripcion = c.getColumnIndex("descripcion");

            arreglo.clear();
            arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arreglo);
            lst1.setAdapter(arrayAdapter);

            final ArrayList<ProductoMascota> lista = new ArrayList<ProductoMascota>();

            if (c.moveToFirst()) {
                do {
                    ProductoMascota producto = new ProductoMascota();
                    producto.id = c.getString(id);
                    producto.nombre_producto = c.getString(nombreProducto);
                    producto.marca = c.getString(marca);
                    producto.precio = Double.parseDouble(c.getString(precio));
                    producto.categoria = c.getString(categoria);
                    producto.descripcion = c.getString(descripcion);

                    lista.add(producto);
                    // Ajustar la visualización de los datos en el ListView
                    arreglo.add(c.getString(id) + " \t " + c.getString(nombreProducto) + " \t " + c.getString(marca) + " \t " + c.getString(precio) + " \t " + c.getString(categoria) + " \t " + c.getString(descripcion));

                } while (c.moveToNext());

                arrayAdapter.notifyDataSetChanged();
                lst1.invalidateViews();
            }

            lst1.setOnItemClickListener((adapterView, view, position, l) -> {
                ProductoMascota producto = lista.get(position);

                Intent i = new Intent(getApplicationContext(), Editar.class);
                i.putExtra("id", producto.id);
                i.putExtra("nombre_producto", producto.nombre_producto);
                i.putExtra("marca", producto.marca);
                i.putExtra("precio", String.valueOf(producto.precio));
                i.putExtra("categoria", producto.categoria);
                i.putExtra("descripcion", producto.descripcion);

                startActivity(i);
            });

        } catch (Exception e) {
            Toast.makeText(this, "Ha ocurrido un error, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para abrir la actividad de agregar producto
    public void abrirAgregarProducto(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
