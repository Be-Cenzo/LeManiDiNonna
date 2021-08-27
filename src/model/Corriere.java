package model;

public class Corriere {
	
	private String nome;
	private String tempo;
	private float prezzo;
	
	public Corriere() {
		
	}
	
	public Corriere(String nome, String tempo, float prezzo) {
		this.nome = nome;
		this.tempo = tempo;
		this.prezzo = prezzo;
	}
	
	/**
	 * @return il nome del corriere
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @param nome il nome del corriere da settare
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return il tempo di spedizione
	 */
	public String getTempo() {
		return tempo;
	}
	
	/**
	 * @param tempo il tempo di spedizione da settare
	 */
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	
	/**
	 * @return il prezzo
	 */
	public float getPrezzo() {
		return prezzo;
	}
	
	/**
	 * @param prezzo il prezzo della spedizione da settare
	 */
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

}
