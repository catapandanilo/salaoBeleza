package br.com.catapan.salaobeleza.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.catapan.salaobeleza.domain.profissional.ItemServico;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class Carrinho implements Serializable {

	private List<ItemPedido> itens = new ArrayList<>();
	private Profissional profissional;
	
	public void adicionarItem(ItemServico itemServico, Integer quantidade, String observacoes) {
		if (itensVazio()) {
			profissional = itemServico.getProfissional();
		}
		
		if (!exists(itemServico)) {	
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setItemServico(itemServico);
			itemPedido.setQuantidade(quantidade);
			itemPedido.setObservacoes(observacoes);
			itemPedido.setPreco(itemServico.getPreco());
			itens.add(itemPedido);
		}
	}
	
	public void adicionarItem(ItemPedido itemPedido) {
		adicionarItem(itemPedido.getItemServico(), itemPedido.getQuantidade(), itemPedido.getObservacoes());
	}
	
	public void removerItem(ItemServico itemServico) {
		for (Iterator<ItemPedido> iterator = itens.iterator(); iterator.hasNext();) {
			ItemPedido itemPedido = iterator.next();
			if (itemPedido.getItemServico().getId().equals(itemServico.getId())) {
				iterator.remove();
				break;
			}
		}
		
		if (itensVazio()) {
			profissional = null;
		}
	}
	
	private boolean exists(ItemServico itemServico) {
		for (ItemPedido itemPedido : itens) {
			if (itemPedido.getItemServico().getId().equals(itemServico.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public BigDecimal getPrecoTotal() {
		BigDecimal soma = BigDecimal.ZERO;
		
		for (ItemPedido item : itens) {
			soma = soma.add(item.getPrecoCalculado());
		}
		
		return soma;
	}
	
	public void limpar() {
		itens.clear();
		profissional = null;
	}
	
	public boolean itensVazio() {
		return itens.size() == 0;
	}
}
