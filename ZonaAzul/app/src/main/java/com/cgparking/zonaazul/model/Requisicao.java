package com.cgparking.zonaazul.model;

import com.cgparking.zonaazul.control.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class Requisicao {
    private String idRequisicao;
    private String status;
    private Usuario fiscal;
    private Usuario condutor;
    private Destino destino;

    public static final String STATUS_AGUARDANDO = "aguardando";
    public static final String STATUS_A_CAMINHO = "a_caminho";
    public static final String STATUS_VIAGEM = "viajando";
    public static final String STATUS_FINALIZADA = "finalizada";


    public Requisicao() {
    }

    public String getIdRequisicao() {
        return idRequisicao;
    }

    public void setIdRequisicao(String idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getFiscal() {
        return fiscal;
    }

    public void setFiscal(Usuario fiscal) {
        this.fiscal = fiscal;
    }

    public Usuario getCondutor() {
        return condutor;
    }

    public void setCondutor(Usuario condutor) {
        this.condutor = condutor;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public void salvar() {
        DatabaseReference fireBaseRef = ConfigFirebase.getFirebaseDatabase();
        DatabaseReference requisicoes = fireBaseRef.child("requisicoes");

        //criando um hash key para atribuir ao id da requisicao
        String idRequisicao = requisicoes.push().getKey();
        // setando o valor do isRequisicao com o valor que foi gerado como id no firebase.
        setIdRequisicao(idRequisicao);

        // savar todos os dados do objeto Requisicoes no proprio firebase
        requisicoes.child(getIdRequisicao()).setValue(this);//this is the itself object referenced
    }

    public void atualizar(){

        DatabaseReference fireBaseRef = ConfigFirebase.getFirebaseDatabase();
        DatabaseReference requisicoes = fireBaseRef.child("requisicoes");

        DatabaseReference requisicao =  requisicoes.child(getIdRequisicao());

        Map objeto  = new HashMap();
        objeto.put("condutor",getCondutor());
        objeto.put("status",getStatus());

        requisicao.updateChildren(objeto);

    }


}
