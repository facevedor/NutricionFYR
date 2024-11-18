package com.example.gestionnutricionfr;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CrudActivity extends AppCompatActivity {

    EditText edtRut, edtNom, edtDir;
    Spinner spSpinner;
    ListView lstLista;
    String[] comunas = {"Puente Alto", "Macul", "San Miguel", "Lampa", "La Florida"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        edtRut = findViewById(R.id.edtRut);
        edtNom = findViewById(R.id.edtNom);
        edtDir = findViewById(R.id.edtDir);
        spSpinner = findViewById(R.id.spSpinner);
        lstLista = findViewById(R.id.lstLista);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, comunas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner.setAdapter(spinnerAdapter);

        CargarLista();
    }

    public void CargarLista() {
        DataHelper dh = new DataHelper(this,"alumno.db",null,1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT rut, nombre, direccion, comuna FROM alumno", null);
        String[] arr = new String[c.getCount()];

        if (c.moveToFirst()) {
            int i = 0;
            do {
                String linea = "|| " + c.getInt(0) + " || " + c.getString(1) + " || " + c.getString(2) + " || " + c.getString(3) + " ||";
                arr[i] = linea;
                i++;
            } while (c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        lstLista.setAdapter(adapter);
        bd.close();
    }

    public void onClickAgregar(View view) {
       // DataHelper dh = new DataHelper(this, "alumno.db", null, 1);
        DataHelper dh = new DataHelper(this,"alumno.db",null,1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();

        reg.put("rut", edtRut.getText().toString());
        reg.put("nombre", edtNom.getText().toString());
        reg.put("direccion", edtDir.getText().toString());
        reg.put("comuna", spSpinner.getSelectedItem().toString());

        long resp = bd.insert("alumno", null, reg);
        bd.close();

        if (resp == -1) {
            Toast.makeText(this, "Error al agregar el registro", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registro agregado", Toast.LENGTH_LONG).show();
        }

        CargarLista();
        limpiar();
    }

    public void onClickModificar(View view) {
        DataHelper dh = new DataHelper(this, "alumno.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg = new ContentValues();

        reg.put("nombre", edtNom.getText().toString());
        reg.put("direccion", edtDir.getText().toString());
        reg.put("comuna", spSpinner.getSelectedItem().toString());

        int resp = bd.update("alumno", reg, "rut=?", new String[]{edtRut.getText().toString()});
        bd.close();

        if (resp == 0) {
            Toast.makeText(this, "No se pudo modificar", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registro modificado", Toast.LENGTH_LONG).show();
        }

        CargarLista();
        limpiar();
    }

    public void onClickEliminar(View view) {
        DataHelper dh = new DataHelper(this, "alumno.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        int resp = bd.delete("alumno", "rut=?", new String[]{edtRut.getText().toString()});
        bd.close();

        if (resp == 0) {
            Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registro eliminado", Toast.LENGTH_LONG).show();
        }

        CargarLista();
        limpiar();
    }

    public void limpiar() {
        edtRut.setText("");
        edtNom.setText("");
        edtDir.setText("");
    }
}