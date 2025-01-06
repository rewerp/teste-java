package com.application.model;

import java.util.Objects;

public class VendaItem {
	private Produto produto;
	private double quantidade;
	private double valorTotal;
	
	public VendaItem() {
	}
	
	public VendaItem(Produto produto, double quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorTotal = this.produto.getPrecoUnitario() * this.quantidade;
	}
	
	public VendaItem(Produto produto, double quantidade, double valorTotal) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public double getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(produto, quantidade, valorTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaItem other = (VendaItem) obj;
		return Objects.equals(produto, other.produto)
				&& Double.doubleToLongBits(quantidade) == Double.doubleToLongBits(other.quantidade)
				&& Double.doubleToLongBits(valorTotal) == Double.doubleToLongBits(other.valorTotal);
	}

	@Override
	public String toString() {
		return "VendaItem={ " + 
				"produto=" + produto + 
				", quantidade=" + quantidade + 
				", valorTotal=" + valorTotal + 
				" }";
	}
	
	public String toJSON() {
	    return "{" +
	           "\"produto\": " + (produto != null ? produto.toJSON() : "null") + "," +
	           "\"quantidade\": " + quantidade + "," +
	           "\"valorTotal\": " + valorTotal +
	           "}";
	}
	
}
