package com.application.model;

public class Cliente {
    private int codigo;
    private String nome;
    private double limiteCompra; // Limite de crédito do cliente
    private int diaFechamentoFatura; // Dia para fechamento do ciclo de compras

    public Cliente() {
    }

    public Cliente(int codigo, String nome, double limiteCompra, int diaFechamentoFatura) {
        this.codigo = codigo;
        this.nome = nome;
        this.limiteCompra = limiteCompra;
        this.diaFechamentoFatura = diaFechamentoFatura;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLimiteCompra() {
        return limiteCompra;
    }

    public void setLimiteCompra(double limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    public int getDiaFechamentoFatura() {
        return diaFechamentoFatura;
    }

    public void setDiaFechamentoFatura(int diaFechamentoFatura) {
        this.diaFechamentoFatura = diaFechamentoFatura;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", limiteCompra=" + limiteCompra +
                ", diaFechamentoFatura=" + diaFechamentoFatura +
                '}';
    }
}
