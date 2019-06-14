package com.cgparking.zonaazul.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgparking.zonaazul.R;
import com.cgparking.zonaazul.model.Requisicao;
import com.cgparking.zonaazul.model.Usuario;

import java.util.List;

public class RequisicoesAdapter extends RecyclerView.Adapter<RequisicoesAdapter.MyViewHolder> {
    private List<Requisicao>requisicoes;
    private Context context;
    private Usuario condutor;

    public RequisicoesAdapter(List<Requisicao> requisicoes, Context context, Usuario condutor) {
        this.requisicoes = requisicoes;
        this.context = context;
        this.condutor = condutor;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//modificado os paremetros para comparar ao original
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_requisicoes,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Requisicao requisicao = requisicoes.get(position);
        Usuario fiscal = requisicao.getFiscal();

        //condutor.getLatitude();
        //condutor.getLongitude();

        myViewHolder.nome.setText(fiscal.getNome());
        myViewHolder.distancia.setText("1km - aproximadamente");

    }

    @Override
    public int getItemCount() {
        return requisicoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome, distancia;

        public MyViewHolder(View itemView){//associar com os itens do OnCreateViewHolder
            super(itemView);
            nome = itemView.findViewById(R.id.tVAdapterNome);
            distancia = itemView.findViewById(R.id.tVAdapterDistancia);
        }
    }
}
