package br.com.catapan.salaobeleza.infrastructure.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import br.com.catapan.salaobeleza.domain.profissional.CategoriaProfissional;
import br.com.catapan.salaobeleza.domain.profissional.CategoriaProfissionalRepository;

public class ControllerHelper {
	
	public static void setEditMode(Model model, boolean isEdit) {
		model.addAttribute("editMode", isEdit);
	}
	
	public static void addCategoriasToRequest(CategoriaProfissionalRepository repository, Model model) {
		List<CategoriaProfissional> categorias = repository.findAll(Sort.by("nome"));
		model.addAttribute("categorias", categorias);
	}

}
