package com.application.model;

import java.util.Objects;

public class Produto {
	private int codigo;
	private String descricao;
	private double precoUnitario;

	public Produto() {
	}

	public Produto(String descricao, double precoUnitario) {
		this.codigo = 0;
		this.descricao = descricao;
		this.precoUnitario = precoUnitario;
	}

	public Produto(int codigo, String descricao, double precoUnitario) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.precoUnitario = precoUnitario;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao, precoUnitario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return codigo == other.codigo && Objects.equals(descricao, other.descricao)
				&& Double.doubleToLongBits(precoUnitario) == Double.doubleToLongBits(other.precoUnitario);
	}

	@Override
	public String toString() {
		return "Produto={ " + 
				"codigo=" + codigo + 
				", descricao=" + descricao + 
				", precoUnitario=" + precoUnitario + " }";
	}
	
	public String toJSON() {
	    return "{" +
	           "\"codigo\": " + codigo + "," +
	           "\"descricao\": \"" + descricao + "\"," +
	           "\"precoUnitario\": " + precoUnitario +
	           "}";
	}

}
