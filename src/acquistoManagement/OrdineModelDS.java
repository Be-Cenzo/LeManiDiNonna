package acquistoManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import catalogoManagement.Prodotto;
import checking.CheckException;
import checking.DBException;
import checking.Validazione;

public class OrdineModelDS {

	private DataSource ds = null;
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	public OrdineModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	
	public Ordine doRetrieveByKey(int ID) throws SQLException {
		connection = null;
		preparedStatement = null;

		Ordine ordine = new Ordine();

		String selectSQL = "SELECT * FROM ordine WHERE ID = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, ID);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ordine.setID(rs.getInt("ID"));
				ordine.setData(rs.getDate("data"));
				ordine.setPrezzo(rs.getFloat("prezzo"));
				ordine.setCostoSped(rs.getFloat("costoSped"));
				ordine.setNote(rs.getString("note"));
				ordine.setStato(rs.getString("stato"));
				ordine.setEmail(rs.getString("email"));
				ordine.setIndirizzo(rs.getInt("indirizzo"));
				ordine.setSpedizione(rs.getString("corriere"));
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return ordine;
	}

	
	public ArrayList<Ordine> doRetrieveAll(String order) throws CheckException, SQLException{
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		//fine
		connection = null;
		preparedStatement = null;

		ArrayList<Ordine> ordini = new ArrayList<Ordine>();

		String selectSQL = "SELECT * FROM ordine";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY data " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Ordine ordine = new Ordine();

				ordine.setID(rs.getInt("ID"));
				ordine.setData(rs.getDate("data"));
				ordine.setPrezzo(rs.getFloat("prezzo"));
				ordine.setCostoSped(rs.getFloat("costoSped"));
				ordine.setNote(rs.getString("note"));
				ordine.setStato(rs.getString("stato"));
				ordine.setEmail(rs.getString("email"));
				ordine.setIndirizzo(rs.getInt("indirizzo"));
				ordine.setSpedizione(rs.getString("corriere"));

				ordini.add(ordine);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return ordini;
	}
	
