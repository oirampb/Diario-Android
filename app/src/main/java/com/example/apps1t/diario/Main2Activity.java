package com.example.apps1t.diario;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {
    EditText text;
    /*

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //buscamos el editText con el findViewById y lo modificamos en caso de que sea necesario con el intent que hemos creado al pulsar en el listview
        text = findViewById(R.id.diario);
        Intent intent = getIntent();
        //Obtenemos el texto que tenia previamente y lo mostramos en el editText
        String editText =intent.getStringExtra("aparecer");
        text.setText(editText);
    }
    /*
    Este es el metodo que usamos para la ejecucion del boton cancelar
     */
    public void Cancelar(View view){
        Button boton =(Button) findViewById(R.id.cancelar);
        if(boton.isClickable()){
            finish();
        }
    }
    /*
    Este es el metodo que usamos para la ejecucion del boton añadir pasamos el texto del editText a la otra pantalla
     */
    public void PonerTexto(View view){
        Button boton = (Button) findViewById(R.id.anadir);

        String texto = text.getText().toString();
        if(texto.isEmpty()){
            return;
        }
        if(boton.isClickable()) {
            Intent intentResultado = new Intent();
            intentResultado.putExtra("resultado", texto);
            setResult(RESULT_OK, intentResultado);
            finish();
        }
    }
}
