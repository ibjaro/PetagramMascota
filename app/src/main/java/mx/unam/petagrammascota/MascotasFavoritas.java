package mx.unam.petagrammascota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MascotasFavoritas extends AppCompatActivity {

    ArrayList<Mascota> mascotasF,mascotasR,mascotaharcodeada;
    RecyclerView listaMascotasF;
    int cantidad=0,fot,li;
    String nomb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas_favoritas);

        //Agregamos la barra personalizada a esta activity
        Toolbar miActionbarF = (Toolbar) findViewById(R.id.miActionBarF);
        setSupportActionBar(miActionbarF);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        listaMascotasF = (RecyclerView) findViewById(R.id.rvMascotasF);
        //mostraremos la lista de mascotas en forma de Linearlayout
        LinearLayoutManager llmf = new LinearLayoutManager(this);
        llmf.setOrientation(LinearLayoutManager.VERTICAL);

        listaMascotasF.setLayoutManager(llmf);
        //hacemos el llamado al metodo inicializarListaMascotas
        inicializarListaMascotasF();
        //hacemos el llamado al metodo inicializarAdaptador
        inicializarAdaptadorF();


    }

    public void inicializarAdaptadorF()
    {
        //creamos el adaptador y le pasamos el arreglo de mascotas favoritas, en que activity estamos
        //y que numero es para saber en la clase MascotaAdaptador como mostrara los datos y si seran clicables o no
        MascotaAdaptador adaptador = new MascotaAdaptador(mascotasF, this,2);
        listaMascotasF.setAdapter(adaptador);

    }

    public void inicializarListaMascotasF()
    { //inicializamos un arreglo para las mascotas favoritas(mascotasF) y mascotas recibidas(mascotasR)
       mascotasF = new ArrayList<>();
       mascotasR = new ArrayList<>();
       mascotaharcodeada = new ArrayList<>();
       //creamos un bundle que recibira el arreglo de objetos enviado por la activity anterior
       Bundle objetoBundleMascotas = getIntent().getExtras();
       //preguntamos si este objeto viene con datos o esta vacio
       if(objetoBundleMascotas!=null)
       {//si hay datos estos se los agregamos a un ArrayList de mascotas favorias
           mascotasR = (ArrayList<Mascota>) objetoBundleMascotas.getSerializable("listamascotas");
           for(int i=0; i<mascotasR.size(); i++)
           {
               if(mascotasR.get(i).getCantidad_like()>0)
               {
                   mascotasF.add(mascotasR.get(i));
               }else{
                      mascotaharcodeada.add(mascotasR.get(i));
                      cantidad = cantidad+1;
                    }
           }
           //preguntamos si algun objeto no tenia Like
           if(cantidad>0)
           {//for para llenar con datoslas marcotas que no tienen like, hardcodear para asi poder mostar 5 mascotas.
               for(int j=0;j<mascotaharcodeada.size();j++)
               {
                   fot = mascotaharcodeada.get(j).getFoto();
                   nomb = mascotaharcodeada.get(j).getNombre_mascota();
                   li = mascotaharcodeada.get(j).getCantidad_like()+7+j;
                   mascotasF.add(new Mascota(fot,nomb,li));
               }
           }

       }
    }

    public void datosBundleIrAtras()
    {
        //creamos un bundle que enviara el arreglo de objetos a la activity gerarquica atras
        Bundle envioBundleDatosAtras = getIntent().getExtras();
        //el arreglo de objetos le enviamos de manera Serializable ya que en la clase Mascota esta el implemente Serializable
        envioBundleDatosAtras.putSerializable("listamascotasatras",mascotasR);
        //hacemos un intent para indicar que enviaremos datos desde esta activity hacia la siguiente
        Intent intentatras = new Intent(this, MainActivity.class);
        intentatras.putExtras(envioBundleDatosAtras);
        //iniciamos el intent
        startActivity(intentatras);
        finish();//finalizamos( cerramos ) la activity( MascotasFavoritas ), para liberar memoria
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Si el boton de atras se selecciona se llamara a la funcion datosBundleIrAtras()
        datosBundleIrAtras();
        return super.onSupportNavigateUp();
    }
}