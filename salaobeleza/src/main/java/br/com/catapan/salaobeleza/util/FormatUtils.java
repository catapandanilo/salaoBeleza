package br.com.catapan.salaobeleza.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

	private static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
	
	public static NumberFormat newCurrencyFormat() {
		NumberFormat nf = NumberFormat.getNumberInstance(LOCALE_BRAZIL);
		
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);//nao usar separador de milhar
		
		return nf;
	}
	
	public static String formatCurrency(BigDecimal number) {
		if (number == null) {
			return null;
		}
		
		return newCurrencyFormat().format(number);
	}
}
