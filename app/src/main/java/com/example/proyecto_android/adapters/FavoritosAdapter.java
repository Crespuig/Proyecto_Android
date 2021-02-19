package com.example.proyecto_android.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.dao.FavoritoDAO;
import com.example.proyecto_android.dao.MonumentoDAO;
import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder>{

    private List<Favorito> favoritos;
    private Usuario usuario;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;
    private TextView textCardViewFav;
    private ImageView imageViewFav;
    private TextView calleCardViewFav;

    private List<Monumento> monumentosList;

    public FavoritosAdapter(List<Monumento> monumentos, Usuario usuario, int layout, OnItemClickListener itemClickListener) {
        this.monumentosList = monumentos;
        this.usuario = usuario;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FavoritosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        FavoritosAdapter.ViewHolder viewHolder = new FavoritosAdapter.ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return monumentosList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Monumento monumento, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosAdapter.ViewHolder holder, int position) {
        holder.bind(monumentosList.get(position), itemClickListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCardViewFav = (TextView) itemView.findViewById(R.id.textCardViewFav);
            calleCardViewFav = (TextView) itemView.findViewById(R.id.calleCardViewFav);
            imageViewFav = (ImageView) itemView.findViewById(R.id.imageCardViewFav);
        }

        public void bind(final Monumento monumento, final FavoritosAdapter.OnItemClickListener listener) {
            textCardViewFav.setText(monumento.getName());
            calleCardViewFav.setText("Tel: " + monumento.getTelefono());
            if (monumento.getImagen() != null) {
                int resId = context.getResources().getIdentifier(monumento.getImagen(), "drawable", context.getPackageName());
                imageViewFav.setImageResource(resId);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(monumento, getAdapterPosition());
                }
            });


        }

    }
}
