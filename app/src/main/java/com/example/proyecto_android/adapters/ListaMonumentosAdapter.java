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
import com.example.proyecto_android.model.Monumento;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaMonumentosAdapter extends RecyclerView.Adapter<ListaMonumentosAdapter.ViewHolder> {

    private List<Monumento> monumentos;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;

    public ListaMonumentosAdapter(List<Monumento> monumentos, int layout, OnItemClickListener itemClickListener) {
        this.monumentos = monumentos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageViewMonumento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textCardView);
            imageViewMonumento = (ImageView) itemView.findViewById(R.id.imageCardView);
        }

        public void bind(final Monumento monumento, final OnItemClickListener listener){
            textViewName.setText(monumento.getName());
            Picasso.with(context).load(monumento.getImagen()).fit().into(imageViewMonumento);
            /*int resId = context.getResources().getIdentifier(monumento.getImagen(), "drawable", context.getPackageName());
            imageViewMonumento.setImageResource(resId);*/
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
}
