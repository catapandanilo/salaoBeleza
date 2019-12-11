package br.com.catapan.salaobeleza.domain.profissional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemServicoRepository extends JpaRepository<ItemServico, Integer> {
	
	public List<ItemServico> findByProfissional_IdOrderByNome(Integer prossionalId);
	
	public List<ItemServico> findByProfissional_IdAndDestaqueOrderByNome(Integer prossionalId, boolean destaque);
	
	public List<ItemServico> findByProfissional_IdAndDestaqueAndCategoriaOrderByNome(Integer prossionalId, boolean destaque, String categoria);
	
	@Query("SELECT DISTINCT ic.categoria FROM ItemServico ic WHERE ic.profissional.id = ?1 ORDER BY ic.categoria")
	public List<String> findCategorias(Integer prossionalId);
}
