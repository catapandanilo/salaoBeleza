package br.com.catapan.salaobeleza.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.catapan.salaobeleza.domain.pedido.Pedido;
import br.com.catapan.salaobeleza.domain.pedido.PedidoRepository;
import br.com.catapan.salaobeleza.domain.pedido.RelatorioItemFaturamento;
import br.com.catapan.salaobeleza.domain.pedido.RelatorioItemFilter;
import br.com.catapan.salaobeleza.domain.pedido.RelatorioPedidoFilter;

@Service
public class RelatorioService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	public List<Pedido> listPedidos(Integer prossionalId, RelatorioPedidoFilter filter) {

		Integer pedidoId = filter.getPedidoId();
		
		if (pedidoId != null) {
			Pedido pedido = pedidoRepository.findByIdAndProfissional_Id(pedidoId, prossionalId);
			return List.of(pedido);
		}
		
		LocalDate dataInicial = filter.getDataInicial();
		LocalDate dataFinal = filter.getDataFinal();
		
		if (dataInicial == null) {
			return List.of();
		}
		
		if (dataFinal == null) {
			dataFinal = LocalDate.now();
		}
		
		return pedidoRepository.findByDateInterval(prossionalId, dataInicial.atStartOfDay(), dataFinal.atTime(23, 59, 59));
	}
	
	public List<RelatorioItemFaturamento> calcularFaturamentoItens(Integer prossionalId, RelatorioItemFilter filter) {
		List<Object[]> itensObj;
		
		Integer itemId = filter.getItemId();
		LocalDate dataInicial = filter.getDataInicial();
		LocalDate dataFinal = filter.getDataFinal();
		
		if (dataInicial == null) {
			return List.of();
		}
		
		if (dataFinal == null) {
			dataFinal = LocalDate.now();
		}
		
		LocalDateTime dataHoraInicial = dataInicial.atStartOfDay();
		LocalDateTime dataHoraFinal = dataFinal.atTime(23, 59, 59);
		
		if (itemId != 0) {
			itensObj = pedidoRepository.findItensForFaturamento(prossionalId, itemId, dataHoraInicial, dataHoraFinal);
		}
		else {
			itensObj = pedidoRepository.findItensForFaturamento(prossionalId, dataHoraInicial, dataHoraFinal);
		}
		
		List<RelatorioItemFaturamento> itens = new ArrayList<>();
		
		for (Object[] item : itensObj) {
			String nome = (String) item[0];
			Long quantidade = (Long) item[1];
			BigDecimal valor = (BigDecimal) item[2];
			itens.add(new RelatorioItemFaturamento(nome, quantidade, valor));
		}
		
		return itens;
	}
}