	public ArrayList<Ordine> doRetrieveAll(String order, String email) throws CheckException, SQLException {
		//pre-condition
		if(order != null && order != "" && order != "ASC" && order != "DESC")
			throw new CheckException("Invalid order");
		Validazione.checkStringaVuota(email);
		//fine
		connection = null;
		preparedStatement = null;

		ArrayList<Ordine> ordini = new ArrayList<Ordine>();

		String selectSQL = "SELECT * FROM ordine WHERE email = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY data " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Ordine ordine = new Ordine();

				ordine.setID(rs.getInt("ID"));
				ordine.setData(rs.getDate("data"));
				ordine.setPrezzo(rs.getFloat("prezzo"));
				ordine.setCostoSped(rs.getFloat("costoSped"));
				ordine.setNote(rs.getString("note"));
				ordine.setStato(rs.getString("stato"));
				ordine.setEmail(rs.getString("email"));
				ordine.setIndirizzo(rs.getInt("indirizzo"));
				ordine.setSpedizione(rs.getString("corriere"));

				ordini.add(ordine);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		return ordini;
	}

	
	public int doSave(String email, String note, int indirizzo, String spedizione, ArrayList<Prodotto> prodotti, float totale) throws CheckException, SQLException {
		//pre-condition
		Validazione.checkStringaVuota(email);
		Validazione.checkStringaVuota(spedizione);
		float somma = 0;
		if(prodotti == null || prodotti.isEmpty())
			throw new CheckException("Invalid list");
		for(Prodotto p : prodotti)
			somma += p.getPrezzo()*p.getQuantita();
		if(totale != somma)
			throw new CheckException("Inconsistent price");
		//fine
		Connection con = null;
        PreparedStatement st = null;
        ResultSet ris = null;
        int res, id = 0, dispo = 0;
        float costoSped = 0;
        System.out.println(email);
        
        try{
            con = ds.getConnection();
            con.setAutoCommit(false);

            st = con.prepareStatement("SELECT costoSped FROM Corriere WHERE nome = ?");
            st.setString(1, spedizione);
            ris = st.executeQuery();

            while(ris.next()){
                costoSped = ris.getFloat("costoSped");
            }

            st = con.prepareStatement("INSERT INTO Ordine(data, prezzo, costoSped, stato, note, email, indirizzo, corriere)" +
                                        "VALUES (current_timestamp(), ?, ?, 'Confermato', ?, ?, ?, ?);");
            st.setFloat(1, totale);
            st.setFloat(2, costoSped);
            st.setString(3, note);
            st.setString(4, email);
            st.setInt(5, indirizzo);
            st.setString(6, spedizione);

            res = st.executeUpdate();
            if (res > 0) {
                st = con.prepareStatement("SELECT MAX(ID) AS Max FROM Ordine WHERE email = ?;");
                st.setString(1, email);
                ris = st.executeQuery();
                while(ris.next()){
                    id = ris.getInt("Max");
                }
                
                for(Prodotto prodotto : prodotti){
                	int quantita = prodotto.getQuantita();
	                st = con.prepareStatement("SELECT SUM(disponibilita) AS quantita FROM Conservato WHERE prodotto = ? AND taglia = ?;");
	                st.setInt(1, prodotto.getCodice());
	                st.setString(2, prodotto.getTaglia());
	                ris = st.executeQuery();
	
	                while(ris.next()){
	                    dispo = ris.getInt("quantita");
	                }
	
	                if(dispo>=quantita) {
	
	                    st = con.prepareStatement("INSERT INTO Relato VALUES (?, ?, ? ,?);");
	                    st.setInt(1, prodotto.getCodice());
	                    st.setString(2, prodotto.getTaglia());
	                    st.setInt(3, id);
	                    st.setInt(4, quantita);
	                    res = st.executeUpdate();
	
	                    if (res > 0) {
	
	                        while(quantita > 0){
	                            int deposito = 1;
	                            int quantitaDep = 0;
	                            st = con.prepareStatement("SELECT MIN(deposito) as dep, disponibilita FROM Conservato WHERE prodotto = ? AND taglia = ? AND disponibilita > 0;");
	                            st.setInt(1, prodotto.getCodice());
                                st.setString(2, prodotto.getTaglia());
	                            ris = st.executeQuery();
	                            while(ris.next()) {
	                                deposito = ris.getInt("dep");
	                                quantitaDep = ris.getInt("disponibilita");
	                            }
	
	                            if(quantita > quantitaDep){
	                                st = con.prepareStatement("UPDATE Conservato SET disponibilita = 0 WHERE deposito = ? AND prodotto = ? AND taglia = ?;");
	                                st.setInt(1, deposito);
	                                st.setInt(2, prodotto.getCodice());
	                                st.setString(3, prodotto.getTaglia());
	                                res = st.executeUpdate();
	                                if(res <= 0) {
	                                    con.rollback();
	                                    throw new CheckException("Errore nell'update del deposito. 1");
	                                }
	                                quantita-=quantitaDep;
	                            }
	                            else{
	                                st = con.prepareStatement("UPDATE Conservato SET disponibilita = ? WHERE deposito = ? AND prodotto = ? AND taglia = ?;");
	                                int rest = quantitaDep - prodotto.getQuantita();
	                                st.setInt(1, rest);
	                                st.setInt(2, deposito);
	                                st.setInt(3, prodotto.getCodice());
	                                st.setString(4, prodotto.getTaglia());
	                                res = st.executeUpdate();
	                                if(res <= 0) {
	                                    con.rollback();
	                                    throw new CheckException("Errore nell'update del deposito. 2");
	                                }
	                                quantita = 0;
	                            }
	                            System.out.println("Update effettuato");
	                            con.commit();
	                        }
	                    } else {
	                        con.rollback();
	                        throw new CheckException("Impossibile effettuare l'update");
	                    }
	                }
	                else{
	                    con.rollback();
	                    throw new CheckException("Non c'è abbastanza disponibilità.");
	                }
                }

            } else {
            	throw new CheckException("Impossibile effettuare l'update");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (ris != null)
                    ris.close();
                if (st != null)
                    st.close();
                if (con != null)
                	con.close();
            }catch(SQLException s){
                s.printStackTrace();
            }
        }
        return 1;
	}
	
	
	

	
	public void doUpdate(Ordine ordine) throws CheckException, SQLException, DBException {
		//pre-condition
		Validazione.checkStringaVuota(ordine.getEmail());
		Validazione.checkStringaVuota(ordine.getSpedizione());
		float somma = 0;
		if(ordine.getProdotti() == null || ordine.getProdotti().isEmpty())
			throw new CheckException("Invalid list");
		for(Prodotto p : ordine.getProdotti())
			somma += p.getPrezzo()*p.getQuantita();
		if(ordine.getPrezzo() != somma)
			throw new CheckException("Inconsistent price");
		//fine
		connection = null;
		preparedStatement = null;

		String updateSQL = "UPDATE ordine SET data = ?, prezzo = ?, costoSped = ?, note = ?, stato = ?, email = ?, indirizzo = ?, corriere = ? WHERE ID = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setDate(1, ordine.getData());
			preparedStatement.setFloat(2, ordine.getPrezzo());
			preparedStatement.setFloat(3, ordine.getCostoSped());
			preparedStatement.setString(4, ordine.getNote());
			preparedStatement.setString(5, ordine.getStato());
			preparedStatement.setString(6, ordine.getEmail());
			preparedStatement.setInt(7, ordine.getIndirizzo());
			preparedStatement.setString(8, ordine.getSpedizione());
			preparedStatement.setInt(9, ordine.getID());

			err = preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		if(err == 0)
			throw new DBException("Update failed");
	}

	
	public void doDelete(Ordine ordine) throws SQLException, DBException {
		connection = null;
		preparedStatement = null;

		String deleteSQL = "DELETE FROM ordine WHERE ID = ?";
		int err;
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, ordine.getID());

			err =preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
		if(err == 0)
			throw new DBException("Update failed");
	}
		

}
