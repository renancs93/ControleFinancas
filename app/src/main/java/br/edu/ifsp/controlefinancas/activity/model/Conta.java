package br.edu.ifsp.controlefinancas.activity.model;

public class Conta {

    private long id;
    private String descricao;
    private float saldo;

    public Conta() {
    }

    public Conta(long id, String descricao, float saldo) {
        this.id = id;
        this.descricao = descricao;
        this.saldo = saldo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }


}
