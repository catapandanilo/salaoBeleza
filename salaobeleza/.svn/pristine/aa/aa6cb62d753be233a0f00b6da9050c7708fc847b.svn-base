package br.com.catapan.salaobeleza.domain.profissional;

import java.util.Comparator;

public class ProfissionalComparator implements Comparator<Profissional> {

	private SearchFilter filter;
	
	public ProfissionalComparator(SearchFilter filter, String cep) {
		this.filter = filter;
	}

	@Override
	public int compare(Profissional r1, Profissional r2) {
		return filter.isAsc() ? 1 : -1;
	}
}
