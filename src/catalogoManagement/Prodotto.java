package catalogoManagement;

import java.io.Serializable;
import java.util.Objects;

public class Prodotto implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codice;
	private String tipo;
	private float prezzo;
	private String colore;
	private String descrizione;
	private String marca;
	private String modello;
	private String taglia;
	private int quantita;
	
	public Prodotto() {
		
	}
	
	public Prodotto(int codice, String tipo, float prezzo, String colore, String descrizione) {
		this.codice = codice;
		this.tipo = tipo;
		this.prezzo = prezzo;
		this.colore = colore;
		this.descrizione = descrizione;
	}
	
	public Prodotto(int codice, String tipo, float prezzo, String colore, String descrizione, String marca, String modello, String taglia) {
		this.codice = codice;
		this.tipo = tipo;
		this.prezzo = prezzo;
		this.colore = colore;
		this.descrizione = descrizione;
		this.marca = marca;
		this.modello = modello;
		this.taglia = taglia;
	}
	
	/**
	 * @return il codice
	 */
	public int getCodice() {
		return codice;
	}
	/**
	 * @param codice il codice da settare
	 */
	public void setCodice(int codice) {
		this.codice = codice;
	}
	/**
	 * @return il tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo il tipo da settare
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return il prezzo
	 */
	public float getPrezzo() {
		return prezzo;
	}
	/**
	 * @param prezzo il prezzo da settare
	 */
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	/**
	 * @return il colore
	 */
	public String getColore() {
		return colore;
	}
	/**
	 * @param colore il colore da settare
	 */
	public void setColore(String colore) {
		this.colore = colore;
	}
	/**
	 * @return la descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param descrizione la descrizione da settare
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return la marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca la marca da settare
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	/**
	 * @return il modello
	 */
	public String getModello() {
		return modello;
	}
	/**
	 * @param modello il modello da settare
	 */
	public void setModello(String modello) {
		this.modello = modello;
	}
	/**
	 * @return la taglia
	 */
	public String getTaglia() {
		return taglia;
	}
	/**
	 * @param taglia la taglia da settare
	 */
	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	public void addQuantita(int quantita) {
		this.quantita += quantita;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Prodotto o = (Prodotto) obj;
        return codice == o.codice && quantita == o.quantita && prezzo == o.prezzo && Objects.equals(marca, o.marca) && Objects.equals(taglia, o.taglia) && Objects.equals(colore, o.colore) && Objects.equals(descrizione, o.descrizione) && Objects.equals(modello, o.modello) && Objects.equals(tipo, o.tipo);
	}

}
