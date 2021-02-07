package com.example.proyecto_android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.dao.VisitamService;
import com.example.proyecto_android.model.Monumento;
import com.firebase.ui.auth.AuthUI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.security.Provider;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaMonumentosAdapter extends RecyclerView.Adapter<ListaMonumentosAdapter.ViewHolder> {

    private List<Monumento> monumentos;
    private int layout;
    private OnItemClickListener itemClickListener;
    VisitamService service;
    private Context context;
    private TextView textViewName;

    public ListaMonumentosAdapter(List<Monumento> monumentos, int layout, OnItemClickListener itemClickListener) {
        this.monumentos = monumentos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewMonumento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textCardView);
            imageViewMonumento = (ImageView) itemView.findViewById(R.id.imageCardView);
        }

        public void bind(final Monumento monumento, final OnItemClickListener listener){


            //textViewName.setText(monumento.getName());
            Picasso.with(context).load(monumento.getImagen()).fit().into(imageViewMonumento);
            if(monumento.getImagen() != null){
                int resId = context.getResources().getIdentifier(monumento.getImagen(), "drawable", context.getPackageName());
                imageViewMonumento.setImageResource(resId);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(monumento, getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ListaMonumentosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        getMonumentos();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaMonumentosAdapter.ViewHolder holder, int position) {
        holder.bind(monumentos.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return monumentos.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Monumento monumento, int position);
    }

    private void getMonumentos(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://mapas.valencia.es/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();

        service = retrofit.create(VisitamService.class);
        Call<List<Monumento>> call = service.getMonumentos();
        call.enqueue(new Callback<List<Monumento>>() {
            @Override
            public void onResponse(Call<List<Monumento>> call, Response<List<Monumento>> response) {
                if (!response.isSuccessful()){
                    textViewName.setText("Codigo: " + response.code());
                    return;
                }

                List<Monumento> monumentos = response.body();
                for(Monumento monumento : monumentos){
                    String content = "";
                    content += monumento.getName();

                    textViewName.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Monumento>> call, Throwable t) {
                textViewName.setText(t.getMessage());
                Log.i("******************", t.toString());
            }
        });
    }
}
