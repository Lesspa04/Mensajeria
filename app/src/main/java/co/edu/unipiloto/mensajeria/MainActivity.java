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

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mensajesCliente = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mensajesListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mensajesCliente);
        listView.setAdapter(adapter);

        cargarHistorial();
    }
    public void respuesta(View view) {
        EditText editText = findViewById(R.id.mensaje);
        String mensajeTexto = editText.getText().toString();

        if (!mensajeTexto.isEmpty()) {
            mensajesCliente.add("Cliente: " + mensajeTexto);
            adapter.notifyDataSetChanged();
            guardarHistorial();
            editText.setText("");
        }
    }
    private void guardarHistorial() {
        SharedPreferences sharedPreferences = getSharedPreferences("chat", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mensajes", String.join(",", mensajesCliente));
        editor.apply();
    }
    private void cargarHistorial() {
        SharedPreferences sharedPreferences = getSharedPreferences("chat", MODE_PRIVATE);
        String mensajesGuardados = sharedPreferences.getString("mensajes", "");
        if (!mensajesGuardados.isEmpty()) {
            String[] mensajesArray = mensajesGuardados.split(",");
            for (String mensaje : mensajesArray) {
                mensajesCliente.add(mensaje);
            }
        }
    }
    public void abrirEmpleado(View view) {
        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        startActivity(intent);
    }

}
