package br.com.catapan.salaobeleza.domain.profissional;

import lombok.Data;

@Data
public class SearchFilter {

	public enum SearchType {
		Texto, Categoria;
	}
	
	//TODO implementar pesquisa por texto
	
	private String texto;
	private SearchType searchType;
	private Integer categoriaId;
	private boolean asc;
	
	public void processFilter(String cmdString) {
		if (searchType == SearchType.Texto) {
			categoriaId = null;
		} 
		else if (searchType == SearchType.Categoria) {
			texto = null;
		}
	}
}
