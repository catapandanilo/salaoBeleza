package br.com.catapan.salaobeleza.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.catapan.salaobeleza.application.service.ClienteService;
import br.com.catapan.salaobeleza.application.service.ProfissionalService;
import br.com.catapan.salaobeleza.application.service.ValidationException;
import br.com.catapan.salaobeleza.domain.cliente.Cliente;
import br.com.catapan.salaobeleza.domain.cliente.ClienteRepository;
import br.com.catapan.salaobeleza.domain.pedido.Pedido;
import br.com.catapan.salaobeleza.domain.pedido.PedidoRepository;
import br.com.catapan.salaobeleza.domain.profissional.CategoriaProfissional;
import br.com.catapan.salaobeleza.domain.profissional.CategoriaProfissionalRepository;
import br.com.catapan.salaobeleza.domain.profissional.ItemServico;
import br.com.catapan.salaobeleza.domain.profissional.ItemServicoRepository;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;
import br.com.catapan.salaobeleza.domain.profissional.ProfissionalRepository;
import br.com.catapan.salaobeleza.domain.profissional.SearchFilter;
import br.com.catapan.salaobeleza.util.SecurityUtils;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;
	
	@Autowired
	private ItemServicoRepository itemServicoRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProfissionalService profissionalService;

	@GetMapping(path = "/home")
	public String home(Model model) {
		List<CategoriaProfissional> categorias = categoriaProfissionalRepository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter());
		
		List<Pedido> pedidos = pedidoRepository.listPedidosByCliente(SecurityUtils.loggedCliente().getId());
		model.addAttribute("pedidos", pedidos);
		
		return "cliente-home";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) {
		Integer clienteId = SecurityUtils.loggedCliente().getId();
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("cliente") @Valid Cliente cliente,
			Errors errors,
			Model model) {
		
		if (!errors.hasErrors()) {
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!");
			
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.setEditMode(model, true);
		return "cliente-cadastro";
	}
	
	@GetMapping(path = "/search")
	public String search(
			@ModelAttribute("searchFilter") SearchFilter filter,
			@RequestParam(value = "cmd", required = false) String command,
			Model model) {
		
		filter.processFilter(command);
		
		List<Profissional> profissionais = profissionalService.search(filter);
		model.addAttribute("profissionais", profissionais);
		
		ControllerHelper.addCategoriasToRequest(categoriaProfissionalRepository, model);
		model.addAttribute("searchFilter", filter);
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		return "cliente-busca";
	}
	
	@GetMapping(path = "/profissional")
	public String viewSalao(
			@RequestParam("profissionalId") Integer prossionalId,
			@RequestParam(value = "categoria", required = false) String categoria,
			Model model) {
		
		Profissional profissional = profissionalRepository.findById(prossionalId).orElseThrow();
		model.addAttribute("profissional", profissional);
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		List<String> categorias = itemServicoRepository.findCategorias(prossionalId);
		model .addAttribute("categorias", categorias);
		
		List<ItemServico> itensCardapioDestaque;
		List<ItemServico> itensCardapioNaoDestaque;
		
		if (categoria == null) {
			itensCardapioDestaque = itemServicoRepository.findByProfissional_IdAndDestaqueOrderByNome(prossionalId, true);
			itensCardapioNaoDestaque = itemServicoRepository.findByProfissional_IdAndDestaqueOrderByNome(prossionalId, false);
		} 
		else {
			itensCardapioDestaque = itemServicoRepository.findByProfissional_IdAndDestaqueAndCategoriaOrderByNome(prossionalId, true, categoria);
			itensCardapioNaoDestaque = itemServicoRepository.findByProfissional_IdAndDestaqueAndCategoriaOrderByNome(prossionalId, false, categoria);
		}
		
		model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
		model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
		model.addAttribute("categoriaSelecionada", categoria);
		
		return "cliente-profissional";
	}
}
