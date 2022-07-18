package com.example.menu.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.R;
import com.example.menu.VerProductoActivity;
import com.example.menu.entidades.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaProductoAdapter extends RecyclerView.Adapter<ListaProductoAdapter.ProductoViewHolder>{

    ArrayList<Producto> listaProducto;
    ArrayList<Producto> listaOriginal;

    public ListaProductoAdapter(ArrayList<Producto> listaProducto){
        this.listaProducto = listaProducto;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaProducto);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_producto, null, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.viewNombre.setText(listaProducto.get(position).getNombre());
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listaProducto.clear();
            listaProducto.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Producto> collecion = listaProducto.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaProducto.clear();
                listaProducto.addAll(collecion);
            }else{
                for (Producto p: listaOriginal) {
                    if(p.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaProducto.add(p);
                    };
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerProductoActivity.class);
                    intent.putExtra("IDPRODUCTO", listaProducto.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
