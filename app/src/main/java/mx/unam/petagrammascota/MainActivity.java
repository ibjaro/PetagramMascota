package mx.unam.petagrammascota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Mascota> mascotas;
    RecyclerView listaMascotas;
    String li;
    private int validarBundle=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Agregamos la barra personalizada a esta activity
        Toolbar miActionbar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionbar);

        listaMascotas = (RecyclerView) findViewById(R.id.rvMascotas);
        //mostraremos la lista de mascotas en forma de Linearlayout
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listaMascotas.setLayoutManager(llm);

        //verificamos si hay datos enviados por otra activity
        datosBundle();
        //si validarBundle es igual a 1, quiere decir que No se han recibido datos de otra activity
        if(validarBundle==1)
        {
            //hacemos el llamado al metodo inicializarListaMascotas
            inicializarListaMascotas();
        }

        //hacemos el llamado al metodo inicializarAdaptador
        inicializarAdaptador();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuFavoritos)
        {
            //creamos un bundle que enviara el arreglo de objetos a la activity siguiente
            Bundle envioBundleDatos = new Bundle();
            //el arreglo de objetos le enviamos de manera Serializable ya que en la clase Mascota esta el implemente Serializable
            envioBundleDatos.putSerializable("listamascotas",mascotas);
            //hacemos un intent para indicar que enviaremos datos desde esta activity hacia la siguiente
            Intent intent = new Intent(this, MascotasFavoritas.class);
            li = String.valueOf(mascotas.get(1).getCantidad_like());
            Toast.makeText(this, li, Toast.LENGTH_LONG).show();
            //en el intent le colocamos el arreglo de objetos creados con Bundle
            intent.putExtras(envioBundleDatos);
           //iniciamos el intent
            startActivity(intent);
            finish();//finalizamos( cerramos ) la activity( MainActivity ), para liberar memoria
        }
        return super.onOptionsItemSelected(item);
    }

    public void inicializarAdaptador()
    {
        //creamos el adaptador y le pasamos el arreglo de mascotas, en que activity estamos
        //y que numero es para saber en la clase MascotaAdaptador como mostrara los datos y si seran clicables o no
        MascotaAdaptador adaptador = new MascotaAdaptador(mascotas, this, 1);
        listaMascotas.setAdapter(adaptador);
    }

    public void inicializarListaMascotas()
    {
        //creamos un arreglo de objetos y le cargamos datos
        mascotas = new ArrayList<>();
        mascotas.add(new Mascota(R.drawable.elefante,"Elefantin",0));
        mascotas.add(new Mascota(R.drawable.conejo,"Conejo",0));
        mascotas.add(new Mascota(R.drawable.tortuga,"Tortuga",0));
        mascotas.add(new Mascota(R.drawable.caballo,"Caballo",0));
        mascotas.add(new Mascota(R.drawable.rana,"Rana",0));
    }

    public void datosBundle()
    {
        //creamos un Bundle para recibir los datos de MascotasFavoritas al presionar el boton de atras en la ActionBar
        Bundle datosBundleAtras = getIntent().getExtras();
        //preguntamos si este objeto viene con datos o esta vacio
        if(datosBundleAtras!=null)
        {//si hay datos estos se los agregamos a un ArrayList de mascotas
            mascotas = (ArrayList<Mascota>) datosBundleAtras.getSerializable("listamascotasatras");
        }else{
               //si el bundle No trae datos entonces se inicializara la lista con datos
               validarBundle=1;
               return;
             }
    }

}