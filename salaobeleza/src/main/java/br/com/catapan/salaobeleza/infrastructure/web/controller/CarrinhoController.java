package br.com.catapan.salaobeleza.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.catapan.salaobeleza.domain.pedido.Carrinho;
import br.com.catapan.salaobeleza.domain.pedido.ItemPedido;
import br.com.catapan.salaobeleza.domain.pedido.Pedido;
import br.com.catapan.salaobeleza.domain.pedido.PedidoRepository;
import br.com.catapan.salaobeleza.domain.profissional.ItemServico;
import br.com.catapan.salaobeleza.domain.profissional.ItemServicoRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {
	
	@Autowired
	private ItemServicoRepository itemServicoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@ModelAttribute("carrinho")
	public Carrinho carrinho() {
		return new Carrinho();
	}
	
	@GetMapping(path = "/visualizar")
	public String viewCarrinho() {
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/adicionar")
	public String adicionarItem(
			@RequestParam("itemId") Integer itemId,
			@RequestParam("quantidade") Integer quantidade,
			@RequestParam("observacoes") String observacoes,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		ItemServico itemServico = itemServicoRepository.findById(itemId).orElseThrow();
		
		carrinho.adicionarItem(itemServico, quantidade, observacoes);
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/remover")
	public String removerItem(
			@RequestParam("itemId") Integer itemId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
		
		ItemServico itemServico = itemServicoRepository.findById(itemId).orElseThrow();
		
		carrinho.removerItem(itemServico);
		
		if (carrinho.itensVazio()) {
			sessionStatus.setComplete();//eliminar da sessao o modelo.
		}
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/refazerCarrinho")
	public String refazerCarrinho(
			@RequestParam("pedidoId") Integer pedidoId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		
		carrinho.limpar();
		
		for (ItemPedido itemPedido : pedido.getItens()) {
			carrinho.adicionarItem(itemPedido);
		}
		
		return "cliente-carrinho";
	}
}
