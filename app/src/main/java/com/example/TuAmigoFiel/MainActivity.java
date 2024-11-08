package com.example.TuAmigoFiel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText ed_nombreProducto, ed_marca, ed_precio, ed_categoria, ed_descripcion;
    private Button b_agregar, b_ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_nombreProducto = findViewById(R.id.et_nombreProducto);
        ed_marca = findViewById(R.id.et_marca);
        ed_precio = findViewById(R.id.et_precio);
        ed_categoria = findViewById(R.id.et_categoria);
        ed_descripcion = findViewById(R.id.et_descripcion);

        b_agregar = findViewById(R.id.btn_agregar);
        b_ver = findViewById(R.id.btn_ver);

        b_ver.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Leer.class);
            startActivity(i);
        });

        b_agregar.setOnClickListener(v -> insertar());
    }

    public void insertar() {
        try {
            String nombreProducto = ed_nombreProducto.getText().toString();
            String marca = ed_marca.getText().toString();
            String precio = ed_precio.getText().toString();
            String categoria = ed_categoria.getText().toString();
            String descripcion = ed_descripcion.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_TUAMIGOFIEL", Context.MODE_PRIVATE, null);

            db.execSQL("CREATE TABLE IF NOT EXISTS producto_mascota(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre_producto VARCHAR, marca VARCHAR, precio REAL, categoria VARCHAR, descripcion VARCHAR)");

            String sql = "insert into producto_mascota(nombre_producto, marca, precio, categoria, descripcion) values(?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, nombreProducto);
            statement.bindString(2, marca);
            statement.bindString(3, precio);
            statement.bindString(4, categoria);
            statement.bindString(5, descripcion);

            statement.execute();

            Toast.makeText(this, "Datos agregados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();

            ed_nombreProducto.setText("");
            ed_marca.setText("");
            ed_precio.setText("");
            ed_categoria.setText("");
            ed_descripcion.setText("");
            ed_nombreProducto.requestFocus();

        } catch (Exception ex) {
            Toast.makeText(this, "Error no se pudieron guardar los datos.", Toast.LENGTH_LONG).show();
        }
    }
}