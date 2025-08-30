package co.edu.unipiloto.mensajeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReceiveMessageActivity extends AppCompatActivity {

    private ArrayList<String> mensajesEmpleado = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        listView = findViewById(R.id.mensajesListViewEmpleado);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mensajesEmpleado);
        listView.setAdapter(adapter);

        cargarHistorial();

        Intent intent = getIntent();
        String mensajeCliente = intent.getStringExtra("mensajeCliente");
        if (mensajeCliente != null) {
            mensajesEmpleado.add("Cliente: " + mensajeCliente);
            adapter.notifyDataSetChanged();
        }
    }
    public void responderMensaje(View view) {
        EditText editText = findViewById(R.id.mensajeEmpleado);
        String mensajeTexto = editText.getText().toString();

        if (!mensajeTexto.isEmpty()) {
            mensajesEmpleado.add("Empleado: " + mensajeTexto);
            adapter.notifyDataSetChanged();
            guardarHistorial();
            editText.setText("");
        }
    }

    private void guardarHistorial() {
        SharedPreferences sharedPreferences = getSharedPreferences("chat", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mensajes", String.join(",", mensajesEmpleado));
        editor.apply();
    }
    private void cargarHistorial() {
        SharedPreferences sharedPreferences = getSharedPreferences("chat", MODE_PRIVATE);
        String mensajesGuardados = sharedPreferences.getString("mensajes", "");
        if (!mensajesGuardados.isEmpty()) {
            String[] mensajesArray = mensajesGuardados.split(",");
            for (String mensaje : mensajesArray) {
                mensajesEmpleado.add(mensaje);
            }
        }
    }
    public void abrirCliente(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

