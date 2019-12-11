package br.com.catapan.salaobeleza.domain.profissional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {

	public Profissional findByEmail(String email);
	
	public List<Profissional> findByNomeIgnoreCaseContaining(String nome);
	
	public List<Profissional> findByCategorias_Id(Integer categoriaId);	
}
