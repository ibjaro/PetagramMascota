package mx.unam.petagrammascota;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.MascotaViewHolder> {

    ArrayList<Mascota> mascotas;
    Activity activity;
    int cantidad=0, usoactividad;
    String Like;

    public MascotaAdaptador(ArrayList<Mascota> mascotas, Activity activity, int usoactividad)
    {
        this.mascotas = mascotas;
        this.activity = activity;
        this.usoactividad = usoactividad;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creamos un View inflado para que maneje nuestro RecyclerView que esta en activity_main.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota,parent,false);
        //le pasamos el view( v, inflado ) a la clase estatica MascotaViewHolder()
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MascotaViewHolder mascotaViewHolderholder, int position) {
        //creamos un elemento contacto para agregarle los datos de el contacto especificado con position
        final Mascota mascota = mascotas.get(position);
        mascotaViewHolderholder.imgvFotoCV.setImageResource(mascota.getFoto());
        mascotaViewHolderholder.txvNombreCV.setText(mascota.getNombre_mascota());
        Like = Integer.toString(mascota.getCantidad_like());
        mascotaViewHolderholder.txvCantidadLikeCV.setText(Like);
        mascotaViewHolderholder.imgvCantidadLikeCV.setImageResource(R.mipmap.huesoamarillo);
        if(usoactividad==1)
        {

            mascotaViewHolderholder.imgbtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //se lee la cantidad de Like de la mascota y suma 1
                    cantidad = mascota.getCantidad_like()+1;
                    mascota.setCantidad_like(cantidad);
                    //se lee la cantidad de Like, se hace una conversion y se agrega a mascotaViewHolderholder
                    Like = Integer.toString(mascota.getCantidad_like());
                    mascotaViewHolderholder.txvCantidadLikeCV.setText(Like);
                }
            });
        }

    }

    @Override
    public int getItemCount()
    {
        return mascotas.size();
    }


    public static class MascotaViewHolder extends RecyclerView.ViewHolder
    {   private ImageView imgvFotoCV;
        private ImageButton imgbtnLike;
        private TextView txvNombreCV;
        private TextView txvCantidadLikeCV;
        private ImageView imgvCantidadLikeCV;


        public MascotaViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imgvFotoCV = (ImageView) itemView.findViewById(R.id.imgvFotoCV);
            imgbtnLike = (ImageButton) itemView.findViewById(R.id.imgbtnLike);
            txvNombreCV = (TextView) itemView.findViewById(R.id.txvNombreCV);
            txvCantidadLikeCV = (TextView) itemView.findViewById(R.id.txvCantidadLikeCV);
            imgvCantidadLikeCV = (ImageView) itemView.findViewById(R.id.imgvCantidadLikeCV);

        }
    }
}
