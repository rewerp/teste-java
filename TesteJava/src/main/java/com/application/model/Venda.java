package com.application.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Venda {
	private int codigo;
	private Cliente cliente;
	private LocalDate dataVenda;
	private List<VendaItem> vendaItens;
	private double valorTotal;

	public Venda() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public List<VendaItem> getVendaItens() {
		return vendaItens;
	}

	public void setVendaItens(List<VendaItem> vendaItens) {
		this.vendaItens = vendaItens;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, codigo, dataVenda, valorTotal, vendaItens);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(cliente, other.cliente) && codigo == other.codigo
				&& Objects.equals(dataVenda, other.dataVenda)
				&& Double.doubleToLongBits(valorTotal) == Double.doubleToLongBits(other.valorTotal)
				&& Objects.equals(vendaItens, other.vendaItens);
	}

	@Override
	public String toString() {
		return "Venda [codigo=" + codigo + ", cliente=" + cliente + ", dataVenda=" + dataVenda + ", vendaItens="
				+ vendaItens + ", valorTotal=" + valorTotal + "]";
	}
	
}
