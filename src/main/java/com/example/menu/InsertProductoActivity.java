package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menu.db.DbProducto;

public class InsertProductoActivity extends AppCompatActivity {

    EditText txtNombre;
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_producto);

        txtNombre = findViewById(R.id.txtNombre);
        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbProducto dbProducto = new DbProducto(InsertProductoActivity.this);
                long id = dbProducto.insertProducto(txtNombre.getText().toString());

                if(id>0){
                    Toast.makeText(InsertProductoActivity.this, "PRODUCTO CREADO", Toast.LENGTH_LONG).show();
                    limpiar();
                }else{
                    Toast.makeText(InsertProductoActivity.this, "ERROR AL CREAR PRODUCTO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar(){
        txtNombre.setText("");
    }
}