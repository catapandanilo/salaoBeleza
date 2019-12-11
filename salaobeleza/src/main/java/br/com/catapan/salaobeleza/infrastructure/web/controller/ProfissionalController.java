package br.com.catapan.salaobeleza.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.catapan.salaobeleza.application.service.ProfissionalService;
import br.com.catapan.salaobeleza.application.service.RelatorioService;
import br.com.catapan.salaobeleza.application.service.ValidationException;
import br.com.catapan.salaobeleza.domain.pedido.Pedido;
import br.com.catapan.salaobeleza.domain.pedido.PedidoRepository;
import br.com.catapan.salaobeleza.domain.pedido.RelatorioItemFaturamento;
import br.com.catapan.salaobeleza.domain.pedido.RelatorioItemFilter;
import br.com.catapan.salaobeleza.domain.pedido.RelatorioPedidoFilter;
import br.com.catapan.salaobeleza.domain.profissional.CategoriaProfissionalRepository;
import br.com.catapan.salaobeleza.domain.profissional.ItemServico;
import br.com.catapan.salaobeleza.domain.profissional.ItemServicoRepository;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;
import br.com.catapan.salaobeleza.domain.profissional.ProfissionalRepository;
import br.com.catapan.salaobeleza.util.SecurityUtils;

@Controller
@RequestMapping(path = "/profissional")
public class ProfissionalController {
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;
	
	@Autowired
	private ItemServicoRepository itemServicoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	

	@GetMapping(path = "/home")
	public String home(Model model) {
		Integer profissionalId = SecurityUtils.loggedProfissional().getId();
		List<Pedido> pedidos = pedidoRepository.findByProfissional_IdOrderByDataDesc(profissionalId);
		model.addAttribute("pedidos", pedidos);
		
		return "profissional-home";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		Integer profissionalId = SecurityUtils.loggedProfissional().getId();
		Profissional profissional = profissionalRepository.findById(profissionalId).orElseThrow();
		model.addAttribute("profissional", profissional);
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaProfissionalRepository, model);
		
		return "profissional-cadastro";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("profissional") @Valid Profissional profissional,
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) {
			try {
				profissionalService.saveProfissional(profissional);
				model.addAttribute("msg", "Profissional gravado com sucesso!");
			
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaProfissionalRepository, model);
		
		return "profissional-cadastro";
	}
	
	@GetMapping(path = "/servicos")
	public String viewServicos(Model model) {
		Integer profissionalId = SecurityUtils.loggedProfissional().getId();
		Profissional profissional = profissionalRepository.findById(profissionalId).orElseThrow();
		model.addAttribute("profissional", profissional);
		
		List<ItemServico> itensCardapio = itemServicoRepository.findByProfissional_IdOrderByNome(profissionalId);
		model.addAttribute("itensCardapio", itensCardapio);
		
		model.addAttribute("itemServico", new ItemServico());
		
		return "profissional-servicos";
	}
	
	@GetMapping(path = "/servicos/remover")
	public String remover(@RequestParam("itemId") Integer itemId, Model model) {
		itemServicoRepository.deleteById(itemId);
		return "redirect:/profissional/servicos";
	}
	
	@PostMapping(path = "/servicos/cadastrar")
	public String cadastrar(
			@Valid @ModelAttribute("itemServico") ItemServico itemServico,
			Errors errors,
			Model model) {
		
		if (errors.hasErrors()) {
			//TODO melhorar codigo... codigo repetido
			Integer profissionalId = SecurityUtils.loggedProfissional().getId();
			Profissional profissional = profissionalRepository.findById(profissionalId).orElseThrow();
			model.addAttribute("profissional", profissional);
			
			List<ItemServico> itensCardapio = itemServicoRepository.findByProfissional_IdOrderByNome(profissionalId);
			model.addAttribute("itensCardapio", itensCardapio);
			
			return "profissional-servicos";
		}
		
		profissionalService.saveItemItemServico(itemServico);
		return "redirect:/profissional/servicos";
	}
	
	@GetMapping(path = "/pedido")
	public String viewPedido(
			@RequestParam("pedidoId") Integer pedidoId,
			Model model
			) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		model.addAttribute("pedido", pedido);
		
		return "profissional-pedido";
	}
	
	@GetMapping("/relatorio/pedidos")
	public String relatorioPedidos(
			@ModelAttribute("relatorioPedidoFilter") RelatorioPedidoFilter filter,
			Model model) {
		
		Integer profissionalId = SecurityUtils.loggedProfissional().getId();
		List<Pedido> pedidos = relatorioService.listPedidos(profissionalId, filter);
		model.addAttribute("pedidos", pedidos);
		
		model.addAttribute("filter", filter);
		
		return "profissional-relatorio-pedidos";
	}
	
	@GetMapping("/relatorio/itens")
	public String relatorioItens(
			@ModelAttribute("relatorioItemFilter") RelatorioItemFilter filter,
			Model model) {
		Integer profissionalId = SecurityUtils.loggedProfissional().getId();
		
		List<ItemServico> itensCardapio = itemServicoRepository.findByProfissional_IdOrderByNome(profissionalId);
		model.addAttribute("itensCardapio", itensCardapio);
		
		List<RelatorioItemFaturamento> itensCalculados = relatorioService.calcularFaturamentoItens(profissionalId, filter);
		model.addAttribute("itensCalculados", itensCalculados);
		
		model.addAttribute("relatorioItemFilter", filter);
		
		return "profissional-relatorio-itens";
	}
}
