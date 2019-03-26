package com.example.firebaseapp.Classes;

public class Usuario {

    private int Idade;
    private String nome;
    private String Sobrenome;
    private String sexo;

    public Usuario() {

    }

    public int getIdade() {
        return Idade;
    }

    public void setIdade(int idade) {
        Idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        Sobrenome = sobrenome;
    }

    public String getSexo() {     return sexo;   }

    public void setSexo(String sexo) {        this.sexo = sexo;    }
}
