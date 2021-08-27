package model;

import java.sql.Date;

public class Fattura {

	//forse da fare immutabile
	
	private int ID;
	private Date data;
	private float ammontare;
	private String modalità;
	private int ordine;
	
	public Fattura() {
		
	}
	
	public Fattura(int ID, Date data, float ammontare, String modalità) {
		this.ID = ID;
		this.data = data;
		this.ammontare = ammontare;
		this.modalità = modalità;
	}
	
	/**
	 * @return l'ID della fattura
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @param iD l'ID da settare
	 */
	public void setID(int iD) {
		ID = iD;
	}
	
	/**
	 * @return la data
	 */
	public Date getData() {
		return data;
	}
	
	/**
	 * @param data la data da settare
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	/**
	 * @return l'ammontare della fattura
	 */
	public float getAmmontare() {
		return ammontare;
	}
	
	/**
	 * @param ammontare l'ammontare da settare
	 */
	public void setAmmontare(float ammontare) {
		this.ammontare = ammontare;
	}
	
	/**
	 * @return la modalità di pagamento
	 */
	public String getModalità() {
		return modalità;
	}
	
	/**
	 * @param modalità la modalità di pagamento da settare
	 */
	public void setModalità(String modalità) {
		this.modalità = modalità;
	}

	/**
	 * @return l'ordine
	 */
	public int getOrdine() {
		return ordine;
	}

	/**
	 * @param ordine l'ordine da settare
	 */
	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}
	
	
}
