package com.example.apps1t.diario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Creamos las variables de la aplicacion
    ArrayList<String> diario; // El array de las entradas del diario
    String texto;
    EditText buscador;
    ListView diario1;         //El listView
    int posicion;             //posicoin en el array de las entradas
    ArrayAdapter<String> adapterDiario;
    SharedPreferences sharedPreferences;
    private Context text;
    /*

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
        //buscamos el listview del layaut por id
        diario1 =findViewById(R.id.diario1);
        buscador = findViewById(R.id.buscador);
        //cCargamos la lista para que se mantengan los datos
        diario = cargarLista();
        //Usamos el adaptador de array y lo mostramos en la aplicacion
        adapterDiario = new ArrayAdapter<String>(this,R.layout.list , diario);
        diario1.setAdapter(adapterDiario);
        text =this;
        /*
        Utilizamos el setOnItemClickListener para poder editar los datos de las entradas del diario
         */
        diario1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int posicionn, long id) {
                //igualamos la posicion para poder cambiar la entrada en un futuro
                posicion = posicionn;
                Intent intent = new Intent(text, Main2Activity.class);

                //Guardamos el texto para la otra pantalla
                String  cogerText = diario.get(posicion);
                //comprobamos que este texto no esta vacio y usamos el intent para llevarlo a la otra pantalla
                if(!cogerText.isEmpty()){
                    intent.putExtra("aparecer", cogerText);
                    startActivityForResult(intent, 2);
                }
            }
        });
        /*
        Usamos el setOnItemLongClickListener para cuando mantengamos pulsado en el listview se borre la entrada selecionada
         */
        diario1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                diario.remove(position);
                adapterDiario.notifyDataSetChanged();
                guardarLista(diario);
                return true;
            }
        });
    }
    /*
    Este metodo realiza la accion de comprobar que accion tiene que realizar si añadir o editar en funcion de requestCode que se reciba
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //En este caso recibe el requestCode 1 lo que implica que se quiere añadir una entrada al diario
        if (requestCode == 1) {
            if(resultCode == MainActivity.RESULT_OK){
                texto = data.getStringExtra("resultado");
                //mandamos el texto y el arraylist al metodo de añardir para que se añada al array
                arrayAnadir(diario, texto);
            }
        }
        //En este caso recibe el requestCode 2 lo que implica que se quiere editar una entrada al diario
        if(requestCode ==2){
            if(resultCode ==MainActivity.RESULT_OK){
                String editarEntrada = data.getStringExtra("resultado");
                //cambiamos la entrada del diario y con el adapter notificamos que este ha cambiado
                diario.set(posicion, editarEntrada);
                adapterDiario.notifyDataSetChanged();
            }
        }
        //llamamos al metodo para guardar el array
        guardarLista(diario);
    }
    /*
    Este metodo lo usamos para hacer el cambio a la otra pantalla cuando le demos al boton de añadir
     */
    public void Cambio(View view){
        Button boton =(Button) findViewById(R.id.button);
        if(boton.isClickable()){
            Intent intent = new Intent(this, Main2Activity.class);
            startActivityForResult(intent,1);
        }
    }
    /*
    este metodo es el que usamos para añadir los datos al array
     */
    private void arrayAnadir(ArrayList<String> diario, String texto){
        if(texto.isEmpty()){
            return;
        }
        diario.add(texto);
        adapterDiario.notifyDataSetChanged();
    }
    /*
    este metodo es el que usamos para guardar los datos del array con el sharedPreferences
     */
    private void guardarLista(ArrayList<String> textos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < textos.size(); i++) {
            editor.putString("text" + i, textos.get(i));
        }
        editor.putInt("longitud", textos.size()); // Guardar el tamaño de la lista
        editor.commit();
    }
    /*
    este metodo es el que usamos para cargar los datos del array con el sharedPreferences
     */
    private ArrayList<String> cargarLista() {
        ArrayList<String> textos = new ArrayList<>();
        // Obtener el tamaño de la lista
        int longitud = sharedPreferences.getInt("longitud", 0);
        // Obtener todos los textos
        for (int i = 0; i < longitud; i++) {
            String texto = sharedPreferences.getString("text" + i, "");
            textos.add(texto);
        }
        return textos;
    }
}
