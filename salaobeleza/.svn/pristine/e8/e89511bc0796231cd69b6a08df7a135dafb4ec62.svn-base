package br.com.catapan.salaobeleza.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.catapan.salaobeleza.domain.cliente.Cliente;
import br.com.catapan.salaobeleza.domain.pagamento.Pagamento;
import br.com.catapan.salaobeleza.domain.profissional.Profissional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@NotNull
	@ManyToOne
	private Profissional profissional;
	
	@NotNull
	private BigDecimal total;
	
	@OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)//ItemPedido.ItemPedidoPK id; + ItemPedidoPK.Pedido pedido;
	private Set<ItemPedido> itens;
	
	@OneToOne(mappedBy = "pedido")
	private Pagamento pagamento;
	
	public String getFormattedId() {
		return String.format("#%04d", id);
	}
}
