package com.example.pt8_bbdd_online_silva_raul;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText etMatricula, etNom, etCognoms, etTelefon, etMarca, etModel;
    private DatabaseReference dbParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String urlBBDD = "https://pt8-bbdd-online-silva-raul-default-rtdb.europe-west1.firebasedatabase.app";
        dbParking = FirebaseDatabase.getInstance(urlBBDD).getReference().child("parking");

        etMatricula = findViewById(R.id.etMatricula);
        etNom = findViewById(R.id.etNom);
        etCognoms = findViewById(R.id.etCognoms);
        etTelefon = findViewById(R.id.etTelefon);
        etMarca = findViewById(R.id.etMarca);
        etModel = findViewById(R.id.etModel);

        findViewById(R.id.btnAlta).setOnClickListener(v -> {
            String mat = etMatricula.getText().toString().trim();
            if (mat.isEmpty()) return;

            dbParking.child(mat).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(MainActivity.this, "Error: Matrícula duplicada", Toast.LENGTH_SHORT).show();
                    } else {
                        procesarGuardado(mat);
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {}
            });
        });

        findViewById(R.id.btnConsulta).setOnClickListener(v -> {
            String mat = etMatricula.getText().toString().trim();
            if (mat.isEmpty()) return;

            dbParking.child(mat).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    Vehicle veh = task.getResult().getValue(Vehicle.class);
                    if (veh != null) {
                        etNom.setText(veh.getNom());
                        etCognoms.setText(veh.getCognoms());
                        etTelefon.setText(veh.getTelefon());
                        etMarca.setText(veh.getMarca());
                        etModel.setText(veh.getModel());
                    }
                } else {
                    Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
                }
            });
        });

        findViewById(R.id.btnModificar).setOnClickListener(v -> {
            String mat = etMatricula.getText().toString().trim();
            if (!mat.isEmpty()) procesarGuardado(mat);
        });

        findViewById(R.id.btnBaixa).setOnClickListener(v -> {
            String mat = etMatricula.getText().toString().trim();
            if (!mat.isEmpty()) {
                dbParking.child(mat).removeValue();
                etMatricula.setText("");
                etNom.setText("");
                etCognoms.setText("");
                etTelefon.setText("");
                etMarca.setText("");
                etModel.setText("");
            }
        });
    }

    private void procesarGuardado(String mat) {
        Vehicle v = new Vehicle(mat, etNom.getText().toString(), etCognoms.getText().toString(),
                etTelefon.getText().toString(), etMarca.getText().toString(), etModel.getText().toString());
        dbParking.child(mat).setValue(v);
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
    }
}