package br.com.catapan.salaobeleza.domain.pedido;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC")
	public List<Pedido> listPedidosByCliente(Integer clienteId);
	
	public List<Pedido> findByCliente_Id(Integer clienteId);
	
	public List<Pedido> findByProfissional_IdOrderByDataDesc(Integer prossionalId);
	
	public Pedido findByIdAndProfissional_Id(Integer pedidoId, Integer prossionalId);
	
	@Query("SELECT p FROM Pedido p WHERE p.profissional.id = ?1 AND p.data BETWEEN ?2 AND ?3")
	public List<Pedido> findByDateInterval(Integer prossionalId, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	@Query("SELECT i.itemServico.nome, COUNT(i.itemServico.id), SUM(i.preco) FROM Pedido p INNER JOIN p.itens i "
			+ "WHERE p.profissional.id = ?1 AND i.itemServico.id = ?2 AND p.data BETWEEN ?3 AND ?4 GROUP BY i.itemServico.nome")
	public List<Object[]> findItensForFaturamento(Integer prossionalId, Integer itemServicoId, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	@Query("SELECT i.itemServico.nome, SUM(i.quantidade), SUM(i.preco * i.quantidade) FROM Pedido p INNER JOIN p.itens i "
			+ "WHERE p.profissional.id = ?1 AND p.data BETWEEN ?2 AND ?3 GROUP BY i.itemServico.nome")
	public List<Object[]> findItensForFaturamento(Integer prossionalId, LocalDateTime dataInicial, LocalDateTime dataFinal);
}
