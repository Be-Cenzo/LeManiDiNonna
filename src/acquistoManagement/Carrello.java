package acquistoManagement;

import java.io.Serializable;
import java.util.ArrayList;

import catalogoManagement.Prodotto;

public class Carrello implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Prodotto> prodotti;
	
	public Carrello() {
		prodotti = new ArrayList<Prodotto>();
	}
	
	public void addProdotto(Prodotto prodotto){
		for(Prodotto prod : prodotti) {
			if(prod.getCodice() == prodotto.getCodice() && prod.getTaglia().equals(prodotto.getTaglia())) {
				prod.addQuantità(prodotto.getQuantità());
				return;
			}
		}
		prodotti.add(prodotto);
	}
	
	public Prodotto removeProdotto(Prodotto prodotto) {
		for(Prodotto prod : prodotti) {
			if(prod.getCodice() == prodotto.getCodice() && prod.getTaglia().equals(prodotto.getTaglia())){
				prodotti.remove(prod);
				return prod;
			}
		}
		return null;
	}
	
	public ArrayList<Prodotto> getProdotti(){
		return prodotti;
	}
	
	public void deleteList() {
		prodotti = new ArrayList<Prodotto>();
	}
	
	public boolean isEmpty() {
		return prodotti == null ? true : (prodotti.size()>0 ? false : true);
	}
	
}
