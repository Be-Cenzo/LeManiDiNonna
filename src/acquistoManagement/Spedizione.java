package acquistoManagement;

import java.io.Serializable;
import java.util.Objects;

public class Spedizione implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private String tempo;
	private float prezzo;
	
	public Spedizione() {
		
	}
	
	public Spedizione(String nome, String tempo, float prezzo) {
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
	
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Spedizione o = (Spedizione) obj;
        return prezzo == o.prezzo && Objects.equals(nome, o.nome) && Objects.equals(tempo, o.tempo);
	}

}
