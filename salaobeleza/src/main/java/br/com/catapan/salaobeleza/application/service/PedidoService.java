package br.com.catapan.salaobeleza.application.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.catapan.salaobeleza.domain.pagamento.DadosCartao;
import br.com.catapan.salaobeleza.domain.pagamento.Pagamento;
import br.com.catapan.salaobeleza.domain.pagamento.PagamentoRepository;
import br.com.catapan.salaobeleza.domain.pagamento.StatusPagamento;
import br.com.catapan.salaobeleza.domain.pedido.Carrinho;
import br.com.catapan.salaobeleza.domain.pedido.ItemPedido;
import br.com.catapan.salaobeleza.domain.pedido.ItemPedidoPK;
import br.com.catapan.salaobeleza.domain.pedido.ItemPedidoRepository;
import br.com.catapan.salaobeleza.domain.pedido.Pedido;
import br.com.catapan.salaobeleza.domain.pedido.PedidoRepository;
import br.com.catapan.salaobeleza.util.SecurityUtils;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Value("${salaobeleza.sbpay.url}")
	private String sbPayUrl;
	
	@Value("${salaobeleza.sbpay.token}")
	private String sbPayToken;

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PagamentoException.class)
	public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
		
		Pedido pedido = new Pedido();
		pedido.setData(LocalDateTime.now());
		pedido.setCliente(SecurityUtils.loggedCliente());
		pedido.setProfissional(carrinho.getProfissional());
		pedido.setTotal(carrinho.getPrecoTotal());
		
		pedido = pedidoRepository.save(pedido);
		
		int ordem = 1;
		
		for (ItemPedido itemPedido : carrinho.getItens()) {
			itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
			itemPedidoRepository.save(itemPedido);
		}
		
		DadosCartao dadosCartao = new DadosCartao();
		dadosCartao.setNumCartao(numCartao);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Token", sbPayToken);
		
		HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
		
		RestTemplate restTemplate = new RestTemplate();//invicacao de webservice
		
		Map<String, String> response;
		try {
			response = restTemplate.postForObject(sbPayUrl, requestEntity, Map.class);//enviar via post
		} catch (Exception e) {
			throw new PagamentoException("Erro no servidor de pagamento");
		}
		
		StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));
		
		if (statusPagamento != StatusPagamento.Autorizado) {
			throw new PagamentoException(statusPagamento.getDescricao());
		}
		
		Pagamento pagamento = new Pagamento();
		pagamento.setData(LocalDateTime.now());
		pagamento.setPedido(pedido);
		pagamento.definirNumeroEBandeira(numCartao);
		pagamentoRepository.save(pagamento);
		
		return pedido;
	}
}
