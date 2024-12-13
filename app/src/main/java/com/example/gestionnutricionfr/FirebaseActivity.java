package com.example.gestionnutricionfr;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity {

    private EditText txtNombrePaciente, txtDireccion, txtRutpaciente;
    private ListView lista;
    private Spinner spinnerGenero;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        CargarListaFirestore();
        db = FirebaseFirestore.getInstance();
        txtRutpaciente = findViewById(R.id.txtRutPaciente);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtNombrePaciente = findViewById(R.id.txtNombrePaciente);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        lista = findViewById(R.id.lista);

    }
    public void enviarDatosFirebase(View view){
        String rut = txtRutpaciente.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String nombre = txtNombrePaciente.getText().toString();
        String genero = spinnerGenero.getSelectedItem().toString();

        Map<String, Object> paciente = new HashMap<>();
        paciente.put("rut", rut);
        paciente.put("direccion", direccion);
        paciente.put("nombre", nombre);
        paciente.put("genero", genero);

        db.collection("pacientes")
                .document(rut)
                .set(paciente)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseActivity.this, "Datos guardados con exito", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseActivity.this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void CargarLista(View view){
        CargarListaFirestore();

    }
    public void CargarListaFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("pacientes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> listaPacientes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String linea = "|| " + document.getString("rut") + " || " + document.getString("nombre") + " || " + document.getString("direccion") + " || " + document.getString("genero") + " ||";
                                listaPacientes.add(linea);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(FirebaseActivity.this, android.R.layout.simple_list_item_1, listaPacientes);
                            lista.setAdapter(adapter);
                        } else {
                            Log.e("TAG,", "Error al obtener los pacientes", task.getException());
                        }
                    }
                });

    }

}
