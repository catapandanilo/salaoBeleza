package br.com.catapan.salaobeleza.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.catapan.salaobeleza.application.service.ClienteService;
import br.com.catapan.salaobeleza.application.service.ProfissionalService;
import br.com.catapan.salaobeleza.application.service.ValidationException;
import br.com.catapan.salaobeleza.domain.cliente.Cliente;
import br.com.catapan.salaobeleza.domain.profissional.CategoriaProfissionalRepository;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;

	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@GetMapping("/profissional/new")
	public String newProfissional(Model model) {
		model.addAttribute("profissional", new Profissional());
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaProfissionalRepository, model);
		return "profissional-cadastro";
	}
	
	@PostMapping(path = "/cliente/save")
	public String saveCliente(
			@ModelAttribute("cliente") @Valid Cliente cliente,
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
		
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/profissional/save")
	public String saveProfissional(
			@ModelAttribute("profissional") @Valid Profissional profissional,
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
		
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaProfissionalRepository, model);
		return "profissional-cadastro";
	}

}
