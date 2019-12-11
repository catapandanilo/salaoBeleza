package br.com.catapan.salaobeleza.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.catapan.salaobeleza.domain.cliente.Cliente;
import br.com.catapan.salaobeleza.domain.cliente.ClienteRepository;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;
import br.com.catapan.salaobeleza.domain.profissional.ProfissionalRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Transactional
	public void saveCliente(Cliente cliente) throws ValidationException {
		if (!validateEmail(cliente.getEmail(), cliente.getId())) {
			throw new ValidationException("O e-mail está duplicado");
		}
		
		if (cliente.getId() != null) {
			Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElseThrow();
			cliente.setSenha(clienteDB.getSenha());
		
		} else {
			cliente.encryptPassword();
		}
		
		clienteRepository.save(cliente);
	}
	
	private boolean validateEmail(String email, Integer id) {
		Profissional profissional = profissionalRepository.findByEmail(email);
		if (profissional != null) {
			return false;
		}
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente != null) {
			if (id == null) {
				return false;
			}
			if(!cliente.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}
}
