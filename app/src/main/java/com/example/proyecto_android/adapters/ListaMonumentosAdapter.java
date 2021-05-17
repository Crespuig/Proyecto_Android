package com.example.proyecto_android.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.api.moumentos.ApiMonumentosUtils;
import com.example.proyecto_android.api.moumentos.ApiMonumetosService;
import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaMonumentosAdapter extends RecyclerView.Adapter<ListaMonumentosAdapter.ViewHolder> {

    private List<Monumento> monumentos;
    private Usuario usuario;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;
    private TextView textViewName;

    private List<Monumento> originalMonuments;
    private ApiMonumetosService apiMonumetosService;


    public ListaMonumentosAdapter(List<Monumento> monumentos, Usuario usuario, int layout, OnItemClickListener itemClickListener) {
        this.monumentos = monumentos;
        this.usuario = usuario;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.originalMonuments = new ArrayList<>();
        originalMonuments.addAll(monumentos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewMonumento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textCardView);
            imageViewMonumento = (ImageView) itemView.findViewById(R.id.imageCardView);
            apiMonumetosService = ApiMonumentosUtils.getClient();
        }

        public void bind(final Monumento monumento, final OnItemClickListener listener) {
            textViewName.setText(monumento.getName());
            Picasso.with(context).load(monumento.getImagen()).fit().into(imageViewMonumento);
            if (monumento.getImagen() != null) {
                int resId = context.getResources().getIdentifier(monumento.getImagen(), "drawable", context.getPackageName());
                imageViewMonumento.setImageResource(resId);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    listener.onItemClick(monumento, getAdapterPosition());
                }
            });

            final ImageButton btnFav = itemView.findViewById(R.id.btnFav);
            if (monumento.isFavorito()){
                btnFav.setImageResource(R.drawable.ic_baseline_favorite_24);
            }else {
                btnFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idUsuario = usuario.getId();
                    int idMonumentos = monumento.getIdNotes();
                    if(monumento.isFavorito()) {
                        apiMonumetosService.borrarFavorito(idMonumentos, idUsuario).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                ((ImageButton) view).setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "fallido", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Favorito favorito = new Favorito();
                        favorito.setMonumento(monumento);
                        favorito.setUsuario(usuario);
                        apiMonumetosService.anyadirFavoritoUsuario(favorito).enqueue(new Callback<Favorito>() {
                            @Override
                            public void onResponse(Call<Favorito> call, Response<Favorito> response) {
                                ((ImageButton) view).setImageResource(R.drawable.ic_baseline_favorite_24);
                            }

                            @Override
                            public void onFailure(Call<Favorito> call, Throwable t) {
                                Toast.makeText(context, "fallido", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                    //TODO: Comprobar si existe favorito
                    //EXISTE = TRUE -> Borra favorito
                    //EXISTE = FALSE -> Agrega favorito

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

    public interface OnItemClickListener {
        void onItemClick(Monumento monumento, int position);
    }

    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            monumentos.clear();
            monumentos.addAll(originalMonuments);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                monumentos.clear();
                List<Monumento> collect = originalMonuments.stream()
                        .filter(i -> i.getName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
                monumentos.addAll(collect);
            } else {
                monumentos.clear();
                for (Monumento m : monumentos) {
                    if (m.getName().toLowerCase().contains(strSearch)) {
                        monumentos.add(m);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


}
