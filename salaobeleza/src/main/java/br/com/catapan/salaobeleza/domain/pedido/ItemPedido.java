package br.com.catapan.salaobeleza.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.catapan.salaobeleza.domain.profissional.ItemServico;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

	@EmbeddedId //chave composta
	@EqualsAndHashCode.Include
	private ItemPedidoPK id;
	
	@NotNull
	@ManyToOne
	private ItemServico itemServico;
	
	@NotNull
	private Integer quantidade;
	
	@Size(max = 50)
	private String observacoes;
	
	@NotNull
	private BigDecimal preco;
	
	public BigDecimal getPrecoCalculado() {
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
}
