package br.com.catapan.sbpay;


public class PaymentResponse {

	private StatusPagamento status;
	private String error;
	
	public PaymentResponse(StatusPagamento status) {
		this.status = status;
	}
	
	public PaymentResponse(String error) {
		this.error = error;
	}
	
	public StatusPagamento getStatus() {
		return status;
	}
	
	public String getError() {
		return error;
	}
}
