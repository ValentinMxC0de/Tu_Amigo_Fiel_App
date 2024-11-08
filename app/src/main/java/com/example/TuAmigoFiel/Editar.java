package com.example.TuAmigoFiel;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Editar extends AppCompatActivity {

    private EditText ed_nombreProducto, ed_marca, ed_precio, ed_categoria, ed_descripcion, ed_id;
    private Button b_editar, b_eliminar, b_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ed_nombreProducto = findViewById(R.id.et_nombreProducto);
        ed_marca = findViewById(R.id.et_marca);
        ed_precio = findViewById(R.id.et_precio);
        ed_categoria = findViewById(R.id.et_categoria);
        ed_descripcion = findViewById(R.id.et_descripcion);
        ed_id = findViewById(R.id.id);

        b_editar = findViewById(R.id.btn_editar);
        b_eliminar = findViewById(R.id.btn_eliminar);
        b_volver = findViewById(R.id.btn_volver);

        Intent i = getIntent();

        String et_id = i.getStringExtra("id").toString();
        String et_nombreProducto = i.getStringExtra("nombre_producto").toString();
        String et_marca = i.getStringExtra("marca").toString();
        String et_precio = i.getStringExtra("precio").toString();
        String et_categoria = i.getStringExtra("categoria").toString();
        String et_descripcion = i.getStringExtra("descripcion").toString();

        ed_id.setText(et_id);
        ed_nombreProducto.setText(et_nombreProducto);
        ed_marca.setText(et_marca);
        ed_precio.setText(et_precio);
        ed_categoria.setText(et_categoria);
        ed_descripcion.setText(et_descripcion);

        b_editar.setOnClickListener(view -> editar());

        b_eliminar.setOnClickListener(view -> eliminar());

        b_volver.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Leer.class);
            startActivity(intent);
        });
    }

    public void eliminar() {
        try {
            String id = ed_id.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_TUAMIGOFIEL", Context.MODE_PRIVATE, null);
            String sql = "delete from producto_mascota where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, id);
            statement.execute();

            Toast.makeText(this, "Datos eliminados de la base de datos.", Toast.LENGTH_LONG).show();

            ed_nombreProducto.setText("");
            ed_marca.setText("");
            ed_precio.setText("");
            ed_categoria.setText("");
            ed_descripcion.setText("");
            ed_nombreProducto.requestFocus();

        } catch (Exception ex) {
            Toast.makeText(this, "Error, no se pudieron eliminar los datos.", Toast.LENGTH_LONG).show();
        }
    }

    public void editar() {
        try {
            String nombreProducto = ed_nombreProducto.getText().toString();
            String marca = ed_marca.getText().toString();
            String precio = ed_precio.getText().toString();
            String categoria = ed_categoria.getText().toString();
            String descripcion = ed_descripcion.getText().toString();
            String id = ed_id.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_TUAMIGOFIEL", Context.MODE_PRIVATE, null);
            String sql = "update producto_mascota set nombre_producto = ?, marca = ?, precio = ?, categoria = ?, descripcion = ? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, nombreProducto);
            statement.bindString(2, marca);
            statement.bindString(3, precio);
            statement.bindString(4, categoria);
            statement.bindString(5, descripcion);
            statement.bindString(6, id);
            statement.execute();

            Toast.makeText(this, "Datos actualizados satisfactoriamente en la base de datos.", Toast.LENGTH_LONG).show();

            ed_nombreProducto.setText("");
            ed_marca.setText("");
            ed_precio.setText("");
            ed_categoria.setText("");
            ed_descripcion.setText("");
            ed_nombreProducto.requestFocus();

        } catch (Exception ex) {
            Toast.makeText(this, "Error, no se pudieron actualizar los datos.", Toast.LENGTH_LONG).show();
        }
    }
}