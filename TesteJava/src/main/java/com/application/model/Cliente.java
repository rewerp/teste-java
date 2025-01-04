package com.application.model;

import java.util.Objects;

public class Cliente {
    private int codigo;
    private String nome;
    private double limiteCompra; // Limite de crédito do cliente
    private int diaFechamentoFatura; // Dia para fechamento do ciclo de compras

    public Cliente() {
    }
    
    public Cliente(String nome, double limiteCompra, int diaFechamentoFatura) {
        this.codigo = 0;
        this.nome = nome;
        this.limiteCompra = limiteCompra;
        this.diaFechamentoFatura = diaFechamentoFatura;
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
	public int hashCode() {
		return Objects.hash(codigo, diaFechamentoFatura, limiteCompra, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return codigo == other.codigo && diaFechamentoFatura == other.diaFechamentoFatura
				&& Double.doubleToLongBits(limiteCompra) == Double.doubleToLongBits(other.limiteCompra)
				&& Objects.equals(nome, other.nome);
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
