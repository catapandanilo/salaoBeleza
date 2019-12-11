package br.com.catapan.salaobeleza.infrastructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.catapan.salaobeleza.domain.cliente.ClienteRepository;
import br.com.catapan.salaobeleza.domain.profissional.ProfissionalRepository;
import br.com.catapan.salaobeleza.domain.usuario.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRespository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = clienteRespository.findByEmail(username);
		
		if (usuario == null) {
			usuario = profissionalRepository.findByEmail(username);
			
			if (usuario == null) {
				throw new UsernameNotFoundException(username);
			}
		}
		
		return new LoggedUser(usuario);
	}
	
}
