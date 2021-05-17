package com.example.proyecto_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_android.R;
import com.example.proyecto_android.model.Favorito;
import com.example.proyecto_android.model.Monumento;
import com.example.proyecto_android.model.Usuario;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder>{

    private Usuario usuario;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;
    private TextView textCardViewFav;
    private ImageView imageViewFav;
    private TextView calleCardViewFav;

    private List<Favorito> favoritos;

    public FavoritosAdapter(List<Favorito> favoritos, Usuario usuario, int layout, OnItemClickListener itemClickListener) {
        this.favoritos = favoritos;
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
        return favoritos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Monumento monumento, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritosAdapter.ViewHolder holder, int position) {
        holder.bind(favoritos.get(position), itemClickListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCardViewFav = (TextView) itemView.findViewById(R.id.textCardViewFav);
            calleCardViewFav = (TextView) itemView.findViewById(R.id.calleCardViewFav);
            imageViewFav = (ImageView) itemView.findViewById(R.id.imageCardViewFav);
        }

        public void bind(final Favorito favorito, final FavoritosAdapter.OnItemClickListener listener) {
            textCardViewFav.setText(favorito.getMonumento().getName());
            calleCardViewFav.setText("Tel: " + favorito.getMonumento().getTelefono());
            if (favorito.getMonumento().getImagen() != null) {
                int resId = context.getResources().getIdentifier(favorito.getMonumento().getImagen(), "drawable", context.getPackageName());
                imageViewFav.setImageResource(resId);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(favorito.getMonumento(), getAdapterPosition());
                }
            });


        }

    }
}
